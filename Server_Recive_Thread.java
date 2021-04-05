package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server_Recive_Thread extends Thread {
	
	//소켓 R_Socket변수를 생성
	private Socket R_Socket;
	//Maria_db 객체를 생성
	private Maria_db maria_db = new Maria_db();
	
	private static ArrayList al = new ArrayList();

	//버퍼드 리더 변수를 생성
	BufferedReader in = null;
	BufferedWriter bw = null;
	BufferedWriter out = null;
	BufferedWriter out_all = null;
	
	String client;
	
	//생성자로 Server_Recive_Thread가 객체가 생성되면 client를 client로 초기화
	public Server_Recive_Thread() {
		client = "client";
	}

	//쓰레드가 시작되는 부분
	public void run() {
		try {
			//버퍼드 리더 객체를 생성하는 부분
			in = new BufferedReader(new InputStreamReader(R_Socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
			out = new BufferedWriter(new OutputStreamWriter(R_Socket.getOutputStream()));
			
			String Recive_str, id, passwd, changed_passwd,result;

			//반복문
			while (true) {
				//소켓에서 읽어온 문자를 Recive_str에 저장
				Recive_str = in.readLine();
				//Recive_str에 따라 스위치문을 실행
				switch (Recive_str) {

				// 로그인
				case "로그인": {
					//아이디와 비밀번호를 소켓에서 읽어옴
					id = in.readLine();
					passwd = in.readLine();
					//maria_db.login(id,passwd)를 사용해서 나온 리턴값을 다시 클라이언트 쪽으로 메시지 보냄
					result = maria_db.login(id, passwd);
					out.write(result);
					out.flush();
					//maria_db.login()메서드가 로그인 성공!을 리턴값으로 반환하면 client를 받은 id로 초기화
					if(result.equals("로그인 성공!\n")) {
						client = id;
					}
					break;
				}
				// 회원가입
				case "회원가입": {
					id = in.readLine();
					passwd = in.readLine();
					//아이디와 비밀번호를 마리아 디비 users에 등록하는 부분
					maria_db.insert_user(id, passwd);
					
					out.write("회원가입 됬습니다.\n");
					out.flush();
					break;
				}
				// 비밀번호 변경
				case "비밀번호 변경": {
					id = in.readLine();
					passwd = in.readLine();
					changed_passwd = in.readLine();
					//maria_db.update_passwd메서를 사용해 아이디 비밀번호 바뀐비밀번호를 주면 리턴값을 반환
					result = maria_db.update_passwd(id, passwd, changed_passwd);
					out.write(result);
					out.flush();
					break;
				}
				// 회원탈퇴
				case "회원탈퇴": {
					id = in.readLine();
					passwd = in.readLine();
					//maria_db.delete_id 메서드를 사용해서 데이터베이스에 있는 아이디와 비밀번호를 삭제
					result = maria_db.delete_id(id, passwd);
					out.write(result);
					out.flush();
					break;
				}
				// 회원보기
				case "회원보기": {
					//maria_db.select_user()메서드를 사용해서 데이터베이스에있는 유저 아이디를 가져옴
					result = maria_db.select_user();
					out.write(result + "가있습니다.\n");
					out.flush();
					break;
				}
				// 보낸 메시지 확인
				case "로그출력": {
					id = in.readLine();
					//maria_db.check_send_message()메서드를 사용해서 나온 데이터베이스 있는 저장된 메시지들을 리턴값으로 반환
					result = maria_db.check_send_message(id);
					out.write(result);
					out.flush();
					break; 
				}    
				case "나가기" :{
				}
				default: {
					//maria_db.log메서드를 사용해서 받은 메시지들을 데이터베이스에 저장
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
