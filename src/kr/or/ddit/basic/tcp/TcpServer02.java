package kr.or.ddit.basic.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer02 {
    public static void main(String[] args) {
        try {
            // 서버 소켓을 만들고 클라이언트가 접속해 오면 클라이언트와 연결된 소켓을 만든다
            // 이 만들어진 소켓을 메세지를 보내는 스레드와 메세지를 받는 스레드에 주입시킨다
            // 이 스레드들을 실행한다
            ServerSocket server = new ServerSocket(7777);

            System.out.println("서버가 준비 중입니다");
            Socket socket = server.accept();
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);
//
//            new Thread(sender).start();
//            new Thread(receiver).start();
            sender.start();
            receiver.start();
        }catch (IOException e){

        }
    }
}
