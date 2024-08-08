package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpFileServer {
    public static void main(String[] args) {
        File file = new File("d:/d_other/upload"); // 저장할곳

        // 저장할 폴더가 없다면
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("서버가 준비되었습니다");
            Socket socket = serverSocket.accept();

            System.out.println("파일 수신 작업 시작");

            // 클라이언트가 보낸 파일명을 받아올 스트림 객체 생성
            DataInputStream din = new DataInputStream(socket.getInputStream());

            String fileName = din.readUTF();

            File saveFile = new File(file, fileName);

            //소켓에서 읽어서 파일로 출력
            BufferedInputStream bin = new BufferedInputStream(socket.getInputStream());
            // 파일로 저장할 출력용 스트림 객체 생성
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(saveFile));

            //소켓으로 읽어서 파일로 저장하기
            byte[] buffer = new byte[1024];
            int length =0;
            while ((length=bin.read(buffer)) != -1) {
                bout.write(buffer, 0, length);
            }
            bout.flush();
            System.out.println("파일 수신작업 완료");
            bout.close();
            bin.close();
            socket.close();
            serverSocket.close();
            din.close();

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
