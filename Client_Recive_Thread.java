package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client_Recive_Thread extends Thread {

	private Socket R_Socket;
	BufferedReader in = null;
	BufferedWriter bw = null;

	public void run() {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(R_Socket.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			String Recive_str;

			while (true) {

				Recive_str = in.readLine();
				bw.write(Recive_str + "\n");
				bw.flush();

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if(bw!=null)
					bw.close();
				if(in!=null)
					in.close();
				if(R_Socket!=null)
					R_Socket.close();
			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}

	public void setSocket(Socket _socket) {
		R_Socket = _socket;
	}
}
