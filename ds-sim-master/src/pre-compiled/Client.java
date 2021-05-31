import java.net.*;
import java.util.*;
import java.io.*;


public class Client {

    private static final String JOBN = "JOBN";
    private static final String JCPL = "JCPL";
    private static final String NONE = "NONE";

    private static byte[] byteMsg;
    private static String stringMsg;
    private static String[] fieldMsg;

    private static ArrayList<Server> serverList;


    public static void main(String[] args) throws IOException {
        

        try {
        Socket s=new Socket("localhost",50000);
        serverList = new ArrayList<>(); 


        InputStreamReader din = new InputStreamReader(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader bfr = new BufferedReader(din);
        
        byteMsg = ("HELO" + "\n").getBytes();
        dout.write(byteMsg);
        dout.flush();

            stringMsg = bfr.readLine();


        byteMsg = ("AUTH" + " " + System.getProperty("user.name") + "\n").getBytes();
        dout.write(byteMsg);
        dout.flush();
            

            stringMsg = bfr.readLine();

		byteMsg = ("REDY" + "\n").getBytes();
        	dout.write(byteMsg);
       	dout.flush();

            while(!(stringMsg = bfr.readLine()).contains(NONE)) {

                if(stringMsg.contains(JOBN)) {
                    fieldMsg= stringMsg.split(" "); 

                    JobParser job = new JobParser(fieldMsg); 
			byteMsg = ("GETS_Capable" + " " + job.JobCoreCount + " " + job.JobMemory + " " + job.JobDisk + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();

			byteMsg = ("OK" + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();

                    stringMsg = bfr.readLine();
                    fieldMsg = stringMsg.split(" "); 
                    
                    int ServerCap = Integer.parseInt(fieldMsg[1]); 

                    byteMsg = ("OK" + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();

                    for(int i = 0; i < ServerCap; i++) {
           stringMsg = bfr.readLine();

            fieldMsg = stringMsg.split(" ");



	Server currentServer = new Server(fieldMsg);
            serverList.add(currentServer);
            }

                    byteMsg = ("OK" + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();

                    stringMsg = bfr.readLine();
                    
                    
                            Server bestServer = serverList.get(0);                                  
        int cheapest = serverList.get(0).ServerCoreCount() - job.JobCoreCount();      

        for(Server serverList : serverList) {                                               
            int fit = serverList.ServerCoreCount() - job.JobCoreCount();                   
            
             if(( cheapest > fit && ( bestServer.ServerJobsWaiting() > serverList.ServerJobsWaiting())) || cheapest < 0  ) {
                        cheapest = fit;
                        bestServer = serverList;
            }
        }



                    
                   	 byteMsg = ("SCHD" + " " + job.JobId + " " + bestServer.ServerName + " " + bestServer.ServerId + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();

                    stringMsg = bfr.readLine();
                    
                    serverList.clear(); 

                    	byteMsg = ("REDY" + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();
                } 
                
                else if (stringMsg.contains(JCPL)) {
                    	byteMsg = ("REDY" + "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();
       		
                }
            }
            
            		byteMsg = ("QUIT"+ "\n").getBytes();
        		dout.write(byteMsg);
       		dout.flush();
           
        din.close();
        dout.close();
        bfr.close();
        s.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
