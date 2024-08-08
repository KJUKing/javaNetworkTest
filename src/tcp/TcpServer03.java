package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer03 {
    public static void main(String[] args) throws IOException {
        //TCP소켓 통신을 위해 Port번호를 지정하여 ServerSocket객체를 생성한다
        ServerSocket server = new ServerSocket(7777);
        System.out.println("서버가 접속을 준비 중입니다.");
        System.out.println();

        // accept() 메소드 -> Client에서 연결 요청이 올 때까지 계속 기다림
        //                -> 연결 요청이 오면 새로운 Socket객체를 생성함
        //                   Client의 Socket과 연결한다
        Socket socket = server.accept();

        // 이 accept()메소드 호출 명령 이후의 내용은 Client와 연결이 완료된 후의 처리 내용을 작성하면 된다.

        System.out.println("클라이언트가 연결되었습니다.");
        System.out.println("접속한 상대방의 정보.");
        System.out.println("IP 주소 : " + socket.getInetAddress().getHostAddress());
        System.out.println("Port 주소 : " + socket.getPort());
        System.out.println();

        System.out.println("자신의 정보");
        System.out.println("접속한 상대방의 정보.");
        System.out.println("IP 주소 : " + socket.getLocalAddress().getHostAddress());
        System.out.println("Port 주소 : " + socket.getLocalPort());
        System.out.println();

        //클라이언트가 보낸 메세지를 받아서 화면에 출력하고 받은 메세지를 다시 클라이언트에 보내기

        // 클라이언트가 보낸 메세지 받기
        //      -> Socket객체의 InputStream객체를 이용한다.
        //      -> Socket의 getInputStream() 메소드를 이용하여 InputStream객체를 구할수도있다

        System.out.println();
        System.out.println("--------------------------------");

        String str ="파일 받았습니다";
        OutputStream out = socket.getOutputStream();
        DataOutputStream dout = new DataOutputStream(out);

        //클라이언트로 메세지보내기
        dout.writeUTF(str);

        System.out.println("클라이언트로 메세지를 보냈습니다,");
        System.out.println();

        System.out.println("연결 종료");

        //사용했던 스트림 소켓닫기
        dout.close();
        socket.close();
        server.close();
    }
}
