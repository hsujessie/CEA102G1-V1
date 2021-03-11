package com.movie.model;

import java.sql.Connection;
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

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Movie;

public class MovDAO implements MovDAO_interface{
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
		"INSERT INTO MOVIE (mov_name,mov_ver,mov_type,mov_lan,mov_ondate,mov_offdate,mov_durat,mov_rating,mov_ditor,mov_cast,mov_des,mov_pos,mov_tra) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT mov_no,mov_name,mov_ver,mov_type,mov_lan,mov_ondate,mov_offdate,mov_durat,mov_rating,mov_ditor,mov_cast,mov_des,mov_pos,mov_tra,mov_satitotal,mov_satipers,mov_expetotal,mov_expepers FROM MOVIE ORDER BY mov_ondate";
	private static final String GET_ONE_STMT =
		"SELECT mov_no,mov_name,mov_ver,mov_type,mov_lan,mov_ondate,mov_offdate,mov_durat,mov_rating,mov_ditor,mov_cast,mov_des,mov_pos,mov_tra,mov_satitotal,mov_satipers,mov_expetotal,mov_expepers FROM MOVIE WHERE mov_no=?";
	private static final String UPDATE =
		"UPDATE MOVIE SET mov_name=?, mov_ver=?, mov_type=?, mov_lan=?, mov_ondate=?, mov_offdate=?, mov_durat=?, mov_rating=?, mov_ditor=?, mov_cast=?, mov_des=? WHERE mov_no=?";
	private static final String UPDATE_POS =
		"UPDATE MOVIE SET mov_pos=? WHERE mov_no=?";
	private static final String UPDATE_TRA =
		"UPDATE MOVIE SET mov_tra=? WHERE mov_no=?";
	
	@Override
	public void insert(MovVO movVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);		
			pstmt.setString(1,movVO.getMovname());
			pstmt.setString(2,movVO.getMovver());
			pstmt.setString(3,movVO.getMovtype());
			pstmt.setString(4,movVO.getMovlan());
			pstmt.setDate(5,movVO.getMovondate());
			pstmt.setDate(6,movVO.getMovoffdate());
			pstmt.setInt(7,movVO.getMovdurat());
			pstmt.setString(8,movVO.getMovrating());
			pstmt.setString(9,movVO.getMovditor());
			pstmt.setString(10,movVO.getMovcast());
			pstmt.setString(11,movVO.getMovdes());
			pstmt.setBytes(12, movVO.getMovpos());
			pstmt.setBytes(13, movVO.getMovtra());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO insert A database error occured. " + se.getMessage());
		
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
	public void update(MovVO movVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);		
			pstmt.setString(1,movVO.getMovname());
			pstmt.setString(2,movVO.getMovver());
			pstmt.setString(3,movVO.getMovtype());
			pstmt.setString(4,movVO.getMovlan());
			pstmt.setDate(5,movVO.getMovondate());
			pstmt.setDate(6,movVO.getMovoffdate());
			pstmt.setInt(7,movVO.getMovdurat());
			pstmt.setString(8,movVO.getMovrating());
			pstmt.setString(9,movVO.getMovditor());
			pstmt.setString(10,movVO.getMovcast());
			pstmt.setString(11,movVO.getMovdes());
			pstmt.setInt(12,movVO.getMovno());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO update A database error occured. " + se.getMessage());
		
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
	public MovVO findByPrimaryKey(Integer movno) {
		MovVO movVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,movno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				movVO = new MovVO();
				movVO.setMovno(rs.getInt("mov_no"));
				movVO.setMovname(rs.getString("mov_name"));
				movVO.setMovver(rs.getString("mov_ver"));
				movVO.setMovtype(rs.getString("mov_type"));
				movVO.setMovlan(rs.getString("mov_lan"));
				movVO.setMovondate(rs.getDate("mov_ondate"));
				movVO.setMovoffdate(rs.getDate("mov_offdate"));
				movVO.setMovdurat(rs.getInt("mov_durat"));
				movVO.setMovrating(rs.getString("mov_rating"));
				movVO.setMovditor(rs.getString("mov_ditor"));
				movVO.setMovcast(rs.getString("mov_cast"));
				movVO.setMovdes(rs.getString("mov_des"));
				movVO.setMovpos(rs.getBytes("mov_pos"));
				movVO.setMovtra(rs.getBytes("mov_tra"));
				movVO.setMovsatitotal(rs.getInt("mov_satitotal"));
				movVO.setMovsatipers(rs.getInt("mov_satipers"));
				movVO.setMovexpetotal(rs.getInt("mov_expetotal"));
				movVO.setMovexpepers(rs.getInt("mov_expepers"));
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO findByPrimaryKey A database error occured. " + se.getMessage());
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
		return movVO;
	}

