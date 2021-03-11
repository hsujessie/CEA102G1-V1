package com.satisfaction.model;

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

public class SatDAO implements SatDAO_interface{
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
		"INSERT INTO SATISFACTION (mov_no,mem_no,sat_rating) VALUES (?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT mov_no,mem_no,sat_rating FROM SATISFACTION ORDER BY sat_rating";
	private static final String GET_ONE_STMT =
		"SELECT mov_no,mem_no,sat_rating FROM SATISFACTION WHERE mov_no=? AND mem_no=?";
	private static final String UPDATE =
		"UPDATE SATISFACTION SET sat_rating=? WHERE mov_no=? AND mem_no=?";

	@Override
	public void insert(SatVO satVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,satVO.getMovNo());
			pstmt.setInt(2,satVO.getMemNo());
			pstmt.setInt(3,satVO.getSatRating());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("SatDAO insert A database error occured. " + se.getMessage());
		
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
	public void update(SatVO satVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);		
			pstmt.setInt(1,satVO.getSatRating());
			pstmt.setInt(2,satVO.getMovNo());
			pstmt.setInt(3,satVO.getMemNo());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("SatDAO update A database error occured. " + se.getMessage());
		
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
	public SatVO findByPrimaryKey(Integer movNo, Integer memNo) {
		SatVO satVO = null;
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
				satVO = new SatVO();
				satVO.setMovNo(rs.getInt("mov_no"));
				satVO.setMemNo(rs.getInt("mem_no"));
				satVO.setSatRating(rs.getInt("sat_rating"));
			}		
			
		} catch(SQLException se) {
			throw new RuntimeException("SatDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		
		return satVO;
	}

	@Override
	public List<SatVO> getAll() {
		List<SatVO> list = new ArrayList<SatVO>();
		SatVO satVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				satVO = new SatVO();
				satVO.setMovNo(rs.getInt("mov_no"));
				satVO.setMemNo(rs.getInt("mem_no"));
				satVO.setSatRating(rs.getInt("sat_rating"));
				list.add(satVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("SatDAO getAll A database error occured. " + se.getMessage());	
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
	public Double getSatRatingAvg(Integer movNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		Double satRatingAvg = null;
		try {
			con = ds.getConnection();

			System.out.println("movNo= " + movNo);
			String getSatRatingAvg = "SELECT AVG(sat_rating) FROM SATISFACTION WHERE mov_no=" + movNo;
			System.out.println("getSatRatingAvg= " + getSatRatingAvg);
			pstmt = con.prepareStatement(getSatRatingAvg);
			
			rs = pstmt.executeQuery();				
			while(rs.next()){
				String satRatingAvgStr = rs.getString(1);
				if(satRatingAvgStr == null) {
					satRatingAvg = null;
				}else {
					satRatingAvg = Double.parseDouble(satRatingAvgStr);
				}			
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("ExpDAO getSatRatingAvg A database error occured. " + se.getMessage());	
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
		
		return satRatingAvg;	
	}

}
