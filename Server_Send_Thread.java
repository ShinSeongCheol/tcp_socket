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
			//클라이언트쪽으로 소켓을 통해 메시지 한번 전송
			out.write("[로그인] [회원가입] [비밀번호 변경] [회원탈퇴] [회원보기] [로그출력] [나가기]\n");
			out.flush();
			out.write("/help를 입력하면 명령어가 나옵니다.\n");
			out.flush();
			//무한 반복문
			while (true) {
				//시스템에서 읽어온 문자열을 클라이언트 쪽으로 소켓을 통해 메시지 전달
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
	//받은 소켓을 S_Socket에 저장
	public void setSocket(Socket _socket) {
		S_Socket = _socket;
	}
}
