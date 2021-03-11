package com.food_cate.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.food.model.FooVO;


public class FooCatDAO implements FooCatDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO FOOD_CATEGORY(FOOCAT_NAME) VALUES (?)";
	private static final String GET_ALL_STMT = "SELECT FOOCAT_NO, FOOCAT_NAME FROM FOOD_CATEGORY ORDER BY FOOCAT_NO";
	private static final String GET_ONE_STMT = "SELECT FOOCAT_NO, FOOCAT_NAME FROM FOOD_CATEGORY WHERE FOOCAT_NO=?";
	private static final String GET_FOOS_ByFOOCATNO_STMT = "SELECT FOO_NO, FOO_NAME, FOOCAT_NO, FOO_INTRO, FOO_IMG, FOO_PRICE, FOO_STATUS FROM FOOD WHERE FOOCAT_NO = ? ORDER BY FOO_NO";
	private static final String UPDATE = "UPDATE FOOD_CATEGORY SET FOOCAT_NAME=? WHERE FOOCAT_NO=?";
	
	
	@Override
	public void insert(FooCatVO fooCatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, fooCatVO.getFooCatName());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(FooCatVO fooCatVO) {
	}

	@Override
	public List<FooCatVO> getAll() {
		List<FooCatVO> list = new ArrayList<FooCatVO>();
		FooCatVO fooCatVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooCatVO = new FooCatVO();
				
				fooCatVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooCatVO.setFooCatName(rs.getString("FOOCAT_NAME"));
				list.add(fooCatVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}

	@Override
	public FooCatVO findByprimaryKey(Integer fooCatNo) {
		FooCatVO fooCatVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, fooCatNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooCatVO = new FooCatVO();
				
				fooCatVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooCatVO.setFooCatName(rs.getString("FOOCAT_NAME"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return fooCatVO;
	}
	
	public Set<FooVO> getFoosByFooCatNo(Integer fooCatNo) {
		Set<FooVO> set = new LinkedHashSet();
		FooVO fooVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOOS_ByFOOCATNO_STMT);
			
			pstmt.setInt(1, fooCatNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooVO = new FooVO();
				
				fooVO.setFooNo(rs.getInt("FOO_NO"));
				fooVO.setFooName(rs.getString("FOO_NAME"));
				fooVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooVO.setFooIntro(rs.getString("FOO_INTRO"));
				fooVO.setFooImg(rs.getBytes("FOO_IMG"));
				fooVO.setFooPrice(rs.getInt("FOO_PRICE"));
				fooVO.setFooStatus(rs.getInt("FOO_STATUS"));
				set.add(fooVO);
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
		
		return set;
	}
}
