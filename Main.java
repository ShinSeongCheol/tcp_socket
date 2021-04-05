package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {

		try {
			//소켓 아이피 주소가 localhost이고 포트가 35248로 열린 서버에 접속
			Socket socket = new Socket("localhost", 35248);
			
			//송신 쓰레드 객체 생성
			Client_Send_Thread cst = new Client_Send_Thread();
			//Client_Send_Thread 객체 메서드 setSocket에 socket번호를 넘겨줌
			cst.setSocket(socket);
			
			//수신 쓰레드 객체 생성
			Client_Recive_Thread crt = new Client_Recive_Thread();
			//Client_Recive_Thread에 소켓 번호를 넘겨줌
			crt.setSocket(socket);

			//송수신 쓰레드 시작
			cst.start();
			crt.start();
			
			//호스트를 알수없을때 에러 발생
		} catch (UnknownHostException e) {
			e.printStackTrace();
			//입출력 에러가 나면 에러 발생
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
