package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {

		try {
			//���� ������ �ּҰ� localhost�̰� ��Ʈ�� 35248�� ���� ������ ����
			Socket socket = new Socket("localhost", 35248);
			
			//�۽� ������ ��ü ����
			Client_Send_Thread cst = new Client_Send_Thread();
			//Client_Send_Thread ��ü �޼��� setSocket�� socket��ȣ�� �Ѱ���
			cst.setSocket(socket);
			
			//���� ������ ��ü ����
			Client_Recive_Thread crt = new Client_Recive_Thread();
			//Client_Recive_Thread�� ���� ��ȣ�� �Ѱ���
			crt.setSocket(socket);

			//�ۼ��� ������ ����
			cst.start();
			crt.start();
			
			//ȣ��Ʈ�� �˼������� ���� �߻�
		} catch (UnknownHostException e) {
			e.printStackTrace();
			//����� ������ ���� ���� �߻�
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
