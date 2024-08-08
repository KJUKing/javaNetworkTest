package tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

// 이 스레드 클래스는 소켓을 통해서 메세지를 받아서 화면에 출력하는 역할 수행
public class Receiver extends Thread {
    private Socket socket;
    private DataInputStream din;

    public Receiver(Socket socket) {
        super();
        this.socket = socket;
        try {
            din = new DataInputStream(this.socket.getInputStream());

        }catch (IOException e) {

        }
    }
@Override
    public void run() {
    while (din != null) {
        try {
            System.out.println(din.readUTF());
        }catch (IOException e) {

        }
    }
    }
}
