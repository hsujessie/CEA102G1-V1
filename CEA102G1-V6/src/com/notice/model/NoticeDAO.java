package com.notice.model;

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



public class NoticeDAO implements NoticeDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT =
			"INSERT INTO Notice (mem_no , not_content) VALUES(?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT * FROM Notice order by not_no";
	private static final String CHECK_UPDATE =
			"UPDATE Notice set not_status= 1 where not_no= ?";
	private static final String GET_NOTICE_BYMEM_ID_STMT = 
			"SELECT * FROM NOTICE WHERE MEM_ID = ? ORDER BY NOT_ID";
	private static final String GET_ONE_STMT =
			"SELECT * FROM NOTICE WHERE NOT_ID =?";
	@Override
	public void insert(NoticeVO noticeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,noticeVO.getMem_no());
			pstmt.setString(2, noticeVO.getNot_content());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(NoticeVO noticeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_UPDATE);
			
			pstmt.setInt(1, noticeVO.getNot_no());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<NoticeVO> getAll() {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		NoticeVO noticeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				noticeVO = new NoticeVO();
				noticeVO.setNot_no(rs.getInt("not_no"));
				noticeVO.setMem_no(rs.getInt("mem_no"));
				noticeVO.setNot_content(rs.getString("not_content"));
				noticeVO.setNot_status(rs.getInt("not_status"));
				noticeVO.setNot_time(rs.getTimestamp("not_time"));
				
				list.add(noticeVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<NoticeVO> getNoticeByMemno(Integer mem_no) {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		NoticeVO noticeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOTICE_BYMEM_ID_STMT);
			
			pstmt.setInt(1, mem_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				noticeVO = new NoticeVO();
				noticeVO.setNot_no(rs.getInt("not_no"));
				noticeVO.setMem_no(rs.getInt("mem_no"));
				noticeVO.setNot_content(rs.getString("not_content"));
				noticeVO.setNot_status(rs.getInt("not_status"));
				noticeVO.setNot_time(rs.getTimestamp("not_time"));
				
				list.add(noticeVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public NoticeVO findByPrimaryKey(Integer not_no) {

		NoticeVO noticeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1,not_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				noticeVO = new NoticeVO();
				noticeVO.setNot_no(rs.getInt("not_no"));
				noticeVO.setMem_no(rs.getInt("mem_no"));
				noticeVO.setNot_content(rs.getString("not_content"));
				noticeVO.setNot_status(rs.getInt("not_status"));
				noticeVO.setNot_time(rs.getTimestamp("not_time"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return noticeVO;
	}


}
