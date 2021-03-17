package com.movie_version.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.theater.model.TheVO;
import com.ticket_type.model.TicTypVO;

import java.sql.*;

public class MovVerDAO implements MovVerDAO_interface{

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
			"INSERT INTO Movie_version (movver_name) VALUES (?)";
	private static final String GET_ALL_STMT =
			"SELECT movver_no,movver_name FROM Movie_version order by movver_no";
	private static final String GET_ONE_STMT =
			"SELECT movver_no,movver_name FROM Movie_version where movver_no = ?";
	private static final String DELETE =
			"DELETE FROM Movie_version where movver_no = ?";
	private static final String UPDATE = 
			"UPDATE Movie_version set movver_name= ? where movver_no= ?";
	private static final String GET_Theaters_ByMovver_no_STMT = 
			"SELECT the_no,movver_no,the_seat,the_seatno FROM theater where movver_no = ? order by the_no";
	private static final String GET_Ticket_type_ByMovver_no_STMT=
			"SELECT tictyp_no, movver_no, ide_no, tictyp_price FROM Ticket_type where movver_no =? order by tictyp_no";

	@Override
	public void insert(MovVerVO movie_versionVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, movie_versionVO.getMovver_name());
			
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
	public void update(MovVerVO movie_versionVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, movie_versionVO.getMovver_name());
			pstmt.setInt(2, movie_versionVO.getMovver_no());
			
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
	public void delete(Integer movver_no) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MovVerVO FindByPrimaryKey(Integer movver_no) {
		
		MovVerVO movie_versionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, movver_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				movie_versionVO = new MovVerVO();
				movie_versionVO.setMovver_no(rs.getInt("movver_no"));
				movie_versionVO.setMovver_name(rs.getString("movver_name"));
				
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
		return movie_versionVO;
	}
	@Override
	public List<MovVerVO> getall() {
		List<MovVerVO> list = new ArrayList<MovVerVO>();
		MovVerVO movie_versionVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				movie_versionVO = new MovVerVO();
				movie_versionVO.setMovver_no(rs.getInt("movver_no"));
				movie_versionVO.setMovver_name(rs.getString("movver_name"));
				list.add(movie_versionVO);
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
	public Set<TheVO> getTheatersByMovver_no(Integer movver_no) {
		Set<TheVO> set = new LinkedHashSet<TheVO>();
		TheVO theaterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Theaters_ByMovver_no_STMT);
			pstmt.setInt(1, movver_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				theaterVO = new TheVO(); 
				theaterVO.setThe_no(rs.getInt("the_no"));
				theaterVO.setMovver_no(rs.getInt("movver_no"));
				theaterVO.setThe_seat(rs.getString("the_seat"));
				theaterVO.setThe_seatno(rs.getString("the_seatno"));
				set.add(theaterVO);				
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
	@Override
	public Set<TicTypVO> getTicket_typeByMovver_no(Integer movver_no) {
		Set<TicTypVO> set = new LinkedHashSet<TicTypVO>();
		TicTypVO ticket_typeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Ticket_type_ByMovver_no_STMT);
			pstmt.setInt(1, movver_no);
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
