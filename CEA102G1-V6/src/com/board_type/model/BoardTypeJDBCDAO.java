package com.board_type.model;

import java.util.*;
import java.sql.*;
import com.board.model.BoardVO;

public class BoardTypeJDBCDAO implements BoardTypeDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO board_type (boatypName) VALUES (?)";
	private static final String DELETE = 
			"DELETE FROM board_type where boatyp_No = ?";
	private static final String GET_ONE_STMT = 
			"SELECT boatyp_No , boatyp_Name FROM board_type where boatyp_No = ?";
	
	private static final String GET_ALL_STMT = 
			"SELECT  boatyp_No, boatyp_Name FROM board_type";
    private static final String UPDATE = 
			"UPDATE board_type set boatyp_No=?, boatyp_Name=? where boatyp_No = ?";
	
    @Override
	public void insert(BoardTypeVO boardTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, boardTypeVO.getBoatypName());
			pstmt.executeUpdate();

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

	}

	@Override
	public void update(BoardTypeVO boardTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, boardTypeVO.getBoatypName());
			pstmt.setInt(2, boardTypeVO.getBoatypNo());
			pstmt.executeUpdate();

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

	}

	@Override
	public void delete(Integer boatypNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, boatypNo);

			pstmt.executeUpdate();

			
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

	}

	@Override
	public BoardTypeVO findByPrimaryKey(Integer boatypNo) {

		BoardTypeVO boardTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, boatypNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardTypeVO = new BoardTypeVO();
				boardTypeVO.setBoatypNo(rs.getInt("boatypNo"));
				boardTypeVO.setBoatypName(rs.getString("boatypName"));
				
				
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());

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
		return boardTypeVO;
	}

	@Override
	public List<BoardTypeVO> getAll() {
		List<BoardTypeVO> list = new ArrayList<BoardTypeVO>();
		BoardTypeVO boardTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardTypeVO = new BoardTypeVO();
				boardTypeVO.setBoatypNo(rs.getInt("boatyp_No"));
				boardTypeVO.setBoatypName(rs.getString("boatyp_Name"));
				
				
				list.add(boardTypeVO); 
			}

			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());

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