package tcp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpFileClient {

    public static void main(String[] args) {

        TcpFileClient client = new TcpFileClient();

//        File file = new File("d:/d_other/펭귄.jpg");
        File file = client.selectFile("OPEN");
        if (file == null) {
            System.out.println("전송할 파일을 선택하세요.");
            System.out.println("전송 작업을 중단합니다");
            return;
        }

        // 전송할 파일이 없으면 '오류 메세지'를 출력하고 종료
        if (!file.exists()) {
            System.out.println(file.getAbsolutePath() + "파일이 없습니다");
            System.out.println("전송 작업을 중단합니다");
            return;
        }
        try {
            Socket socket = new Socket("127.0.0.1", 7777);
            System.out.println("전송 작업 시작");

            //파일 이름 전송을 위한 스트림 객체 생성
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(file.getName()); // 파일이름 전송


            //파일 읽어서 소켓출력

            //파일을 읽어올 스트림 객체 생성
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            //서버로 전송할 출력용 스트림 객체 생성(소켓이용)
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            byte[] buf = new byte[1024];
            int length = 0;

            while ((length = bis.read(buf)) != -1) {
                bos.write(buf, 0, length);
            }
            bos.flush();

            System.out.println("파일 전송 완료");
            bis.close();
            bos.close();
            socket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("파일 전송실패");
            e.printStackTrace();
        }
    }
    public File selectFile(String option) {
        //SWING에서 제공하는 파일 열기, 저장 창 연습
        JFileChooser chooser = new JFileChooser();

        //선택할 파일 유형 중 '모든 파일'

        // 선택할 파일의 확장자 설정
        FileNameExtensionFilter txt =
                new FileNameExtensionFilter("text파일(*.txt)", "txt");
        FileNameExtensionFilter doc =
                new FileNameExtensionFilter("MS Word File", "doc", "docx");
        FileNameExtensionFilter img =
                new FileNameExtensionFilter("Image File", new String[] {"jpg", "jpeg", "png", "gif", "bmp"});

        //확장자 목록을 FileChooser에 등록
        chooser.addChoosableFileFilter(txt);
        chooser.addChoosableFileFilter(doc);
        chooser.addChoosableFileFilter(img);

        // 전체 확장자 목록(파일 유형)
        chooser.setFileFilter(txt);

        // Dialog 기본 위치 설정
        chooser.setCurrentDirectory(new File("d:d_other"));

        //Dialog창 열기
//        int result = chooser.showOpenDialog(new Panel()); //열기
//        int result = chooser.showSaveDialog(new Panel()); //저장
        int result;
        if ("SAVE".equals(option.toUpperCase())) {
            result = chooser.showSaveDialog(new Panel()); //저장

        } else if ("OPEN".equals(option.toUpperCase())) {
            result = chooser.showOpenDialog(new Panel()); //열기
        }else{
            System.out.println("메소드를 호출할 때 매개변수에 'save' 또는 'open'을 지정하세요..");
            return null;
        }

        File selectedFile = null;
        // 창에서 '열기' 또는 '저장' 버트을 눌렀는지 확인
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            System.out.println("선택한 파일 :  " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }
}
