package com.identity.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ticket_type.model.TicTypVO;

public class IdeDAO implements IdeDAO_interface{
	
	
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
			"INSERT INTO Theater (ide_name) VALUES(?)";
	private static final String GET_ALL_STMT =
			"SELECT ide_no, ide_name FROM Identity order by ide_no";
	private static final String GET_ONE_STMT =
			"SELECT ide_no, ide_name FROM Identity where ide_no = ?";
	private static final String DELETE =
			"DELETE FROM Identity where ide_no = ?";
	private static final String UPDATE =
			"UPDATE Identity set ide_name= ? where ide_no= ?";
	private static final String GET_Ticket_type_ByIde_no_STMT=
			"SELECT tictyp_no, movver_no, ide_no, tictyp_price FROM Ticket_type where ide_no =? order by tictyp_no";
	@Override
	public void insert(IdeVO identityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, identityVO.getIde_name());
			
			pstmt.executeUpdate();
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
	public void update(IdeVO identityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt= con.prepareStatement(UPDATE);
			
			pstmt.setString(1, identityVO.getIde_name());
			pstmt.setInt(2, identityVO.getIde_no());
			
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
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
				
	}
	
	@Override
	public void delete(Integer ide_no) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IdeVO findByPrimaryKey(Integer ide_no) {

		IdeVO identityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1,ide_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				identityVO = new IdeVO();				
				identityVO.setIde_no(rs.getInt("ide_no"));
				identityVO.setIde_name(rs.getString("ide_name"));
			}
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
		return identityVO;
		}
	
	@Override
	public List<IdeVO> getAll() {
		List<IdeVO> list = new ArrayList<IdeVO>();
		IdeVO identityVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				identityVO = new IdeVO();
				identityVO.setIde_no(rs.getInt("ide_no"));
				identityVO.setIde_name(rs.getString("ide_name"));
				list.add(identityVO);
			}
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
	public Set<TicTypVO> getTicket_typesByIde_no(Integer ide_no) {
		Set<TicTypVO> set = new LinkedHashSet<TicTypVO>();
		TicTypVO ticket_typeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Ticket_type_ByIde_no_STMT);
			pstmt.setInt(1, ide_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticket_typeVO = new TicTypVO();
				ticket_typeVO.setTictyp_no(rs.getInt("tictyp_no"));
				ticket_typeVO.setMovver_no(rs.getInt("movver_no"));
				ticket_typeVO.setIde_no(rs.getInt("ide_no"));
				ticket_typeVO.setTictyp_price(rs.getInt("tictyp_price"));
				set.add(ticket_typeVO);				
			}
		} catch  (SQLException se) {
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
		return set;
		
	}
	

}
