package com.comment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ComDAO implements ComDAO_interface{
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
		"INSERT INTO COMMENT (mov_no,mem_no,com_time,com_content,com_status) VALUES (?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT com_no,mov_no,mem_no,com_time,com_content,com_status FROM COMMENT ORDER BY com_no";
	private static final String GET_ONE_STMT =
		"SELECT com_no,mov_no,mem_no,com_time,com_content,com_status FROM COMMENT WHERE com_no=?";
	private static final String UPDATE =
		"UPDATE COMMENT SET com_status=? WHERE com_no=?";

	@Override
	public void insert(ComVO comVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,comVO.getMovNo());
			pstmt.setInt(2,comVO.getMemNo());
			pstmt.setTimestamp(3,comVO.getComTime());
			pstmt.setString(4,comVO.getComContent());
			pstmt.setInt(5,comVO.getComStatus());
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("ComDAO insert A database error occured. " + se.getMessage());		
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
	public void update(ComVO comVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1,comVO.getComStatus());
			pstmt.setInt(2,comVO.getComNo());	
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("ComDAO update A database error occured. " + se.getMessage());
		
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
	public ComVO findByPrimaryKey(Integer comNo) {
		ComVO comVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,comNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comVO = new ComVO();
				comVO.setComNo(rs.getInt("com_no"));
				comVO.setMovNo(rs.getInt("mov_no"));
				comVO.setMemNo(rs.getInt("mem_no"));
				comVO.setComTime(rs.getTimestamp("com_time"));
				comVO.setComContent(rs.getString("com_content"));
				comVO.setComStatus(rs.getInt("com_status"));
			}		
			
		} catch(SQLException se) {
			throw new RuntimeException("ComDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		return comVO;
	}

	@Override
	public List<ComVO> getAll() {
		List<ComVO> list = new ArrayList<ComVO>();
		ComVO comVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				comVO = new ComVO();
				comVO.setComNo(rs.getInt("com_no"));
				comVO.setMovNo(rs.getInt("mov_no"));
				comVO.setMemNo(rs.getInt("mem_no"));
				comVO.setComTime(rs.getTimestamp("com_time"));
				comVO.setComContent(rs.getString("com_content"));
				comVO.setComStatus(rs.getInt("com_status"));
				list.add(comVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ComDAO getAll A database error occured. " + se.getMessage());	
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
}
