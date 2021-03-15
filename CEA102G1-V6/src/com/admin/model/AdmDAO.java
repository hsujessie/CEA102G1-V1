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

import com.admin_auth.model.AdmAutService;
import com.admin_auth.model.AdmAutVO;


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
	
	private static final String INSERT_STMT = "INSERT INTO ADMINISTRATOR (ADM_NAME, ADM_IMG, ADM_ACCOUNT, ADM_PASSWORD, ADM_MAIL) VALUES(?,?,?,?,?)";
	
	private static final String GET_ALL_STMT = "SELECT ADM_NO, ADM_NAME, ADM_ACCOUNT, ADM_PASSWORD, ADM_MAIL, ADM_STATUS FROM ADMINISTRATOR ORDER BY ADM_NO";
	private static final String GET_ONE_STMT = "SELECT ADM_NO, ADM_NAME, ADM_ACCOUNT, ADM_PASSWORD, ADM_MAIL, ADM_STATUS FROM ADMINISTRATOR WHERE ADM_NO=?";
	private static final String GET_AUTHS_BYADMNO_STMT = "SELECT ADM_NO, FUN_NO FROM ADMIN_AUTHORITY WHERE ADM_NO=?";
	
	
	private static final String UPDATE = "UPDATE ADMINISTRATOR SET ADM_NAME=?, ADM_IMG=?, ADM_ACCOUNT=?, ADM_PASSWORD=?, ADM_MAIL=?, ADM_STATUS=? WHERE ADM_NO=?";
	
	@Override
	public void insertWithAuth(AdmVO admVO, String[] funNoArray) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet key = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false); 
			
			String[] cols = {"ADM_NO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, admVO.getAdmName());
			pstmt.setBytes(2, admVO.getAdmImg());
			pstmt.setString(3, admVO.getAdmAccount());
			pstmt.setString(4, admVO.getAdmPassword());
			pstmt.setString(5, admVO.getAdmMail());
			
			pstmt.executeUpdate();
			
			Integer admNo = null;
			key = pstmt.getGeneratedKeys();
			if (key.next()) {
				admNo = new Integer(key.getString(1));
			} else {
				throw new RuntimeException("未取得自增主鍵");
			}
			key.close();
			
			AdmAutService admAutSvc = new AdmAutService();
			for (int i = 0; i < funNoArray.length; i++) {
				Integer funNo = new Integer(funNoArray[i]);
				admAutSvc.addAdmAut(admNo, funNo, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					throw new RuntimeException("A rollback error occured. " + e.getMessage());
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
	public void update(AdmVO admVO, String[] funNoArray) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false); 
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, admVO.getAdmName());
			pstmt.setBytes(2, admVO.getAdmImg());
			pstmt.setString(3, admVO.getAdmAccount());
			pstmt.setString(4, admVO.getAdmPassword());
			pstmt.setString(5, admVO.getAdmMail());
			pstmt.setInt(6, admVO.getAdmStatus());
			pstmt.setInt(7, admVO.getAdmNo());
			
			pstmt.executeUpdate();
			
			Integer admNo = new Integer(admVO.getAdmNo());
			AdmAutService admAutSvc = new AdmAutService();
			
			admAutSvc.deleteAdmAut(admNo, con);
			
			for (int i = 0; i < funNoArray.length; i++) {
				Integer funNo = new Integer(funNoArray[i]);
				admAutSvc.addAdmAut(admNo, funNo, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					throw new RuntimeException("A rollback error occured. " + e.getMessage());
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
				
				admVO.setAdmNo(rs.getInt("ADM_NO"));
				admVO.setAdmName(rs.getString("ADM_NAME"));
				admVO.setAdmAccount(rs.getString("ADM_ACCOUNT"));
				admVO.setAdmPassword(rs.getString("ADM_PASSWORD"));
				admVO.setAdmMail(rs.getString("ADM_MAIL"));
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
		AdmVO admVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, admNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				admVO = new AdmVO();
				
				admVO.setAdmNo(rs.getInt("ADM_NO"));
				admVO.setAdmName(rs.getString("ADM_NAME"));
				admVO.setAdmAccount(rs.getString("ADM_ACCOUNT"));
				admVO.setAdmPassword(rs.getString("ADM_PASSWORD"));
				admVO.setAdmMail(rs.getString("ADM_MAIL"));
				admVO.setAdmStatus(rs.getInt("ADM_STATUS"));
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
		return admVO;
	}

	@Override
	public void updateNoImg(AdmVO admVO) {
		
	}

	@Override
	public List<AdmAutVO> getAuthsByAdmNo(Integer admNo) {
		List<AdmAutVO> list = new ArrayList<AdmAutVO>();
		AdmAutVO admAutVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_AUTHS_BYADMNO_STMT);
			
			pstmt.setInt(1, admNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				admAutVO = new AdmAutVO();
				
				admAutVO.setAdmNo(rs.getInt("ADM_NO"));
				admAutVO.setFunNo(rs.getInt("FUN_NO"));
				
				list.add(admAutVO);
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
	
	

}
