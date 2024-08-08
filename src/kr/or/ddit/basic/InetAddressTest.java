package kr.or.ddit.basic;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {

        // 특정 사이트 ip 정보 가져오기
        // InetAddress 클래스 -> IP주소를 다루기 위한 클래스
        // www.naver.com
//        InetAddress ip = InetAddress.getByName("www.naver.com");
        InetAddress ip = InetAddress.getByName("www.nate.com");

        System.out.println("HostName : " + ip.getHostName());
        System.out.println("HostAddress : " + ip.getHostAddress());
        System.out.println("toString: " + ip.toString());

        InetAddress localIp = InetAddress.getLocalHost();

        System.out.println("HostName : " + localIp.getHostName());
        System.out.println("HostAddress : " + localIp.getHostAddress());
        System.out.println("toString: " + localIp.toString());

        //IP주소가 여러개인 호스트의 IP정보 가져오기
        InetAddress[] ipArr = InetAddress.getAllByName("www.naver.com");
        for (InetAddress inetAddress : ipArr) {
            System.out.println("inetAddress.getHostName() = " + inetAddress.getHostName());
            System.out.println("inetAddress.getHostAddress() = " + inetAddress.getHostAddress());
        }
    }
}
