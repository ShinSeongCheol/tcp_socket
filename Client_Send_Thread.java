package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client_Send_Thread extends Thread {

	// 소켓
	private Socket S_Socket;

	// 버퍼드 리더 변수들을 생성
	BufferedReader br = null;
	BufferedWriter bw = null;
	BufferedWriter out = null;

	// 스레드가 시작되는 부분
	public void run() {
		try {
			// 버퍼드 리더 초기화
			// 입력받은 문자를 읽거나 화면에 출력할 수있게하는 변수를 버퍼드 리더 객체로 생성
			br = new BufferedReader(new InputStreamReader(System.in));
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
			// 소켓으로 메시지를 보내는 버퍼드 리더 객체를 생성
			out = new BufferedWriter(new OutputStreamWriter(S_Socket.getOutputStream()));

			// 문자열 변수 Send_str, id, passwd, changed_passwd를생성
			String Send_str, id, passwd, changed_passwd;

			// 무한반복
			while (true) {

				// Send_str에 시스템에서 입력한 문자열을 읽어서 저장
				Send_str = br.readLine();
				// Send_str의 문자열에 따라 스위치문을 실행
				switch (Send_str) {
				case "로그인": {
					// 서버 쪽으로 소켓을 통해 Send_str의 문자열을 전송
					out.write(Send_str + "\n");
					out.flush();
					try {
						// 화면에 문자열을 출력
						bw.write("로그인!\n");
						bw.flush();
						bw.write("아이디와 비밀번호를 입려하세요..\n");
						bw.flush();
						bw.write("아이디 : ");
						bw.flush();
						// id에 시스템에서 읽은 문자열을 저장
						id = br.readLine();
						// 소켓을 통해 id를 서버로 보냄
						out.write(id + "\n");
						out.flush();

						// 화면에 문자열 출력
						bw.write("비밀번호 : ");
						bw.flush();
						// passwd에 시스템에서 읽어온 문자열을 저장
						passwd = br.readLine();

						// 화면에 문자열 출력
						out.write(passwd + "\n");
						out.flush();

						// IO에러가 발생하면 실행
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 멈춤
					break;
				}
				case "회원가입": {
					out.write(Send_str + "\n");
					out.flush();
					try {

						bw.write("회원가입!\n");
						bw.flush();
						bw.write("회원 가입하고 싶은 아이디와 비밀번호를 입력해주세요!\n");
						bw.flush();
						bw.write("아이디 : ");
						bw.flush();

						id = br.readLine();
						out.write(id + "\n");
						out.flush();

						bw.write("비밀번호 : ");
						bw.flush();

						passwd = br.readLine();
						out.write(passwd + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "비밀번호 변경": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("비밀번호 변경!\n");
						bw.flush();
						bw.write("비밀번호를 변경하고 싶은 아이디를 입력해주세요!\n");
						bw.flush();

						bw.write("아이디 : ");
						bw.flush();
						id = br.readLine();
						out.write(id + "\n");
						out.flush();

						bw.write("비밀번호를 입력해주세요!\n");
						bw.flush();
						bw.write("비밀번호 : ");
						bw.flush();

						passwd = br.readLine();
						out.write(passwd + "\n");
						out.flush();

						bw.write("새로운 비밀번호를 입력해주세요!\n");
						bw.flush();
						bw.write("새로운 비밀번호 : ");
						bw.flush();

						changed_passwd = br.readLine();
						out.write(changed_passwd + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "회원탈퇴": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("아이디와 비밀번호를 입력하세요..\n");
						bw.flush();
						bw.write("아이디 : ");
						bw.flush();
						id = br.readLine();

						out.write(id + "\n");
						out.flush();

						bw.write("비밀번호 : ");
						bw.flush();
						passwd = br.readLine();

						out.write(passwd + "\n");
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "회원보기": {
					out.write(Send_str + "\n");
					out.flush();
					break;
				}
				case "로그출력": {
					out.write(Send_str + "\n");
					out.flush();
					try {
						bw.write("메시지를 보고싶은 아이디를 입력하세요..\n");
						bw.flush();
						bw.write("아이디 : ");
						bw.flush();

						id = br.readLine();
						out.write(id + "\n");
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				case "나가기": {
					// 나가기가 입력되면 시스템 종료
					out.write("나가기\n");
					out.flush();
					System.exit(0);
				}
				default: {
					// /help가 입력되면 화면에 출력하는 부분
					if (Send_str.equalsIgnoreCase("/help")) {
						bw.write("[로그인] [회원가입] [비밀번호 변경] [회원탈퇴] [회원보기] [로그출력] [나가기]\n");
						bw.flush();
					} else {
						// Send_str을 서버로 보냄
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
				// 버퍼드 리더 종료
				if(out!=null)
					out.close();
				if(bw!=null)
					bw.close();
				if(br!=null)
					br.close();
				// 소켓 종료
				if(S_Socket!=null)
				S_Socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// 받은 소켓을 S_Socket에 저장
	public void setSocket(Socket _socket) {
		S_Socket = _socket;
	}

}
