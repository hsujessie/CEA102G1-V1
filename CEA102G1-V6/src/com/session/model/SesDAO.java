package com.session.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Session;

public class SesDAO implements SesDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}

	
	private static final String INSERT_STMT =
		"INSERT INTO SESSION (mov_no,the_no,ses_date,ses_time,ses_seat_status,ses_seatno) VALUES (?,?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT ses_no,mov_no,the_no,ses_date,ses_time,ses_seat_status,ses_seatno FROM SESSION ORDER BY ses_date DESC,ses_time ASC,the_no ASC";
	private static final String GET_ONE_STMT =
		"SELECT ses_no,mov_no,the_no,ses_date,ses_time,ses_seat_status,ses_seatno FROM SESSION WHERE ses_no=? ORDER BY ses_date DESC,ses_time ASC,the_no ASC";
	private static final String UPDATE =
		"UPDATE SESSION SET ses_date=?,ses_time=? WHERE ses_no=?";

	private static final String UPDATE_SEAT_STATUS = "UPDATE SESSION SET SES_SEAT_STATUS=? WHERE SES_NO=?";
	@Override
	public void insert(SesVO sesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,sesVO.getMovNo());
			pstmt.setInt(2,sesVO.getTheNo());
			pstmt.setDate(3,sesVO.getSesDate());
			pstmt.setTime(4,sesVO.getSesTime());
			pstmt.setString(5,sesVO.getSesSeatStatus());
			pstmt.setString(6,sesVO.getSesSeatNo());
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO insert A database error occured. " + se.getMessage());		
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
	public void update(SesVO sesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setDate(1,sesVO.getSesDate());
			pstmt.setTime(2,sesVO.getSesTime());
			pstmt.setInt(3,sesVO.getSesNo());
			
			pstmt.executeUpdate();
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO update A database error occured. " + se.getMessage());
		
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
	public SesVO findByPrimaryKey(Integer sesNo) {
		SesVO sesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,sesNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sesVO = new SesVO();
				sesVO.setSesNo(rs.getInt("ses_no"));
				sesVO.setMovNo(rs.getInt("mov_no"));
				sesVO.setTheNo(rs.getInt("the_no"));
				sesVO.setSesDate(rs.getDate("ses_date"));
				sesVO.setSesTime(rs.getTime("ses_time"));
				sesVO.setSesSeatStatus(rs.getString("ses_seat_status"));
				sesVO.setSesSeatNo(rs.getString("ses_seatno"));
			}		
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		return sesVO;
	}

	@Override
	public List<SesVO> getAll() {
		List<SesVO> list = new ArrayList<SesVO>();
		SesVO sesVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				sesVO = new SesVO();
				sesVO.setSesNo(rs.getInt("ses_no"));
				sesVO.setMovNo(rs.getInt("mov_no"));
				sesVO.setTheNo(rs.getInt("the_no"));
				sesVO.setSesDate(rs.getDate("ses_date"));
				sesVO.setSesTime(rs.getTime("ses_time"));
				sesVO.setSesSeatStatus(rs.getString("ses_seat_status"));
				sesVO.setSesSeatNo(rs.getString("ses_seatno"));
				list.add(sesVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO getAll A database error occured. " + se.getMessage());	
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
	public List<SesVO> getAll(Map<String, String[]> map) {
		List<SesVO> list = new ArrayList<SesVO>();
		SesVO sesVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			String finalSQL = "select * from session"
			          		   + jdbcUtil_CompositeQuery_Session.get_WhereCondition(map)
			          		   + " ORDER BY ses_date DESC,ses_time ASC";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by SesDAO) = "+finalSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sesVO = new SesVO();sesVO = new SesVO();
				sesVO.setSesNo(rs.getInt("ses_no"));
				sesVO.setMovNo(rs.getInt("mov_no"));
				sesVO.setTheNo(rs.getInt("the_no"));
				sesVO.setSesDate(rs.getDate("ses_date"));
				sesVO.setSesTime(rs.getTime("ses_time"));
				sesVO.setSesSeatStatus(rs.getString("ses_seat_status"));
				list.add(sesVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("SesDAO Map getAll A database error occured. " + se.getMessage());
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
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}

	@Override
	public List<SesVO> findMoviesBySesDate(Date sesDate) {
		List<SesVO> list = new ArrayList<SesVO>();
		SesVO sesVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String getMoviesBySesDate = "select * from session where ses_date = '"
	          		   + sesDate +"' ORDER BY ses_date DESC,ses_time ASC";
			
			System.out.println("getMoviesBySesDate= " + getMoviesBySesDate);
			pstmt = con.prepareStatement(getMoviesBySesDate);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				sesVO = new SesVO();
				sesVO.setSesNo(rs.getInt("ses_no"));
				sesVO.setMovNo(rs.getInt("mov_no"));
				sesVO.setTheNo(rs.getInt("the_no"));
				sesVO.setSesDate(rs.getDate("ses_date"));
				sesVO.setSesTime(rs.getTime("ses_time"));
				sesVO.setSesSeatStatus(rs.getString("ses_seat_status"));
				sesVO.setSesSeatNo(rs.getString("ses_seatno"));
				list.add(sesVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO findMoviesBySesDate A database error occured. " + se.getMessage());	
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
	public List<SesVO> findDistinctSesDate() {
		List<SesVO> list = new ArrayList<SesVO>();
		SesVO sesVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String getDistinctSesDate = "SELECT DISTINCT ses_date FROM session ORDER BY ses_date";
			
			System.out.println("getDistinctSesDate= " + getDistinctSesDate);
			pstmt = con.prepareStatement(getDistinctSesDate);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				sesVO = new SesVO();
				sesVO.setSesDate(rs.getDate("ses_date"));
				list.add(sesVO);
			}
			
		} catch(SQLException se) {
			throw new RuntimeException("SesDAO findDistinctSesDate A database error occured. " + se.getMessage());	
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
	public void updateSeatStatus(Integer sesNo, String sesSeatStatus) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_SEAT_STATUS);
			
			pstmt.setString(1, sesSeatStatus);
			pstmt.setInt(2, sesNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("SesDAO Map getAll A database error occured. " + se.getMessage());
		} finally {
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
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	
}
