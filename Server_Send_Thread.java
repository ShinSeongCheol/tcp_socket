package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Server_Send_Thread extends Thread {

	Server_Recive_Thread srt = new Server_Recive_Thread();

	BufferedReader br = null;
	BufferedWriter out = null;

	Socket S_Socket;
	String Send_str;

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(S_Socket.getOutputStream()));
			//Ŭ���̾�Ʈ������ ������ ���� �޽��� �ѹ� ����
			out.write("[�α���] [ȸ������] [��й�ȣ ����] [ȸ��Ż��] [ȸ������] [�α����] [������]\n");
			out.flush();
			out.write("/help�� �Է��ϸ� ��ɾ ���ɴϴ�.\n");
			out.flush();
			//���� �ݺ���
			while (true) {
				//�ý��ۿ��� �о�� ���ڿ��� Ŭ���̾�Ʈ ������ ������ ���� �޽��� ����
				Send_str = br.readLine();
				out.write(Send_str + "\n");
				out.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(S_Socket != null)
					S_Socket.close();
				if(out != null)
					out.close();
				if(br != null)
					br.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
	//���� ������ S_Socket�� ����
	public void setSocket(Socket _socket) {
		S_Socket = _socket;
	}
}