	@Override
	public List<MovVO> getAll() {
		List<MovVO> list = new ArrayList<MovVO>();
		MovVO movVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				movVO = new MovVO();
				movVO.setMovno(rs.getInt("mov_no"));
				movVO.setMovname(rs.getString("mov_name"));
				movVO.setMovver(rs.getString("mov_ver"));
				movVO.setMovtype(rs.getString("mov_type"));
				movVO.setMovlan(rs.getString("mov_lan"));
				movVO.setMovondate(rs.getDate("mov_ondate"));
				movVO.setMovoffdate(rs.getDate("mov_offdate"));
				movVO.setMovdurat(rs.getInt("mov_durat"));
				movVO.setMovrating(rs.getString("mov_rating"));
				movVO.setMovditor(rs.getString("mov_ditor"));
				movVO.setMovcast(rs.getString("mov_cast"));
				movVO.setMovdes(rs.getString("mov_des"));
				movVO.setMovpos(rs.getBytes("mov_pos"));
				movVO.setMovtra(rs.getBytes("mov_tra"));
				movVO.setMovsatitotal(rs.getInt("mov_satitotal"));
				movVO.setMovsatipers(rs.getInt("mov_satipers"));
				movVO.setMovexpetotal(rs.getInt("mov_expetotal"));
				movVO.setMovexpepers(rs.getInt("mov_expepers"));
				list.add(movVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO getAll A database error occured. " + se.getMessage());
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
	public List<MovVO> getAll(Map<String, String[]> map) {
		List<MovVO> list = new ArrayList<MovVO>();
		MovVO movVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			String finalSQL = "select * from movie"
			          		   + jdbcUtil_CompositeQuery_Movie.get_WhereCondition(map)
			          		   + " order by mov_ondate";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by MovDAO) = "+finalSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				movVO = new MovVO();
				movVO.setMovno(rs.getInt("mov_no"));
				movVO.setMovname(rs.getString("mov_name"));
				movVO.setMovver(rs.getString("mov_ver"));
				movVO.setMovtype(rs.getString("mov_type"));
				movVO.setMovlan(rs.getString("mov_lan"));
				movVO.setMovondate(rs.getDate("mov_ondate"));
				movVO.setMovoffdate(rs.getDate("mov_offdate"));
				movVO.setMovdurat(rs.getInt("mov_durat"));
				movVO.setMovrating(rs.getString("mov_rating"));
				movVO.setMovditor(rs.getString("mov_ditor"));
				movVO.setMovcast(rs.getString("mov_cast"));
				movVO.setMovdes(rs.getString("mov_des"));
				movVO.setMovpos(rs.getBytes("mov_pos"));
				movVO.setMovtra(rs.getBytes("mov_tra"));
				movVO.setMovsatitotal(rs.getInt("mov_satitotal"));
				movVO.setMovsatipers(rs.getInt("mov_satipers"));
				movVO.setMovexpetotal(rs.getInt("mov_expetotal"));
				movVO.setMovexpepers(rs.getInt("mov_expepers"));
				list.add(movVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO Map getAll A database error occured. " + se.getMessage());
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
	 public void updateMovpos(MovVO movVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_POS);
			
			pstmt.setBytes(1,movVO.getMovpos());
			pstmt.setInt(2,movVO.getMovno());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) { 
			/*==== !!ＷARING!! 有同學寫se.printStackTrace()，而不是RuntimeException，將SQL指令的欄位名稱故意打錯，不會出現錯誤訊息，會讓你誤以為資料到資料庫。 ===*/
			throw new RuntimeException("MovDAO updateMovpos A database error occured. " + se.getMessage()); //RuntimeException(無繼承限制)
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se){
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
	public void updateMovtra(MovVO movVO) {	
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_TRA);

			pstmt.setBytes(1,movVO.getMovtra());
			pstmt.setInt(2,movVO.getMovno());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("MovDAO updateMovtra A database error occured. " + se.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se){
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
}