package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server_Recive_Thread extends Thread {
	
	//���� R_Socket������ ����
	private Socket R_Socket;
	//Maria_db ��ü�� ����
	private Maria_db maria_db = new Maria_db();
	
	private static ArrayList al = new ArrayList();

	//���۵� ���� ������ ����
	BufferedReader in = null;
	BufferedWriter bw = null;
	BufferedWriter out = null;
	BufferedWriter out_all = null;
	
	String client;
	
	//�����ڷ� Server_Recive_Thread�� ��ü�� �����Ǹ� client�� client�� �ʱ�ȭ
	public Server_Recive_Thread() {
		client = "client";
	}

	//�����尡 ���۵Ǵ� �κ�
	public void run() {
		try {
			//���۵� ���� ��ü�� �����ϴ� �κ�
			in = new BufferedReader(new InputStreamReader(R_Socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
			out = new BufferedWriter(new OutputStreamWriter(R_Socket.getOutputStream()));
			
			String Recive_str, id, passwd, changed_passwd,result;

			//�ݺ���
			while (true) {
				//���Ͽ��� �о�� ���ڸ� Recive_str�� ����
				Recive_str = in.readLine();
				//Recive_str�� ���� ����ġ���� ����
				switch (Recive_str) {

				// �α���
				case "�α���": {
					//���̵�� ��й�ȣ�� ���Ͽ��� �о��
					id = in.readLine();
					passwd = in.readLine();
					//maria_db.login(id,passwd)�� ����ؼ� ���� ���ϰ��� �ٽ� Ŭ���̾�Ʈ ������ �޽��� ����
					result = maria_db.login(id, passwd);
					out.write(result);
					out.flush();
					//maria_db.login()�޼��尡 �α��� ����!�� ���ϰ����� ��ȯ�ϸ� client�� ���� id�� �ʱ�ȭ
					if(result.equals("�α��� ����!\n")) {
						client = id;
					}
					break;
				}
				// ȸ������
				case "ȸ������": {
					id = in.readLine();
					passwd = in.readLine();
					//���̵�� ��й�ȣ�� ������ ��� users�� ����ϴ� �κ�
					maria_db.insert_user(id, passwd);
					
					out.write("ȸ������ ����ϴ�.\n");
					out.flush();
					break;
				}
				// ��й�ȣ ����
				case "��й�ȣ ����": {
					id = in.readLine();
					passwd = in.readLine();
					changed_passwd = in.readLine();
					//maria_db.update_passwd�޼��� ����� ���̵� ��й�ȣ �ٲ��й�ȣ�� �ָ� ���ϰ��� ��ȯ
					result = maria_db.update_passwd(id, passwd, changed_passwd);
					out.write(result);
					out.flush();
					break;
				}
				// ȸ��Ż��
				case "ȸ��Ż��": {
					id = in.readLine();
					passwd = in.readLine();
					//maria_db.delete_id �޼��带 ����ؼ� �����ͺ��̽��� �ִ� ���̵�� ��й�ȣ�� ����
					result = maria_db.delete_id(id, passwd);
					out.write(result);
					out.flush();
					break;
				}
				// ȸ������
				case "ȸ������": {
					//maria_db.select_user()�޼��带 ����ؼ� �����ͺ��̽����ִ� ���� ���̵� ������
					result = maria_db.select_user();
					out.write(result + "���ֽ��ϴ�.\n");
					out.flush();
					break;
				}
				// ���� �޽��� Ȯ��
				case "�α����": {
					id = in.readLine();
					//maria_db.check_send_message()�޼��带 ����ؼ� ���� �����ͺ��̽� �ִ� ����� �޽������� ���ϰ����� ��ȯ
					result = maria_db.check_send_message(id);
					out.write(result);
					out.flush();
					break; 
				}    
				case "������" :{
				}
				default: {
					//maria_db.log�޼��带 ����ؼ� ���� �޽������� �����ͺ��̽��� ����
					maria_db.log(client, Recive_str);
					//bw.write(client + " : " + Recive_str + "\n");
					//bw.flush();
					for(int i=0;i<al.size();i++) {
					Socket tmp_Socket = (Socket) al.get(i);
					if(tmp_Socket!=R_Socket) {
						out_all = new BufferedWriter(new OutputStreamWriter(tmp_Socket.getOutputStream()));
						out_all.write(client + ":" + Recive_str + "\n");
						out_all.flush();
					}
					}
					break;
				}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null)
					out.close();
				if(bw!= null)
					bw.close();
				if(in != null)
					in.close();
				if(R_Socket != null)
					R_Socket.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setSocket(Socket _socket) {
		R_Socket = _socket;
		al.add(_socket);
	}
}
