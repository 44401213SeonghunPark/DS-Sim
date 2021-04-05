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

String stringMsg3="REDY\n";
byte[] byteMsg3 = stringMsg3.getBytes();
dout.write(byteMsg3);
dout.flush();


in.close();
dout.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
