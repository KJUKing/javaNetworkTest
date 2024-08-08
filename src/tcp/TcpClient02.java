package tcp;

import java.net.Socket;

public class TcpClient02 {
    public static void main(String[] args) {

        try {
            // 소켓 객체를 생성하여 서버에 접속 요청을 보내고, 접속이 완료되면
            // 생성된 소켓을 수신용 스레드와 송신용 스레드를 생성할떄 주입하여 실행
            Socket socket = new Socket("127.0.0.1", 8888);
        } catch (Exception e) {

        }
    }
}
