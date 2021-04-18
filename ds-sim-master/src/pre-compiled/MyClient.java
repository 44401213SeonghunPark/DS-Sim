import java.io.*;
import java.net.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class MyClient{
public static void main(String args[]){
try{

    Socket s=new Socket("localhost",50000);

    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
    BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream()));

    //Handshake
    dout.write(("HELO"+"\n").getBytes());
    dout.flush();

    String reply=in.readLine();

    String username = System.getProperty("user.name");
    dout.write(("AUTH "+username+"\n").getBytes());
    dout.flush();

    String reply2=in.readLine();

    while(true){
        //Send REDY
        dout.write(("REDY"+"\n").getBytes());
        dout.flush();

        //Read command from server (JOBN or JCPL or NONE)
        String reply3=in.readLine();
        String[] jobn=reply3.split(" ");

        //check if  server is finished sending jobs
        if(jobn[0].equals("NONE")){
            break;
        }

        //check if server is sending a job
        if(jobn[0].equals("JOBN")){

        //XML PARSING: FIND LARGEST SERVER
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document document = dBuilder.parse(new File("ds-system.xml"));
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("server");

            int  serverSize=0;
            String serverType="";

            for (int i =0; i<nList.getLength(); i++){
                Node node =nList.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    String current = eElement.getAttribute("coreCount");
                    int number = Integer.parseInt(current);

                    if (number > serverSize){
                        serverSize=number;
                        serverType = eElement.getAttribute("type");
                    }
                }
            }

            //send GETS command
            dout.write(("GETS Capable "+jobn[4]+" "+jobn[5]+" "+jobn[6]+"\n").getBytes());
            dout.flush();

            //read DATA
            String reply4=in.readLine();
            String[] servAmountArray=reply4.split(" ");
            int servAmountNum = Integer.parseInt(servAmountArray[1]);

            //send OK
            dout.write(("OK"+"\n").getBytes());
            dout.flush();

            //read SERVER INFO
            for(int i =0; i<servAmountNum; i++){
                String reply5=in.readLine();
            }

            //Send OK
            dout.write(("OK"+"\n").getBytes());
            dout.flush();

            //recieve "."
            String reply6=in.readLine();

            //Send SCHD command (JOBID SERVERTYPE SERVER ID)
            dout.write(("SCHD "+jobn[2]+" "+serverType+" "+"0"+"\n").getBytes());
            dout.flush();

            //read OK
            String reply7=in.readLine();

        }// end of : if jobn[0].equals(“JOBN”)
    }//end of while loop

    //Send quit
    dout.write(("QUIT"+"\n").getBytes());
    dout.flush();

    //read quit
    String replyQuit=in.readLine();


    in.close();
    dout.close();
    s.close();
    }catch(Exception e){System.out.println(e);}
    }
}

