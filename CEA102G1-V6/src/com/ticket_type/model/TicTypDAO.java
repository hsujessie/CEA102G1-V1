package com.ticket_type.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.food.model.FooCartVO;


public class TicTypDAO implements TicTypDAO_interface{
	
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
			"INSERT INTO ticket_type (movver_no,ide_no,tictyp_price) values (?,?,?)";
	private static final String GET_ALL_STMT=
			"SELECT tictyp_no,movver_no,ide_no,tictyp_price FROM ticket_type order by tictyp_no";
	private static final String GET_ONE_STMT = 
			"SELECT tictyp_no,movver_no,ide_no,tictyp_price FROM ticket_type where tictyp_no = ?";
	private static final String DELETE =
			"DELETE FROM ticket_type where tictyp_no =?";
	private static final String UPDATE = 
			"UPDATE ticket_type set movver_no=?,ide_no=?,tictyp_price=? where tictyp_no =?";
	
	private static final String GET_TICTYPS_BYMOVVERNO_STMT = "SELECT tictyp_no,movver_no,ide_no,tictyp_price FROM ticket_type where MOVVER_NO = ? ORDER BY TICTYP_NO";
	
	@Override
	public void insert(TicTypVO ticket_typeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,ticket_typeVO.getMovver_no());
			pstmt.setInt(2,ticket_typeVO.getIde_no());
			pstmt.setInt(3,ticket_typeVO.getTictyp_price());

			pstmt.executeUpdate();

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
	public void update(TicTypVO ticket_typeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,ticket_typeVO.getMovver_no());
			pstmt.setInt(2,ticket_typeVO.getIde_no());
			pstmt.setInt(3,ticket_typeVO.getTictyp_price());
			pstmt.setInt(4, ticket_typeVO.getTictyp_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(Integer tictyp_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, tictyp_no);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public TicTypVO findByPrimaryKey(Integer tictyp_no) {

		TicTypVO ticket_typeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, tictyp_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticket_typeVO = new TicTypVO();
				ticket_typeVO.setTictyp_no(rs.getInt("tictyp_no"));
				ticket_typeVO.setMovver_no(rs.getInt("movver_no"));
				ticket_typeVO.setIde_no(rs.getInt("ide_no"));
				ticket_typeVO.setTictyp_price(rs.getInt("tictyp_price"));
			}

			// Handle any driver errors
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
		return ticket_typeVO;
	}

	@Override
	public List<TicTypVO> getAll() {
		List<TicTypVO> list = new ArrayList<TicTypVO>();
		TicTypVO ticket_typeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				ticket_typeVO = new TicTypVO();
				ticket_typeVO.setTictyp_no(rs.getInt("tictyp_no"));
				ticket_typeVO.setMovver_no(rs.getInt("movver_no"));
				ticket_typeVO.setIde_no(rs.getInt("ide_no"));
				ticket_typeVO.setTictyp_price(rs.getInt("tictyp_price"));
				list.add(ticket_typeVO); // Store the row in the list
			}

			// Handle any driver errors
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

	@Override
	public Set<TicTypCartVO> getTicTypCart(Map<Integer, Integer> ticTypMap) {
		Set<TicTypCartVO> ticTypCartSet = new LinkedHashSet<TicTypCartVO>();
		TicTypCartVO ticTypCartVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			Set<Integer> keys = ticTypMap.keySet();
			for (Integer ticTypNo : keys) {
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setInt(1, ticTypNo);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {
					ticTypCartVO = new TicTypCartVO();
					
					ticTypCartVO.setTicTypNo(rs.getInt("TICTYP_NO"));
					ticTypCartVO.setTicLisPrice(rs.getInt("TICTYP_PRICE"));
					ticTypCartVO.setIdeNo(rs.getInt("IDE_NO"));
					ticTypCartVO.setTicTypCount(ticTypMap.get(ticTypNo));
					ticTypCartSet.add(ticTypCartVO);
				}
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
		return ticTypCartSet;
	}

	@Override
	public List<TicTypVO> getTicTypsByMovVerNo(Integer movVerNo) {
		List<TicTypVO> list = new ArrayList<TicTypVO>();
		TicTypVO ticket_typeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TICTYPS_BYMOVVERNO_STMT);
			
			pstmt.setInt(1, movVerNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticket_typeVO = new TicTypVO();
				
				ticket_typeVO.setTictyp_no(rs.getInt("tictyp_no"));
				ticket_typeVO.setMovver_no(rs.getInt("movver_no"));
				ticket_typeVO.setIde_no(rs.getInt("ide_no"));
				ticket_typeVO.setTictyp_price(rs.getInt("tictyp_price"));
				
				list.add(ticket_typeVO);
			}

			// Handle any driver errors
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
