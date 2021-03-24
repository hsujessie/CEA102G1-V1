package com.food.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.admin.model.AdmVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Admin;
import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Food;

public class FooDAO implements FooDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO FOOD(FOO_NAME, FOOCAT_NO, FOO_IMG, FOO_PRICE) VALUES(?,?,?,?)";
	
	private static final String GET_ALL_STMT = "SELECT FOO_NO, FOO_NAME, FOOCAT_NO, FOO_PRICE, FOO_STATUS FROM FOOD ORDER BY FOO_NO DESC";
	private static final String GET_ONE_STMT = "SELECT FOO_NO, FOO_NAME, FOOCAT_NO, FOO_PRICE, FOO_STATUS FROM FOOD WHERE FOO_NO=?";
	private static final String GET_FOOS_ByFOOSTATUS_STMT = "SELECT FOO_NO, FOO_NAME, FOOCAT_NO, FOO_PRICE, FOO_STATUS FROM FOOD WHERE FOO_STATUS = ? ORDER BY FOO_NO";
	
	private static final String UPDATE = "UPDATE FOOD SET FOO_NAME=?, FOOCAT_NO=?, FOO_IMG=?, FOO_PRICE=?, FOO_STATUS=? WHERE FOO_NO=?";
	private static final String UPDATE_NOIMG = "UPDATE FOOD SET FOO_NAME=?, FOOCAT_NO=?, FOO_PRICE=?, FOO_STATUS=? WHERE FOO_NO=?";
	private static final String UPDATE_STATUS = "UPDATE FOOD SET FOO_STATUS=? WHERE FOO_NO=?";
	
	@Override
	public void insert(FooVO fooVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, fooVO.getFooName());
			pstmt.setInt(2, fooVO.getFooCatNo());
			pstmt.setBytes(3, fooVO.getFooImg());
			pstmt.setInt(4, fooVO.getFooPrice());
			
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
	public void update(FooVO fooVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, fooVO.getFooName());
			pstmt.setInt(2, fooVO.getFooCatNo());
			pstmt.setBytes(3, fooVO.getFooImg());
			pstmt.setInt(4, fooVO.getFooPrice());
			pstmt.setInt(5, fooVO.getFooStatus());
			pstmt.setInt(6, fooVO.getFooNo());
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
	public List<FooVO> getAll() {
		List<FooVO> list = new ArrayList<FooVO>();
		FooVO fooVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooVO = new FooVO();
				
				fooVO.setFooNo(rs.getInt("FOO_NO"));
				fooVO.setFooName(rs.getString("FOO_NAME"));
				fooVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooVO.setFooPrice(rs.getInt("FOO_PRICE"));
				fooVO.setFooStatus(rs.getInt("FOO_STATUS"));
				list.add(fooVO);
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

	@Override
	public FooVO findByprimaryKey(Integer fooNo) {
		FooVO fooVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, fooNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooVO = new FooVO();
				
				fooVO.setFooNo(rs.getInt("FOO_NO"));
				fooVO.setFooName(rs.getString("FOO_NAME"));
				fooVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooVO.setFooPrice(rs.getInt("FOO_PRICE"));
				fooVO.setFooStatus(rs.getInt("FOO_STATUS"));
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
		
		return fooVO;
	}
	

	@Override
	public Set<FooVO> getFoosByFooStatus(Integer fooStatus) {
		Set<FooVO> set = new LinkedHashSet<FooVO>();
		FooVO fooVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOOS_ByFOOSTATUS_STMT);
			
			pstmt.setInt(1, fooStatus);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooVO = new FooVO();
				
				fooVO.setFooNo(rs.getInt("FOO_NO"));
				fooVO.setFooName(rs.getString("FOO_NAME"));
				fooVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
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

	@Override
	public void updateNoImg(FooVO fooVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_NOIMG);
			
			pstmt.setString(1, fooVO.getFooName());
			pstmt.setInt(2, fooVO.getFooCatNo());
			pstmt.setInt(3, fooVO.getFooPrice());
			pstmt.setInt(4, fooVO.getFooStatus());
			pstmt.setInt(5, fooVO.getFooNo());
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
	public void changeStatus(Integer fooNo, Integer fooStatus) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			
			switch(fooStatus) {
				case 0 :
					pstmt.setInt(1, 1);
					break;
				case 1 :
					pstmt.setInt(1, 0);
					break;
			}
			pstmt.setInt(2, fooNo);
			
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
	public Set<FooCartVO> getFooCart(Map<Integer, Integer> foodMap) {
		Set<FooCartVO> fooCartSet = new LinkedHashSet<FooCartVO>();
		FooCartVO fooCartVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			Set<Integer> keys = foodMap.keySet();
			for (Integer fooNo : keys) {
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setInt(1, fooNo);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {
					fooCartVO = new FooCartVO();
					
					fooCartVO.setFooNo(rs.getInt("FOO_NO"));
					fooCartVO.setFooPrice(rs.getInt("FOO_PRICE"));
					fooCartVO.setFooName(rs.getString("FOO_NAME"));
					fooCartVO.setFooCount(foodMap.get(fooNo));
					
					fooCartSet.add(fooCartVO);
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
		
		return fooCartSet;
	}

	@Override
	public List<FooVO> getAll(Map<String, String[]> map) {
		List<FooVO> list = new ArrayList<FooVO>();
		FooVO fooVO = new FooVO();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			
			String finalSQL = "select * from food "+ jdbcUtil_CompositeQuery_Food.get_WhereCondition(map) + "order by foo_no desc";
			
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooVO = new FooVO();
				
				fooVO.setFooNo(rs.getInt("FOO_NO"));
				fooVO.setFooName(rs.getString("FOO_NAME"));
				fooVO.setFooCatNo(rs.getInt("FOOCAT_NO"));
				fooVO.setFooPrice(rs.getInt("FOO_PRICE"));
				fooVO.setFooStatus(rs.getInt("FOO_STATUS"));
				list.add(fooVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
