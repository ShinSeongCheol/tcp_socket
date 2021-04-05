package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			// 서버소켓 포트번호가 35248인 서버소켓을 s생성
			ServerSocket serversocket = new ServerSocket(35248);
			System.out.println("서버 연결 대기중..");
			while(true) {
				Socket socket = new Socket();

				// 서버소켓에 들어온 소켓번호를 소켓에다가 저장
				socket = serversocket.accept();
				System.out.println(socket.getPort()+"서버 연결 성공");

				// 수신받는 쓰레드 객체를 생성
				Server_Recive_Thread srt = new Server_Recive_Thread();
				srt.setSocket(socket);

				// 송신하는 쓰레드 객체를 생성
				Server_Send_Thread sst = new Server_Send_Thread();
				sst.setSocket(socket);
				
				// 송수신쓰레드를 시작
				srt.start();
				sst.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
