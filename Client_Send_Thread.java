package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client_Send_Thread extends Thread {

	// ����
	private Socket S_Socket;

	// ���۵� ���� �������� ����
	BufferedReader br = null;
	BufferedWriter bw = null;
	BufferedWriter out = null;

	// �����尡 ���۵Ǵ� �κ�
	public void run() {
		try {
			// ���۵� ���� �ʱ�ȭ
			// �Է¹��� ���ڸ� �аų� ȭ�鿡 ����� ���ְ��ϴ� ������ ���۵� ���� ��ü�� ����
			br = new BufferedReader(new InputStreamReader(System.in));
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
			// �������� �޽����� ������ ���۵� ���� ��ü�� ����
			out = new BufferedWriter(new OutputStreamWriter(S_Socket.getOutputStream()));

			// ���ڿ� ���� Send_str, id, passwd, changed_passwd������
			String Send_str, id, passwd, changed_passwd;

			// ���ѹݺ�
			while (true) {

				// Send_str�� �ý��ۿ��� �Է��� ���ڿ��� �о ����
				Send_str = br.readLine();
				// Send_str�� ���ڿ��� ���� ����ġ���� ����
				switch (Send_str) {
				case "�α���": {
					// ���� ������ ������ ���� Send_str�� ���ڿ��� ����
					out.write(Send_str + "\n");
					out.flush();
					try {
						// ȭ�鿡 ���ڿ��� ���
						bw.write("�α���!\n");
						bw.flush();
						bw.write("���̵�� ��й�ȣ�� �Է��ϼ���..\n");
						bw.flush();
						bw.write("���̵� : ");
						bw.flush();
						// id�� �ý��ۿ��� ���� ���ڿ��� ����
						id = br.readLine();
						// ������ ���� id�� ������ ����
						out.write(id + "\n");
						out.flush();

						// ȭ�鿡 ���ڿ� ���
						bw.write("��й�ȣ : ");
						bw.flush();
						// passwd�� �ý��ۿ��� �о�� ���ڿ��� ����
						passwd = br.readLine();

						// ȭ�鿡 ���ڿ� ���
						out.write(passwd + "\n");
						out.flush();

						// IO������ �߻��ϸ� ����
					} catch (IOException e) {
						e.printStackTrace();
					}
					// ����
					break;
				}
				case "ȸ������": {
					out.write(Send_str + "\n");
					out.flush();
					try {

						bw.write("ȸ������!\n");
						bw.flush();
						bw.write("ȸ�� �����ϰ� ���� ���̵�� ��й�ȣ�� �Է����ּ���!\n");
						bw.flush();
						bw.write("���̵� : ");
						bw.flush();

						id = br.readLine();
						out.write(id + "\n");
						out.flush();

						bw.write("��й�ȣ : ");
						bw.flush();

						passwd = br.readLine();
						out.write(passwd + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "��й�ȣ ����": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("��й�ȣ ����!\n");
						bw.flush();
						bw.write("��й�ȣ�� �����ϰ� ���� ���̵� �Է����ּ���!\n");
						bw.flush();

						bw.write("���̵� : ");
						bw.flush();
						id = br.readLine();
						out.write(id + "\n");
						out.flush();

						bw.write("��й�ȣ�� �Է����ּ���!\n");
						bw.flush();
						bw.write("��й�ȣ : ");
						bw.flush();

						passwd = br.readLine();
						out.write(passwd + "\n");
						out.flush();

						bw.write("���ο� ��й�ȣ�� �Է����ּ���!\n");
						bw.flush();
						bw.write("���ο� ��й�ȣ : ");
						bw.flush();

						changed_passwd = br.readLine();
						out.write(changed_passwd + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "ȸ��Ż��": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("���̵�� ��й�ȣ�� �Է��ϼ���..\n");
						bw.flush();
						bw.write("���̵� : ");
						bw.flush();
						id = br.readLine();

						out.write(id + "\n");
						out.flush();

						bw.write("��й�ȣ : ");
						bw.flush();
						passwd = br.readLine();

						out.write(passwd + "\n");
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "ȸ������": {
					out.write(Send_str + "\n");
					out.flush();
					break;
				}
				case "�α����": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("�޽����� ������� ���̵� �Է��ϼ���..\n");
						bw.flush();
						bw.write("���̵� : ");
						bw.flush();

						id = br.readLine();
						out.write(id + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "������": {
					// �����Ⱑ �ԷµǸ� �ý��� ����
					out.write("������\n");
					out.flush();
					System.exit(0);
				}
				default: {
					// /help�� �ԷµǸ� ȭ�鿡 ����ϴ� �κ�
					if (Send_str.equalsIgnoreCase("/help")) {
						bw.write("[�α���] [ȸ������] [��й�ȣ ����] [ȸ��Ż��] [ȸ������] [�α����] [������]\n");
						bw.flush();
					} else {
						// Send_str�� ������ ����
						out.write(Send_str + "\n");
						out.flush();
					}
					break;
				}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// ���۵� ���� ����
				if(out!=null)
					out.close();
				if(bw!=null)
					bw.close();
				if(br!=null)
					br.close();
				// ���� ����
				if(S_Socket!=null)
				S_Socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// ���� ������ S_Socket�� ����
	public void setSocket(Socket _socket) {
		S_Socket = _socket;
	}

}
