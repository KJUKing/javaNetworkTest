package kr.or.ddit.basic.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TcpMultiChatServer {
    // 접속한 클라이언트들의 정보를 저장할 Map객체 변수 선언
    // 	key값 : 접속한 사람 이름, value : 접속한 Socket객체
    private Map<String, Socket> clientMap;

    // 생성자
    public TcpMultiChatServer() {
        // 동기화 처리가 되도록 Map객체를 생성한다.
        clientMap = Collections.synchronizedMap(new HashMap<String, Socket>());
    }

    public static void main(String[] args) {
        new TcpMultiChatServer().serverStart();
    }

    // 시작 메서드
    public void serverStart() {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(7777);
            System.out.println("서버가 시작되었습니다...");

            while(true) {
                socket = server.accept();  // 클라이언트의 접속을 기다린다.

                System.out.println("[" + socket.getInetAddress() + " : " +
                        socket.getLocalPort() + "]에서 접속했습니다...");
                System.out.println();

                //------------------------------------------

                // 쓰레드 객체를 생성하고 실행한다...
                ServerReceiver serverThread = new ServerReceiver(socket);
                serverThread.start();

            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if(server!=null) try { server.close(); }catch(IOException e) {}
        }

    }  // serverStart()메서드 끝...

    // clientMap변수에 저장된 전체 클라이언트 정보를 이용하여 모든 클라이언트에 메시지를 전송하는 메서드
    private void sendToAll(String msg) {
        // clientMap의 전체 데이터 개수만큼 반복한다.
        for(String name : clientMap.keySet()) {
            try {
                // 해당 key값과 쌍으로 같이 저장된 Socket객체를 이용하여 출력용 스트림 객체를 생성한다.
                DataOutputStream dout = new DataOutputStream(
                        clientMap.get(name).getOutputStream()	);
                // 생성된 스트림 객체를 이용하여 메시지를 출력한다.(전송한다.)
                dout.writeUTF(msg);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    } // sendToAll()메서드 끝....

    //------------------------------------------------
    // 접속한 각각의 클라이어트와 연결되어 처리될 Thread클래스를 작성한다.
    // (접속한 클라이언트 정보를 Map에 추가하는 작업과 클라이언트가 보내온 메시지를 받아서
    //	Map에 저장된 전체 클라이언트들에게 보내는 작업을 수행한다.)
    class ServerReceiver extends Thread{
        private Socket socket;
        private DataInputStream din;
        private DataOutputStream dout;

        // 생성자
        public ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                // 수신용 스트림 객체 생성
                din = new DataInputStream(this.socket.getInputStream());

                // 송신용 스트림 객체 생성
                dout = new DataOutputStream(this.socket.getOutputStream());

            } catch (Exception e) {
                // TODO: handle exception
            }
        }// 생성자 끝...

        @Override
        public void run() {
            String name = "";
            try {
                // 클라이언트가 연결이 완료되면 첫번째로 '대화명'을 입력받아 서버로 전송한다.
                // 서버에서는 이 '대화명'을 받아서 '대화명'이 중복되는지 여부를 검사하여 결과를
                // 클라이언트에게 보내준다.
                // 클라이언트가 보내온 '대화명'이 중복되지 않을 때까지 반복한다.
                while(true) {
                    name = din.readUTF(); // 클라이언트가 보낸 '대화명' 받기

                    if(clientMap.containsKey(name)) {  // '대화명'이 중복되면...
                        dout.writeUTF("대화명중복");   // '대화명중복'메시지를 전송한다.
                    }else {  // '대화명'이 중복되지 않으면...
                        dout.writeUTF("OK");	// 'OK'메시지를 전송한다.
                        break;
                    }

                } // while문 끝...

                // 새로운 접속자를 기존의 접속자들에게 알려준다.
                sendToAll("[" + name + "]님이 대화방에 입장했습니다...");

                // '대화명'과 접속한 클라이언트의 Socket을 Map에 추가한다.
                clientMap.put(name, socket);

                System.out.println("현재 접속자 수 : " + clientMap.size() + "명");
                System.out.println();

                // 한 클라이언트가 보낸 메시지를 받아서 전체 접속자들에게 전송한다.
                while(din!=null) {
                    sendToAll(din.readUTF());
                }


            }catch(Exception e) {

            }finally {
                // 이 finally 영역이 실행된다는 것은 현재의 클라이언트가 접속이 종료된 상태임을 의미한다.
                sendToAll("[" + name + "]님이 접속을 종료했습니다...");

                // 접속자 목록(clientMap)에서 해당 클라이언트를 삭제한다.
                clientMap.remove(name);

                System.out.println("[" + socket.getInetAddress() + " : " +
                        socket.getLocalPort() + "]에서 접속을 종료했습니다...");
                System.out.println();
                System.out.println("현재 접속자 수 : " + clientMap.size() + "명");
                System.out.println();

            }

        }


    }


}