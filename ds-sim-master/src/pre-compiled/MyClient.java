import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
Socket s=new Socket("localhost",50000);
DataOutputStream dout=new DataOutputStream(s.getOutputStream());
BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream()));
String stringMsg="HELO\n";
byte[] byteMsg = stringMsg.getBytes();
dout.write(byteMsg);
dout.flush();


dout.close();  
s.close();  
}catch(Exception e){System.out.println(e);}  
}  
}  
