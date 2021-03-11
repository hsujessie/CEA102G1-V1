package com.satisfaction.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SatJDBCDAO implements SatDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,satVO.getMovNo());
			pstmt.setInt(2,satVO.getMemNo());
			pstmt.setInt(3,satVO.getSatRating());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("insert A database error occured. " + se.getMessage());
		} finally {
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
	} 

	@Override
	public void update(SatVO satVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(UPDATE);			
			pstmt.setInt(1,satVO.getSatRating());
			pstmt.setInt(2,satVO.getMovNo());
			pstmt.setInt(3,satVO.getMemNo());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("insert A database error occured. " + se.getMessage());
		} finally {
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
	}

	@Override
	public SatVO findByPrimaryKey(Integer movNo, Integer memNo) {
		SatVO satVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);		
			
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
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
		} catch(SQLException se) {
			throw new RuntimeException("findByPrimaryKey A database error occured. " + se.getMessage());	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();	
			while(rs.next()){
				satVO = new SatVO();
				satVO.setMovNo(rs.getInt("mov_no"));
				satVO.setMemNo(rs.getInt("mem_no"));
				satVO.setSatRating(rs.getInt("sat_rating"));
				list.add(satVO);
			}
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
		} catch(SQLException se) {
			throw new RuntimeException("getAll A database error occured. " + se.getMessage());	
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
	
	public static void main(String[] args) {
		SatJDBCDAO dao = new SatJDBCDAO();
		
		// 新增
		SatVO satVO = new SatVO();
		satVO.setMovNo(1);
		satVO.setMemNo(1);
		satVO.setSatRating(1000);
		dao.insert(satVO);
		
		// 修改
		SatVO satVO2 = new SatVO();
		satVO2.setMovNo(1);
		satVO2.setMemNo(1);
		satVO2.setSatRating(1500);
		dao.update(satVO2);
		
		// 查詢
		SatVO satVO3 = dao.findByPrimaryKey(1,1);
		System.out.print(satVO3.getMovNo() + ",");
		System.out.print(satVO3.getMemNo() + ",");
		System.out.print(satVO3.getSatRating());
		System.out.println("---------------------");
		
		// 查詢
		List<SatVO> list = dao.getAll();
		for (SatVO aSat : list) {
			System.out.print(aSat.getMovNo() + ",");
			System.out.print(aSat.getMemNo() + ",");
			System.out.print(aSat.getSatRating());
			System.out.println();
		}
	}

	@Override
	public Double getSatRatingAvg(Integer movNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
