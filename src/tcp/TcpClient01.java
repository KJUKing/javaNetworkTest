package tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

public class TcpClient01 {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("서버에 접속 합니다..");

        // 자신의 컴퓨터를 나타내는 방법
        // 1) 원래의 IP주소) 192.168.146.51
        // 2) 지정된 IP주소) 127.0.0.1
        // 3) 원래의 컴퓨터 이름 : DESKTOP-D3CH74P
        // 4) 지정된 컴퓨터 이름 : localhost

        // 접속할 서버의 IP주소와 Port번호를 지정하여 Socket객체를 생성한다.
        // Socket객체는 생성이 완료되면 지정한 서버로 요청 신호를 보낸다.

//        Socket socket = new Socket("서버의ip주소", "서버소켓이 설정한 포트번호");
        Socket socket = new Socket("192.168.146.51", 7777);

        // Socket객체가 생성된 이후에는 서버와 연결이 완료된 상태이므로
        // 서버와 연결된 상태에서 처리할 내용을 보내면 된다.
        System.out.println("서버와 연결되었습니다..");
        System.out.println();

        System.out.println("서버로 보낼 메세지 입력 >>");
        String str = sc.nextLine();

        // 서버로 메세지 보내기
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(str);
        System.out.println("서버로 메세지를 보냈습니다");
        System.out.println("--------------------------");
        dos.flush();

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        System.out.println("서버에서 보내온 메세지 : " + dis.readUTF());
        System.out.println();

        System.out.println("연결 종료");
        dos.close();
        dis.close();
        socket.close();

    }

}
