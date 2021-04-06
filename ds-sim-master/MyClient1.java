import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;
public class MyClient1 {

public static void main(String[] args){
    try{
        int largest = 0;
        Socket s=new Socket("localhost", 50000);
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());

        dout.write(("HELO"+"\n").getBytes());
        dout.flush();

        String str=(String)br.readLine();
        System.out.println("Server says "+ str);

        dout.write(("AUTH rand"+"\n").getBytes());
        dout.flush();

        String str2=(String)br.readLine();
        System.out.println("Server says " + str2);

        dout.write(("REDY"+"\n").getBytes());

        String str3=(String)br.readLine();
        String[] arr = str3.split(" ");
        System.out.println("Server says " + str3);
        System.out.println(arr[1]);

        dout.write(("GETS All" +"\n").getBytes());

        String getResults =(String)br.readLine();
        String[] getAmount = getResults.split(" ");
        int serverAmount = Integer.parseInt(getAmount[1]);
        //String str4=(String)br.readLine();
        System.out.println("Server amount "+ serverAmount);

        dout.write(("OK" +"\n").getBytes());

        String servers="";
        String serverType="";
        String serverId="";
        int max = 0;
        int temp = 0;
        int i = 0;
        servers=(String)br.readLine();
        while(i<serverAmount){
            servers=(String)br.readLine();
            String[] arr2 = servers.split(" ");
            System.out.println("Server says "+servers);
            System.out.println("CoreCount = "+arr2[4]);
            temp = Integer.parseInt(arr2[4]);
            if(temp>max){
                max = temp;
                serverType=arr[0];
                serverId=arr[1];
            }
            System.out.println("max = "+max);
        }
        dout.write(("REDY"+"\n").getBytes());
        String jobs="";
        String jobId="";
        String ok="";
        while(!jobs.equals("NONE")){
            jobs=(String)br.readLine();
            String[] jobsInfo = jobs.split(" ");
            jobId= jobsInfo[2];
            dout.write(("SCHD "+jobId+serverType+serverId+"\n").getBytes());
            ok=(String)br.readLine();
           // System.out.println("Server says "+str4);

            dout.write(("OK" +"\n").getBytes());
            dout.write(("REDY"+"\n").getBytes());
        }
        dout.write(("QUIT"+"\n").getBytes());
        dout.flush();
        dout.close();
        s.close();
    }catch(Exception e){System.out.println(e);}
}
}

