package com.expectation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpJDBCDAO implements ExpDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,expVO.getMovNo());
			pstmt.setInt(2,expVO.getMemNo());
			pstmt.setInt(3,expVO.getExpRating());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ExpJDBCDAO insert A database error occured. " + se.getMessage());		
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(UPDATE);			
			pstmt.setInt(1,expVO.getExpRating());
			pstmt.setInt(2,expVO.getMovNo());
			pstmt.setInt(3,expVO.getMemNo());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ExpJDBCDAO update A database error occured. " + se.getMessage());
		
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
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
			
		}  catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
		} catch(SQLException se) {
			throw new RuntimeException("ExpJDBCDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				expVO = new ExpVO();
				expVO.setMovNo(rs.getInt("mov_no"));
				expVO.setMemNo(rs.getInt("mem_no"));
				expVO.setExpRating(rs.getInt("exp_rating"));
				list.add(expVO);
			}
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
		} catch(SQLException se) {
			throw new RuntimeException("ExpJDBCDAO getAll A database error occured. " + se.getMessage());	
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
		

		Double expRatingAvg = null; // 包裝類別才有null   //基本資料別沒有null	//這邊宣告null，是因為讓 前台 EL 取資料，若是空值，就不顯示	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			System.out.println("movNo= " + movNo);
			String getExpRatingAvg = "SELECT AVG(exp_rating) FROM EXPECTATION WHERE mov_no=" + movNo;
			
			pstmt = con.prepareStatement(getExpRatingAvg);
			
			rs = pstmt.executeQuery();	
			
			while(rs.next()) {         /*======= 注意注意 ======= 這邊沒有寫rs.next()，會有 Exception in thread "main" java.lang.RuntimeException */  //Before start of result set因為指標現在在column name這列，沒到下一列，會有這個exception!!
				String expRatingAvgStr = rs.getString(1);
				if(expRatingAvgStr == null) {        /*======= 注意注意 ======= 這邊沒有判斷，會有 java.lang.NullPointerException */	                  //指標到了下一列，才知道撈到的資料是null
					System.out.println("is null");
				}else {
					System.out.println("not null");
					expRatingAvg = Double.parseDouble(expRatingAvgStr);   //java.lang.NullPointerException 無法被parse
				}
			}	
			
			
		     
			
	/*=================================================================================
		    不能用以下setMovNo、setMemNo、setExpRating,因為欄位名稱剩一個，叫 AVG(exp_rating)
	  =================================================================================*/
//			while(rs.next()){
//				expVO = new ExpVO();
//				expVO.setMovNo(rs.getInt("mov_no"));
//				expVO.setMemNo(rs.getInt("mem_no"));
//				expVO.setExpRating(rs.getInt("exp_rating"));
//			}
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
		} catch(SQLException se) {
			throw new RuntimeException("ExpJDBC getExpRatingAvg A database error occured. " + se.getMessage());	
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
	
	
	public static void main(String[] args) {
		ExpJDBCDAO dao = new ExpJDBCDAO();
		
		// 新增
//		ExpVO expVO = new ExpVO();
//		expVO.setMovNo(1);
//		expVO.setMemNo(1);
//		expVO.setExpRating(1000);
//		dao.insert(expVO);
//		
//		// 修改
//		ExpVO expVO2 = new ExpVO();
//		expVO2.setMovNo(1);
//		expVO2.setMemNo(1);
//		expVO2.setExpRating(1500);
//		dao.update(expVO2);
//		
//		// 查詢
//		ExpVO expVO3 = dao.findByPrimaryKey(1,1);
//		System.out.print(expVO3.getMovNo() + ",");
//		System.out.print(expVO3.getMemNo() + ",");
//		System.out.print(expVO3.getExpRating());
//		System.out.println("---------------------");
//		
//		// 查詢
//		List<ExpVO> list = dao.getAll();
//		for (ExpVO aExp : list) {
//			System.out.print(aExp.getMovNo() + ",");
//			System.out.print(aExp.getMemNo() + ",");
//			System.out.print(aExp.getExpRating());
//			System.out.println();
//		}

		// 查詢
		Double expRatingAvg = dao.getExpRatingAvg(5);
		System.out.print( "Avg: " + expRatingAvg);
		System.out.println("---------------------");
	}

}
