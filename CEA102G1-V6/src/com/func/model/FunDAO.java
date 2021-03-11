package com.func.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FunDAO implements FunDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ALL_STMT = "SELECT FUN_NO, FUN_NAME FROM FUNC ORDER BY FUN_NO";
	
	@Override
	public Set<FunVO> getAll() {
		Set<FunVO> set = new LinkedHashSet<FunVO>();
		FunVO funVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				funVO = new FunVO();
				
				funVO.setFunNo(rs.getInt("FUN_NO"));
				funVO.setFunName(rs.getString("FUN_NAME"));
				set.add(funVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return set;
	}

}
