package kr.or.ddit.basic.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 소켓 객체 생성
            DatagramSocket socket = new DatagramSocket();

            //상대방의 주소 정보 생성하기
            InetAddress address = InetAddress.getByName("127.0.0.1");

            while (true) {
                System.out.print("전송할 메세지 입력 : ");
                String msg = sc.nextLine();

                //전송용 패킷 객체 생성
                DatagramPacket outPacket = new DatagramPacket(msg.getBytes("utf-8"), msg.getBytes("utf-8").length, address, 9999);

                //전송하기
                socket.send(outPacket);

                if("/end".equals(msg)) {
                    break;
                }

                System.out.println();
                //상대방이 보낸 메세지 받아서 출력
                byte[] inMsg = new byte[1024];
                //수신용 패킷 객체 생성
                DatagramPacket inPacket = new DatagramPacket(inMsg, inMsg.length);

                //데이터 수신
                socket.receive(inPacket);

                // 수신된 데이터를 문자열로 변환하여 추라력하기
                String receiveMsg = new String(inMsg, 0, inPacket.getLength(), "utf-8");
                System.out.println("서버의 응답 메세지 : " + receiveMsg);
                System.out.println();

            } //.while문 끝
            System.out.println("통신 끝");

        } catch (Exception e) {

        }
    }
}
