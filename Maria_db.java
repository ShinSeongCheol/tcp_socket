package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Maria_db {
	//ResulSet변수 생성
	ResultSet rs = null;
	//프리페어드 스테이트 변수 생성
	PreparedStatement pstmt = null;
	
	//커넥션 변수 생성
	Connection con = null;
	//마리아 디비 연결 메서드
	public void connect() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/debe", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//받은 아이디와 비밀번호를 이용해 로그인하는 메서드
	public String login(String id, String passwd) {
		connect();
		//str 문자열을 ""로 초기화
		String str = "";
		//어레이리스트 객체를 생성
		ArrayList<String> tem_str = new ArrayList<String>();
		try {
			//프리페어드 스테이트를 사용해서 마리아디비 질의어를 생성
			pstmt = con.prepareStatement("select ID from users");
			//리절트 셋에 질의어 실행한 결과를 저장
			rs = pstmt.executeQuery();
			//리절트셋에 다음 부분이 있으면 어레이 리스트에 칼럼이 아이디인 부분을 저장
			while (rs.next()) {
				tem_str.add(rs.getString("ID"));
			}
			//어레이 리스트에 아이디가 있으면 실행하는 조건문
			//없으면 아이디 없습니다! str에 저장
			if (tem_str.contains(id)) {
				//프리페어드 스테이트에 질의어를 저장
				pstmt = con.prepareStatement("select PASSWORD from users where ID=?");
				//질의어에 있는 첫번째 ? 에 아이디에 저장되어있는 값으로 바꿔줌
				pstmt.setString(1, id);
				//리절트 셋에 프리페어드 스테이트를 실행한 결과를 저장
				rs = pstmt.executeQuery();
				//리절트셋에 위치를 한칸 이동
				rs.next();
				//리절트셋에 PASSWORD가 passwd랑 맞으면 로그인성공
				//아니면 로그인 실패
				if (rs.getString("PASSWORD").equals(passwd)) {
					str = "로그인 성공!\n";
				} else {
					str = "비밀번호가 다릅니다!\n";
				}
			} else {
				str = "아이디가 없습니다!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	
	//마리아디비 테이블users에 아이디와 패스워드를 추가하는 메서드
	public void insert_user(String ID, String PASSWORD) {
		connect();
		try {
			pstmt = con.prepareStatement("insert into users (ID,PASSWORD) values (?,?)");
			pstmt.setString(1, ID);
			pstmt.setString(2, PASSWORD);
			pstmt.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}

	//마리아 디비 테이블 users에 있는 비밀번호를  changed_passwd로 바꾸는 메서드
	public String update_passwd(String id, String passwd, String changed_passwd) {
		connect();
		String str = "";
		ArrayList<String> tem_str = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement("select ID from users");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tem_str.add(rs.getString("ID"));
			}
			if (tem_str.contains(id)) {
				pstmt = con.prepareStatement("select PASSWORD from users where ID=?");
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				rs.next();
				if (rs.getString("PASSWORD").equals(passwd)) {
					pstmt = con.prepareStatement("UPDATE users SET PASSWORD = ? WHERE ID = ?");
					pstmt.setString(1,changed_passwd);
					pstmt.setString(2, id);
					rs = pstmt.executeQuery();
					str = "비밀번호가 변경되었습니다.!\n";
				} else {
					str = "비밀번호가 다릅니다!\n";
				}
			} else {
				str = "아이디가 없습니다!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//마리아 디비 테이블에 있는 id와 passwd를 지우는 메서드
	public String delete_id(String id, String passwd) {
		connect();
		String str = "";
		ArrayList<String> tem_str = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement("select ID from users");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tem_str.add(rs.getString("ID"));
			}
			if (tem_str.contains(id)) {
				pstmt = con.prepareStatement("select PASSWORD from users where ID=?");
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				rs.next();
				if (rs.getString("PASSWORD").equals(passwd)) {
					pstmt = con.prepareStatement("DELETE FROM users WHERE ID=?");
					pstmt.setString(1, id);
					rs = pstmt.executeQuery();
					str = "아이디가 삭제되었습니다!\n";
				} else {
					str = "비밀번호가 다릅니다!\n";
				}
			} else {
				str = "아이디가 없습니다!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//마리아디비 테이블에 있는 유저들을 보여주는 메서드
	public String select_user() {
		connect();
		String str = "";
		try {
			pstmt = con.prepareStatement("select ID from users");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				str += "[" + rs.getString("ID") + "] ";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//마리아 디비에 저장된 메시지들을 보여주는 메서드
	public String check_send_message(String id) {
		connect();
		String str = "";
		ArrayList<String> tem_str = new ArrayList<String>();
		int i = 1;
		try {
			pstmt = con.prepareStatement("select ID from log where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tem_str.add(rs.getString("ID"));
			}
			if (tem_str.contains(id)) {
				pstmt = con.prepareStatement("select * from log where id=?");
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					str += "[" + i + "] 보낸메시지 :" + rs.getString("MESSAGE") + "\n";
					i++;
				}
				return str += "더이상 메시지가 없습니다\n";
			} else {
				return str = "없는 아이디입니다.\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//받은 메시지들을 저장하는 메서드
	public void log(String client, String Recive_str) {
		connect();
		try {
			if (client != "client") {
				pstmt = con.prepareStatement("select ID from users where id=?");
				pstmt.setString(1, client);
				rs = pstmt.executeQuery();
				rs.first();
				if (rs.getString("ID").equals(client)) {
					pstmt = con.prepareStatement("insert into log (ID,MESSAGE) values (?,?)");
					pstmt.setString(1, client);
					pstmt.setString(2,Recive_str);
					pstmt.executeQuery();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}
	
	//마리아 디비 연결을 종료하는 메서드
	public void finish() {
		try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
