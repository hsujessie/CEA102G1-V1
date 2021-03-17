package com.admin_auth.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdmAutDAO implements AdmAutDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	private static final String INSERT_STMT = "INSERT INTO ADMIN_AUTHORITY(ADM_NO, FUN_NO) VALUES (?,?)";
	private static final String DELETE = "DELETE FROM ADMIN_AUTHORITY WHERE ADM_NO=?";
	private static final String CHECK_ADMAUTH_STMT = "SELECT ADM_NO, FUN_NO FROM ADMIN_AUTHORITY WHERE ADM_NO=? AND FUN_NO=?";
	
	@Override
	public void insert(AdmAutVO admAutVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, admAutVO.getAdmNo());
			pstmt.setInt(2, admAutVO.getFunNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void deleteByAdmNo(Integer admNo, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, admNo);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public boolean checkAdmAut(AdmAutVO admAutVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_ADMAUTH_STMT);
			
			pstmt.setInt(1, admAutVO.getAdmNo());
			pstmt.setInt(2, admAutVO.getFunNo());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return true;
			} else {
				return false;
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
	}

}
