package com.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AdmDAO implements AdmDAO_interface{
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	private static final String INSERT_STMT = "INSERT INTO ADMINISTRATOR (ADM_NAME, ADM_IMG, ADM_ACCOUNT, ADM_PASSWORD) VALUES(?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT ADM_NO, ADM_NAME, ADM_ACCOUNT, ADM_PASSWORD, ADM_STATUS FROM ADMINISTRATOR ORDER BY ADM_NO";
	private static final String GET_ONE_STMT = "SELECT ADM_NO, ADM_NAME, ADM_ACCOUNT, ADM_PASSWORD, ADM_STATUS FROM ADMINISTRATOR WHERE ADM_NO=?";
	private static final String UPDATE = "UPDATE ADMINISTRATOR SET ADM_NAME=?, ADM_IMG=?, ADM_ACCOUNT=?, ADM_PASSWORD, ADM_STATUS WHERE ADM_NO=?";
	
	@Override
	public void insert(AdmVO admVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, admVO.getAdmName());
			pstmt.setBytes(2, admVO.getAdmImg());
			pstmt.setString(3, admVO.getAdmAccount());
			pstmt.setString(4, admVO.getAdmPassword());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
	}

	@Override
	public void update(AdmVO admVO) {
	}

	@Override
	public List<AdmVO> getAll() {
		List<AdmVO> list = new ArrayList<AdmVO>();
		AdmVO admVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				admVO = new AdmVO();
				
				admVO.setAdmName(rs.getString("ADM_NAME"));
				admVO.setAdmAccount(rs.getString("ADM_ACCOUNT"));
				admVO.setAdmPassword(rs.getString("ADM_PASSWORD"));
				admVO.setAdmStatus(rs.getInt("ADM_STATUS"));
				
				list.add(admVO);
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return list;
	}

	@Override
	public AdmVO findByprimaryKey(Integer admNo) {
		return null;
	}

}
