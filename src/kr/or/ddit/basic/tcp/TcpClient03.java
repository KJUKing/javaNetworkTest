package kr.or.ddit.basic.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

public class TcpClient03 {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner sc = new Scanner(System.in);

        String c = "d:/d_other/펭귄.jpg";
        String v = "d:/d_other/upload/펭귄.jpg";
        System.out.println("서버에 접속 합니다..");
        Socket socket = new Socket("192.168.146.51", 7777);

        // Socket객체가 생성된 이후에는 서버와 연결이 완료된 상태이므로
        // 서버와 연결된 상태에서 처리할 내용을 보내면 된다.
        System.out.println("서버와 연결되었습니다..");
        System.out.println();

        try  {
            FileInputStream fis = new FileInputStream(c);
            FileOutputStream fos = new FileOutputStream(v);
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] buf = new byte[1024]; //버퍼 크기는 1kb설정
            int read;
//            while ((read = fis.read(buf)) != -1) {
//                fos.write(buf, 0, read);
//            }

            while ((read = bis.read(buf)) != -1) {
                fos.write(buf, 0, read);
            }
            bos.flush();

            bos.close();
            System.out.println("복사완료");
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }

        // 서버로 메세지 보내기
        System.out.println("서버로 파일을 보냈습니다");
        System.out.println("--------------------------");

        System.out.println("연결 종료");
        socket.close();

    }

}
