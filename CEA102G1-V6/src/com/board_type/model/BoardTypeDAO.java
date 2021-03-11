package com.board_type.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.board_type.model.BoardTypeDAO;

public class BoardTypeDAO implements BoardTypeDAO_interface {

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BOARD_TYPE (boatyp_Name) VALUES (?)";
	private static final String GET_ALL_STMT = "SELECT boatypNo , boatypName FROM BOARD_TYPE";
	private static final String GET_ONE_STMT = "SELECT deptno , dname, loc FROM dept2 where deptno = ?";
	private static final String DELETE_Board = "DELETE FROM BOARD where boatyp_No = ?";
	private static final String DELETE_BoardType = "DELETE FROM BOARD_TYPE where boatyp_No = ?";	
	
	private static final String UPDATE = "UPDATE BOARD_TYPE set boatyp_Name=? where boatyp_No = ?";

	@Override
	public void insert(BoardTypeVO boardtypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, boardtypeVO.getBoatypName());

pstmt.executeUpdate("set auto_increment_offset=1;");
pstmt.executeUpdate("set auto_increment_increment=1;");
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
	public void update(BoardTypeVO boardtypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, boardtypeVO.getBoatypName());
			pstmt.setInt(2, boardtypeVO.getBoatypNo());

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

		
			pstmt = con.prepareStatement(DELETE_Board);
			pstmt.setInt(1, boatypNo);
			updateCount_EMPs = pstmt.executeUpdate();
		
			pstmt = con.prepareStatement(DELETE_BoardType);
			pstmt.setInt(1, boatypNo);
			pstmt.executeUpdate();

			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除公告編號" + boatypNo + "時,共有幾筆" + updateCount_EMPs
					+ "公告同時被刪除");
			
			
		} catch (SQLException se) {
			if (con != null) {
				try {
				
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
//
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
//
//	@Override
//	public Set<EmpVO> getEmpsByDeptno(Integer deptno) {
//		Set<EmpVO> set = new LinkedHashSet<EmpVO>();
//		EmpVO empVO = null;
//	
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//	
//		try {
//	
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_Emps_ByDeptno_STMT);
//			pstmt.setInt(1, deptno);
//			rs = pstmt.executeQuery();
//	
//			while (rs.next()) {
//				empVO = new EmpVO();
//				empVO.setEmpno(rs.getInt("empno"));
//				empVO.setEname(rs.getString("ename"));
//				empVO.setJob(rs.getString("job"));
//				empVO.setHiredate(rs.getDate("hiredate"));
//				empVO.setSal(rs.getDouble("sal"));
//				empVO.setComm(rs.getDouble("comm"));
//				empVO.setDeptno(rs.getInt("deptno"));
//				set.add(empVO); // Store the row in the vector
//			}
//	
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
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
//		return set;
//	}



}