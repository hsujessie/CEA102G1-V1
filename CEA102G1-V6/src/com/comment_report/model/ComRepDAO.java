package com.comment_report.model;

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

import com.session.model.SesVO;

public class ComRepDAO implements ComRepDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT =
		"INSERT INTO COMMENT_REPORT (com_no,mem_no,comrep_reason,comrep_time,comrep_status) VALUES (?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT comrep_no,com_no,mem_no,comrep_reason,comrep_time,comrep_status FROM COMMENT_REPORT ORDER BY comrep_no DESC";
	private static final String GET_ONE_STMT =
		"SELECT comrep_no,com_no,mem_no,comrep_reason,comrep_time,comrep_status FROM COMMENT_REPORT WHERE comrep_no=? ORDER BY comrep_no DESC";
	private static final String UPDATE =
		"UPDATE COMMENT_REPORT SET comrep_status=? WHERE comrep_no=?";

	@Override
	public void insert(ComRepVO comRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,comRepVO.getComNo());
			pstmt.setInt(2,comRepVO.getMemNo());
			pstmt.setString(3,comRepVO.getComRepReason());
			pstmt.setTimestamp(4,comRepVO.getComRepTime());
			pstmt.setInt(5,comRepVO.getComRepStatus());
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO insert A database error occured. " + se.getMessage());		
		} finally {
			if(pstmt !=  null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(ComRepVO comRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1,comRepVO.getComRepStatus());
			pstmt.setInt(2,comRepVO.getComRepNo());	
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO update A database error occured. " + se.getMessage());
		
		} finally {
			if(pstmt !=  null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public ComRepVO findByPrimaryKey(Integer comRepNo) {
		ComRepVO comRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,comRepNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comRepVO = new ComRepVO();
				comRepVO.setComRepNo(rs.getInt("comrep_no"));
				comRepVO.setComNo(rs.getInt("com_no"));
				comRepVO.setMemNo(rs.getInt("mem_no"));
				comRepVO.setComRepReason(rs.getString("comrep_reason"));
				comRepVO.setComRepTime(rs.getTimestamp("comrep_time"));
				comRepVO.setComRepStatus(rs.getInt("comrep_status"));
			}		
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO findByPrimaryKey A database error occured. " + se.getMessage());	
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return comRepVO;
	}

	@Override
	public List<ComRepVO> getAll() {
		List<ComRepVO> list = new ArrayList<ComRepVO>();
		ComRepVO comRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				comRepVO = new ComRepVO();
				comRepVO.setComRepNo(rs.getInt("comrep_no"));
				comRepVO.setComNo(rs.getInt("com_no"));
				comRepVO.setMemNo(rs.getInt("mem_no"));
				comRepVO.setComRepReason(rs.getString("comrep_reason"));
				comRepVO.setComRepTime(rs.getTimestamp("comrep_time"));
				comRepVO.setComRepStatus(rs.getInt("comrep_status"));
				list.add(comRepVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO getAll A database error occured. " + se.getMessage());	
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}	
		return list;
	}

	@Override
	public List<ComRepVO> findComRepByComReStatus(Integer comReStatus) {
		List<ComRepVO> list = new ArrayList<ComRepVO>();
		ComRepVO comRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			String getComRepByComReStatus = "SELECT * FROM COMMENT_REPORT WHERE comrep_status= " + comReStatus;
			
			pstmt = con.prepareStatement(getComRepByComReStatus);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				comRepVO = new ComRepVO();
				comRepVO.setComRepNo(rs.getInt("comrep_no"));
				comRepVO.setComNo(rs.getInt("com_no"));
				comRepVO.setMemNo(rs.getInt("mem_no"));
				comRepVO.setComRepReason(rs.getString("comrep_reason"));
				comRepVO.setComRepTime(rs.getTimestamp("comrep_time"));
				comRepVO.setComRepStatus(rs.getInt("comrep_status"));
				list.add(comRepVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO findComRepByComReStatus A database error occured. " + se.getMessage());	
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}	
		return list;
	}

	@Override
	public Integer findRepeatedComRep(Integer comNo, Integer memNo, String comRepReason) {
		Integer comrepNoResult = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String findRepeatedComRep = "select comrep_no from comment_report where com_no = " + comNo + " AND mem_no = '"
	          		   + memNo + "' AND comrep_reason = '" + comRepReason + "'";
			
			pstmt = con.prepareStatement(findRepeatedComRep);
			
			rs = pstmt.executeQuery();			
			while(rs.next()){
				Integer comrepNo = rs.getInt("comrep_no");  // only get comrep_no
				if(comrepNo != null) {           /*======= 注意注意 ======= 這邊沒有判斷，會有 java.lang.NullPointerException */ //指標到了下一列，才知道撈到的資料是null					
					comrepNoResult = comrepNo;
				}
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepDAO findRepeatedComRep A database error occured. " + se.getMessage());	
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return comrepNoResult;
	}
}
