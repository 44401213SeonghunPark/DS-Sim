import java.io.*;
import java.net.*;
public class MyServer {
public static void main(String[]args){
    try{
        ServerSocket ss=new ServerSocket(50000);
        Socket s=ss.accept();//establishes connection
        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());

        String str=(String)dis.readUTF();
        System.out.println("Client says= "+str);

        dout.writeUTF("G'DAY");

        String str2=(String)dis.readUTF();
        System.out.println("Client says= "+str2);

        dout.writeUTF("BYE");

        ss.close();
    }catch(Exception e){System.out.println(e);}
}
}
