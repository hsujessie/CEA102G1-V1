package com.expectation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ExpDAO implements ExpDAO_interface{
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
		"INSERT INTO EXPECTATION (mov_no,mem_no,exp_rating) VALUES (?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT mov_no,mem_no,exp_rating FROM EXPECTATION ORDER BY exp_rating";
	private static final String GET_ONE_STMT =
		"SELECT mov_no,mem_no,exp_rating FROM EXPECTATION WHERE mov_no=? AND mem_no=?";
	private static final String UPDATE =
		"UPDATE EXPECTATION SET exp_rating=? WHERE mov_no=? AND mem_no=?";

	@Override
	public void insert(ExpVO expVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,expVO.getMovNo());
			pstmt.setInt(2,expVO.getMemNo());
			pstmt.setInt(3,expVO.getExpRating());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("ExpDAO insert A database error occured. " + se.getMessage());
		
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
	public void update(ExpVO expVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);			
			pstmt.setInt(1,expVO.getExpRating());
			pstmt.setInt(2,expVO.getMovNo());
			pstmt.setInt(3,expVO.getMemNo());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("ExpDAO update A database error occured. " + se.getMessage());
		
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
	public ExpVO findByPrimaryKey(Integer movNo, Integer memNo) {
		ExpVO expVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,movNo);
			pstmt.setInt(2,memNo);
			rs = pstmt.executeQuery();	
			
			while(rs.next()) {
				expVO = new ExpVO();
				expVO.setMovNo(rs.getInt("mov_no"));
				expVO.setMemNo(rs.getInt("mem_no"));
				expVO.setExpRating(rs.getInt("exp_rating"));
			}		
			
		} catch(SQLException se) {
			throw new RuntimeException("ExpDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		return expVO;
	}

	@Override
	public List<ExpVO> getAll() {
		List<ExpVO> list = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				expVO = new ExpVO();
				expVO.setMovNo(rs.getInt("mov_no"));
				expVO.setMemNo(rs.getInt("mem_no"));
				expVO.setExpRating(rs.getInt("exp_rating"));
				list.add(expVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ExpDAO getAll A database error occured. " + se.getMessage());	
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
	public Double getExpRatingAvg(Integer movNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		Double expRatingAvg = null;
		try {
			con = ds.getConnection();

			System.out.println("movNo= " + movNo);
			String getExpRatingAvg = "SELECT AVG(exp_rating) FROM EXPECTATION WHERE mov_no=" + movNo;
			System.out.println("getExpRatingAvg= " + getExpRatingAvg);
			pstmt = con.prepareStatement(getExpRatingAvg);
			
			rs = pstmt.executeQuery();				
			while(rs.next()){
				String expRatingAvgStr = rs.getString(1);
				if(expRatingAvgStr == null) {
					expRatingAvg = null;
				}else {
					expRatingAvg = Double.parseDouble(expRatingAvgStr);
				}			
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ExpDAO getExpRatingAvg A database error occured. " + se.getMessage());	
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
		
		return expRatingAvg;	
	}
	
}
