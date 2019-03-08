package com.keman.zhgd.common.databaseutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class JdbcUtil {

	public static void close(Connection con, Statement st) {
		close(con);
		close(st);
	}

	public static void close(Connection connection, Statement statement, ResultSet resultSet) {
		close(connection, statement);
		close(resultSet);
	}

	public static void close(Connection con) {
		try {
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void close(Statement st) {
		try {

			st.close();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {

			rs.close();

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public static void close(ResultSet rs, Statement st) {
		close(st);
		close(rs);
	}

	public static Timestamp gettime(Connection con) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Timestamp time = null;

		try {
			pst = con.prepareStatement("select sysdate from dual");
			rs = pst.executeQuery();
			rs.next();
			time = rs.getTimestamp(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pst);
		}
		return time;

	}

}
