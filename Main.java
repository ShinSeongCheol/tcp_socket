package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			// �������� ��Ʈ��ȣ�� 35248�� ���������� s����
			ServerSocket serversocket = new ServerSocket(35248);
			System.out.println("���� ���� �����..");
			while(true) {
				Socket socket = new Socket();

				// �������Ͽ� ���� ���Ϲ�ȣ�� ���Ͽ��ٰ� ����
				socket = serversocket.accept();
				System.out.println(socket.getPort()+"���� ���� ����");

				// ���Ź޴� ������ ��ü�� ����
				Server_Recive_Thread srt = new Server_Recive_Thread();
				srt.setSocket(socket);

				// �۽��ϴ� ������ ��ü�� ����
				Server_Send_Thread sst = new Server_Send_Thread();
				sst.setSocket(socket);
				
				// �ۼ��ž����带 ����
				srt.start();
				sst.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
