package com.deposit.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.deposit.model.DepositVO;
import com.board.model.BoardDAO_interface;
import com.deposit.model.DepositDAO;

public class DepositDAO implements DepositDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenem");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
		
	
	

	private static final String INSERT_STMT = 
			"INSERT INTO board (dep_No,dep_Amount) VALUES (?, ?)";
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, depositVO.getDepNo()); 
			pstmt.setInt(2, depositVO.getDepamount());
			
//			pstmt.executeUpdate("set auto_increment_offset=1;");
//			pstmt.executeUpdate("set auto_increment_increment=1;");
			
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
	public void update(DepositVO depositVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, depositVO.getMemNo());
			pstmt.setInt(2, depositVO.getDepamount());
			pstmt.setInt(3, depositVO.getDepNo());
			
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
	public Boolean delete(Integer depNo) {
		Boolean check = false;
		int updateCount_EMPs = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
//			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, depNo);

//			pstmt.executeUpdate();
			updateCount_EMPs = pstmt.executeUpdate();
			
//			con.commit();
//			con.setAutoCommit(true);
			System.out.println("刪除公告編號" + depNo + "時,共有幾筆" + updateCount_EMPs
					+ "公告同時被刪除");
			check = true;
		
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
		return check;

	}

	@Override
	public DepositVO findByPrimaryKey(Integer depNo) {

		DepositVO depositVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
		return depositVO;
	}

	@Override
	public List<DepositVO> getAll() {
		List<DepositVO> list = new ArrayList<DepositVO>();
		DepositVO depositVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
