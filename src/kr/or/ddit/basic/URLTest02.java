package kr.or.ddit.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLTest02 {
    public static void main(String[] args) throws IOException {
        //URLConnection 클래스 -> 애플리케이션과 URL간의 통신연결을위한 클래스

        // 특정 서버의 정보와 파일 내용 가져오기
        URL url = new URL("https://www.naver.com");

        //URL객체를 이용하여 URLConnection객체를 구한다.
        URLConnection urlCon = url.openConnection();

        //헤더 정보가져오기

        Map<String, List<String>> headerMap = urlCon.getHeaderFields();

        //헤더의 key값(또는 Header의 Name값) 가져오기
        for (String s : headerMap.keySet()) {

            //키값 이용해서 헤더밸류값 구하기
            System.out.println(s + " : " + headerMap.get(s));

        }
        System.out.println("---------------------------------------------------");

        // 해당 문서의 내용 가져오기 (index.html문서의 내용 가져오기)

        // 방법1) -> URLConnection객체를 이요하는 방법
        //파일내용을 읽어오기위한 스트림 객체 생성
        InputStream in = urlCon.getInputStream();
        InputStreamReader isr = new InputStreamReader(in, "utf-8");
        BufferedReader br = new BufferedReader(isr);

        //파일내용을 읽어와 화면에 출력하기
        while (true) {
            String str = br.readLine();
            if (str == null) {
                break;
            }
            System.out.println(str);
        }
        br.close();


        // 방법2) -> URL객체의 openStream() 메소드 이용하기
        InputStream in2 = url.openStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2, "utf-8"));
        while (true) {
            String str = br2.readLine();
            if (str == null) {
                break;
            }
            System.out.println(str);
        }
        br2.close();
    }
}
