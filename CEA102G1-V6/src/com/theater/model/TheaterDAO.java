package com.theater.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TheaterDAO implements TheaterDAO_interface{

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
			"INSERT INTO Theater (movver_no , the_seat , the_seatno) VALUES(?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT the_no, movver_no, the_seat, the_seatno FROM Theater order by the_no";
	private static final String GET_ONE_STMT =
			"SELECT the_no, movver_no, the_seat, the_seatno FROM Theater where the_no = ?";
	private static final String DELETE =
			"DELETE FROM Theater where the_no = ?";
	private static final String UPDATE =
			"UPDATE Theater set movver_no= ?, the_seat= ?, the_seatno= ? where the_no= ?";
	@Override
	public void insert(TheaterVO theaterVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, theaterVO.getMovver_no());
			pstmt.setString(2, theaterVO.getThe_seat());
			pstmt.setString(3, theaterVO.getThe_seatno());
			
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
	public void update(TheaterVO theaterVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt= con.prepareStatement(UPDATE);
			
			pstmt.setInt(1,theaterVO.getMovver_no());
			pstmt.setString(2,theaterVO.getThe_seat());
			pstmt.setString(3, theaterVO.getThe_seatno());
			pstmt.setInt(4, theaterVO.getThe_no());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
				+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(Integer the_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TheaterVO findByPrimaryKey(Integer the_no) {

		TheaterVO theaterVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1,the_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				theaterVO = new TheaterVO();				
				theaterVO.setThe_no(rs.getInt("the_no"));
				theaterVO.setMovver_no(rs.getInt("movver_no"));
				theaterVO.setThe_seat(rs.getString("the_seat"));
				theaterVO.setThe_seatno(rs.getString("the_seatno"));
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
		return theaterVO;
	}

	@Override
	public List<TheaterVO> getAll() {
		List<TheaterVO> list = new ArrayList<TheaterVO>();
		TheaterVO theaterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				theaterVO = new TheaterVO();
				theaterVO.setThe_no(rs.getInt("the_no"));
				theaterVO.setMovver_no(rs.getInt("movver_no"));
				theaterVO.setThe_seat(rs.getString("the_seat"));
				theaterVO.setThe_seatno(rs.getString("the_seatno"));
				list.add(theaterVO);
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

	
	
}
