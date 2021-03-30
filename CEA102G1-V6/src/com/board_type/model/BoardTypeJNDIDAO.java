package com.board_type.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.board.model.BoardJNDIDAO;
import com.board.model.BoardVO;

public class BoardTypeJNDIDAO implements BoardTypeDAO_interface {

	
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
			"INSERT INTO board_type (boatypName) VALUES (?)";
	private static final String DELETE = 
			"DELETE FROM board_type where boatyp_No = ?";
	private static final String UPDATE = 
			"UPDATE board_type set boatyp_Name=? where boatyp_No = ?";
	private static final String GET_ALL_STMT = 
			"SELECT  boatyp_No, boatyp_Name FROM board_type";
	private static final String GET_ONE_STMT = 
			"SELECT boatyp_No, boatyp_Name FROM board_type where boatyp_No = ?";

	
	@Override
	public void insert(BoardTypeVO boardTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
//			pstmt.setInt(1, boardTypeVO.getBoatypNo());
			pstmt.setString(1, boardTypeVO.getBoatypName());
			
		
			pstmt.executeUpdate();

		
		}
			
		catch (SQLException se) {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, boardTypeVO.getBoatypName());
			pstmt.setInt(2, boardTypeVO.getBoatypNo());

			pstmt.executeUpdate();
		}
		 catch (SQLException se) {
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
		
		int updateCount_EMPs = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, boatypNo);

			
			updateCount_EMPs = pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�����楊���" + boatypNo + "���,���嗾蝑�" + updateCount_EMPs
					+ "�����◤��");
			
		
		}  
		catch (SQLException se) {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, boatypNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardTypeVO = new BoardTypeVO();
				boardTypeVO.setBoatypNo(rs.getInt("boatypNo"));
				boardTypeVO.setBoatypName(rs.getString("boatypName"));
				
			}
		}
			catch (SQLException se) {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardTypeVO = new BoardTypeVO();
				boardTypeVO.setBoatypNo(rs.getInt("boatyp_No"));
				boardTypeVO.setBoatypName(rs.getString("boatyp_Name"));
				
				
				list.add(boardTypeVO); 
			}
		}
		  catch (SQLException se) {
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
	
	

	

	
