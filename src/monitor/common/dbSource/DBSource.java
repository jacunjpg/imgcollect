package monitor.common.dbSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSource {
	static final String driver = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://127.0.0.1:3306/aloo?characterEncoding=UTF-8&useUnicode=true";
	static final String username = "root";
	static final String password = "123456";
	public static Connection getConn(){
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		
	}
}
