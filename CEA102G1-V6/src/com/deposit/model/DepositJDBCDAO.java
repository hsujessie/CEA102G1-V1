package com.deposit.model;
import java.util.*;

import com.deposit.model.DepositDAO_interface;
import com.deposit.model.DepositVO;

import java.sql.*;

public class DepositJDBCDAO implements DepositDAO_interface{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "12qwaszx";
	
	private static final String INSERT_STMT = 
			"INSERT INTO board (mem_No,dep_Amount) VALUES (?, ?)";
	private static final String DELETE = 
			"DELETE FROM deposit where dep_No = ?";
	private static final String UPDATE = 
			"UPDATE deposit set mem_No=?, dep_Amount=? where dep_No = ?";
	private static final String GET_ALL_STMT = 
			"SELECT  dep_No, mem_No, dep_Amount, dep_Time FROM deposit";
	private static final String GET_ONE_STMT = 
			"SELECT dep_No , mem_No, dep_Amount, dep_Time FROM deposit where dep_No = ?";
	
	@Override
	public void insert(DepositVO depositVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, depositVO.getMemNo()); // get後面的字母要大寫,打code記得要善用ide
			pstmt.setInt(2, depositVO.getDepamount());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public void update(DepositVO depositVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, depositVO.getMemNo());
			pstmt.setInt(2, depositVO.getDepamount());
			pstmt.setInt(3, depositVO.getDepNo());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public Boolean delete(Integer depNo) {
		Boolean check = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, depNo);

			pstmt.executeUpdate();
			check = true;
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return check;

	}

	@Override
	public DepositVO findByPrimaryKey(Integer depNo) {

		DepositVO depositVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, depNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				depositVO = new DepositVO();
				depositVO.setDepNo(rs.getInt("dep_No"));
				depositVO.setMemNo(rs.getInt("mem_No"));
				depositVO.setDepamount(rs.getInt("dep_Amount"));
				depositVO.setDepTime(rs.getDate("dep_Time"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return depositVO;
	}

//	@Override
//	public BoardVO findByFK(Integer boatypNo) {
//
//		BoardVO boardVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT2);
//
//			pstmt.setInt(1, boatypNo);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				
//				boardVO = new BoardVO();
//				
//				boardVO.setBoatypNo(rs.getInt("boatyp_No"));
//				boardVO.setBoaNo(rs.getInt("boa_No"));
//				boardVO.setBoaContent(rs.getString("boa_Content"));
//				boardVO.setBoaTime(rs.getDate("boa_Time"));
//				
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return boardVO;
//	}
	
	
	
	
	//
	@Override
	public List<DepositVO> getAll() {
		List<DepositVO> list = new ArrayList<DepositVO>();
		DepositVO depositVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				depositVO = new DepositVO();
				depositVO.setDepNo(rs.getInt("dep_No"));
				depositVO.setMemNo(rs.getInt("mem_No"));
				depositVO.setDepamount(rs.getInt("dep_Amount"));
				depositVO.setDepTime(rs.getDate("dep_Time"));
				
				list.add(depositVO); 
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
