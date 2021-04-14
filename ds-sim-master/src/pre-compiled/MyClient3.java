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
String stringMsg="HELO\n";
byte[] byteMsg = stringMsg.getBytes();
dout.write(byteMsg);
dout.flush();

String reply=in.readLine();

String stringMsg2="AUTH username\n";
byte[] byteMsg2 = stringMsg2.getBytes();
dout.write(byteMsg2);
dout.flush();

String reply2=in.readLine();

while(true){
        //Send REDY
        String stringMsg3="REDY\n";
        byte[] byteMsg3 = stringMsg3.getBytes();
        dout.write(byteMsg3);
        dout.flush();

        //receive command from server (JOBN or JCPL or NONE)
        String reply3=in.readLine();
        String[] jobn=reply3.split(" ");
        System.out.println(jobn[0]);

        //check if  server is finished sending jobs
        if(jobn[0].equals("NONE")){
        System.out.println("none detected");
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

        for (int i =0; i<nList.getLength(); i++)
        {
                Node node =nList.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE)
                {
            Element eElement = (Element) node;
                        String current = eElement.getAttribute("coreCount");
                        int number = Integer.parseInt(current);

                        if (number > serverSize)
                        {
                                serverSize=number;
                                serverType = eElement.getAttribute("type");
                        }
                }
        }

        //send GETS command
        String stringMsg4="GETS Capable "+jobn[4]+" "+jobn[5]+" "+jobn[6]+"\n";
        byte[] byteMsg4 = stringMsg4.getBytes();
        dout.write(byteMsg4);
        dout.flush();

        //receive DATA
        String reply4=in.readLine();
        String[] servAmountArray=reply4.split(" ");
        int servAmountNum = Integer.parseInt(servAmountArray[1]);

        //send OK
        String stringMsg5="OK\n";

     byte[] byteMsg5 = stringMsg5.getBytes();
        dout.write(byteMsg5);
        dout.flush();

        //read SERVER INFO
        for(int i =0; i<servAmountNum; i++){
        String reply5=in.readLine();
        }

        String stringMsg6="OK\n";
        byte[] byteMsg6 = stringMsg6.getBytes();
        dout.write(byteMsg6);
        dout.flush();

        //recieve "."
        String reply6=in.readLine();

        //send SCHD command (JOBID SERVERTYPE SERVER ID)
        String stringMsg7="SCHD "+jobn[2]+" "+serverType+" "+"0"+"\n";
        System.out.println(stringMsg7);
        byte[] byteMsg7 = stringMsg7.getBytes();
        dout.write(byteMsg7);
        dout.flush();

        //receive OK
        String reply7=in.readLine();

        }// end of : if jobn[0].equals(“JOBN”)
}//end of while loop



        //Send quit
        String stringMsgQuit="QUIT\n";
        byte[] byteMsgQuit = stringMsgQuit.getBytes();
        dout.write(byteMsgQuit);
        dout.flush();

        //receive quit
        String replyQuit=in.readLine();




in.close();
dout.close();   
s.close();
}catch(Exception e){System.out.println(e);}
}
}
