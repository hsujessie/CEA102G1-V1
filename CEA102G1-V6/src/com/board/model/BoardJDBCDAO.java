package com.board.model;

import java.util.*;
import java.sql.*;
import com.board.model.BoardVO;

public class BoardJDBCDAO implements BoardDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO board (boatyp_NO,boa_CONTENT) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT  boa_NO, boatyp_NO, boa_CONTENT, boa_Time FROM board";
	private static final String GET_ONE_STMT = 
			"SELECT boa_NO , boatyp_NO, boa_CONTENT, boa_Time FROM board where boa_NO = ?";
	private static final String GET_ONE_STMT2 = 
			"SELECT boa_NO , boatyp_NO, boa_CONTENT, boa_Time FROM board where boatyp_NO = ?";
	private static final String DELETE = 
			"DELETE FROM board where boa_NO = ?";
	private static final String UPDATE = 
			"UPDATE board set boatyp_No=?, boa_Content=? where boa_NO = ?";
	
	@Override
	public void insert(BoardVO boardVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, boardVO.getBoatypNo()); // get後面的字母要大寫,打code記得要善用ide
			pstmt.setString(2, boardVO.getBoaContent());
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
	public void update(BoardVO boardVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, boardVO.getBoatypNo());
			pstmt.setString(2, boardVO.getBoaContent());
			pstmt.setInt(3, boardVO.getBoaNo());
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
	public Boolean delete(Integer boaNO) {
		Boolean check = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, boaNO);

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
	public BoardVO findByPrimaryKey(Integer boaNo) {

		BoardVO boardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, boaNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardVO = new BoardVO();
				boardVO.setBoaNo(rs.getInt("boa_No"));
				boardVO.setBoatypNo(rs.getInt("boatyp_No"));
				boardVO.setBoaContent(rs.getString("boa_Content"));
				boardVO.setBoaTime(rs.getDate("boa_Time"));
				
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
		return boardVO;
	}

	@Override
	public List<BoardVO> findByFK(Integer boatypNo) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO boardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT2);

			pstmt.setInt(1, boatypNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardVO = new BoardVO();
				
				boardVO.setBoatypNo(rs.getInt("boatyp_No"));
				boardVO.setBoaNo(rs.getInt("boa_No"));
				boardVO.setBoaContent(rs.getString("boa_Content"));
				boardVO.setBoaTime(rs.getDate("boa_Time"));
				
				list.add(boardVO);
				
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
	
	
	
	
	//
	@Override
	public List<BoardVO> getAll() {
		List<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO boardVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardVO = new BoardVO();
				boardVO.setBoaNo(rs.getInt("boa_No"));
				boardVO.setBoatypNo(rs.getInt("boatyp_No"));
				boardVO.setBoaContent(rs.getString("boa_Content"));
				boardVO.setBoaTime(rs.getDate("boa_Time"));
				
				list.add(boardVO); 
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
	
	
	

	
//	public static void main(String[] args) {
//
//		BoardJDBCDAO dao = new BoardJDBCDAO();
//
//		List<BoardVO> boardList = dao.getAll();
//		for (BoardVO BoardBox : boardList) {
//			System.out.print(BoardBox.getBoaNo() + ",");
//			System.out.print(BoardBox.getBoatypNo() + ",");
//			System.out.print(BoardBox.getBoaContent()+ ",");
//			System.out.print(BoardBox.getBoaTime());
//		System.out.println("動作完成");
//		}
//	}
}