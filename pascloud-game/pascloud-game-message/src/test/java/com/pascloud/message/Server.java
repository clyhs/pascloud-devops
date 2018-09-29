package com.pascloud.message;

import com.google.protobuf.ByteString;
 
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 * Created by yubo7 on 2016/8/16.
 */
public class Server {
 
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8000);
        System.out.println("server started...");
        Socket socket = ss.accept();
        System.out.println("a client connected!");
        //从输入流中解析出Person对象
        PersonBean.Person person = PersonBean.Person.parseFrom(ByteString.readFrom(socket.getInputStream()));
        if(person != null) {
            System.out.println("server received data:\n" + person.toString());
        }
    }
 
}
