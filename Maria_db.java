package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Maria_db {
	//ResulSet���� ����
	ResultSet rs = null;
	//�������� ������Ʈ ���� ����
	PreparedStatement pstmt = null;
	
	//Ŀ�ؼ� ���� ����
	Connection con = null;
	//������ ��� ���� �޼���
	public void connect() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/debe", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//���� ���̵�� ��й�ȣ�� �̿��� �α����ϴ� �޼���
	public String login(String id, String passwd) {
		connect();
		//str ���ڿ��� ""�� �ʱ�ȭ
		String str = "";
		//��̸���Ʈ ��ü�� ����
		ArrayList<String> tem_str = new ArrayList<String>();
		try {
			//�������� ������Ʈ�� ����ؼ� �����Ƶ�� ���Ǿ ����
			pstmt = con.prepareStatement("select ID from users");
			//����Ʈ �¿� ���Ǿ� ������ ����� ����
			rs = pstmt.executeQuery();
			//����Ʈ�¿� ���� �κ��� ������ ��� ����Ʈ�� Į���� ���̵��� �κ��� ����
			while (rs.next()) {
				tem_str.add(rs.getString("ID"));
			}
			//��� ����Ʈ�� ���̵� ������ �����ϴ� ���ǹ�
			//������ ���̵� �����ϴ�! str�� ����
			if (tem_str.contains(id)) {
				//�������� ������Ʈ�� ���Ǿ ����
				pstmt = con.prepareStatement("select PASSWORD from users where ID=?");
				//���Ǿ �ִ� ù��° ? �� ���̵� ����Ǿ��ִ� ������ �ٲ���
				pstmt.setString(1, id);
				//����Ʈ �¿� �������� ������Ʈ�� ������ ����� ����
				rs = pstmt.executeQuery();
				//����Ʈ�¿� ��ġ�� ��ĭ �̵�
				rs.next();
				//����Ʈ�¿� PASSWORD�� passwd�� ������ �α��μ���
				//�ƴϸ� �α��� ����
				if (rs.getString("PASSWORD").equals(passwd)) {
					str = "�α��� ����!\n";
				} else {
					str = "��й�ȣ�� �ٸ��ϴ�!\n";
				}
			} else {
				str = "���̵� �����ϴ�!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	
	//�����Ƶ�� ���̺�users�� ���̵�� �н����带 �߰��ϴ� �޼���
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

	//������ ��� ���̺� users�� �ִ� ��й�ȣ��  changed_passwd�� �ٲٴ� �޼���
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
					str = "��й�ȣ�� ����Ǿ����ϴ�.!\n";
				} else {
					str = "��й�ȣ�� �ٸ��ϴ�!\n";
				}
			} else {
				str = "���̵� �����ϴ�!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//������ ��� ���̺� �ִ� id�� passwd�� ����� �޼���
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
					str = "���̵� �����Ǿ����ϴ�!\n";
				} else {
					str = "��й�ȣ�� �ٸ��ϴ�!\n";
				}
			} else {
				str = "���̵� �����ϴ�!\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//�����Ƶ�� ���̺� �ִ� �������� �����ִ� �޼���
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
	
	//������ ��� ����� �޽������� �����ִ� �޼���
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
					str += "[" + i + "] �����޽��� :" + rs.getString("MESSAGE") + "\n";
					i++;
				}
				return str += "���̻� �޽����� �����ϴ�\n";
			} else {
				return str = "���� ���̵��Դϴ�.\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finish();
		return str;
	}
	
	//���� �޽������� �����ϴ� �޼���
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
	
	//������ ��� ������ �����ϴ� �޼���
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
