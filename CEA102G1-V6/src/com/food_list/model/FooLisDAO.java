package com.food_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FooLisDAO implements FooLisDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO FOOD_LIST(ORDMAS_NO, FOO_NO, FOOLIS_COUNT, FOOLIS_PRICE) VALUES(?,?,?,?)";
	private static final String GET_BYORDMASNO_STMT = "SELECT ORDMAS_NO, FOO_NO, FOOLIS_COUNT, FOOLIS_PRICE FROM FOOD_LIST WHERE ORDMAS_NO=?" ;
	
	
	@Override
	public void insert(FooLisVO fooLisVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, fooLisVO.getOrdMasNo());
			pstmt.setInt(2, fooLisVO.getFooNo());
			pstmt.setInt(3, fooLisVO.getFooLisCount());
			pstmt.setInt(4, fooLisVO.getFooLisPrice());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured(交易失敗). "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public List<FooLisVO> findByOrdMasNo(Integer ordMasNo) {
		List<FooLisVO> list = new ArrayList<FooLisVO>();
		FooLisVO fooLisVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYORDMASNO_STMT);
			
			pstmt.setInt(1, ordMasNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fooLisVO = new FooLisVO();
				
				fooLisVO.setOrdMasNo(rs.getInt("ORDMAS_NO"));
				fooLisVO.setFooNo(rs.getInt("FOO_NO"));
				fooLisVO.setFooLisCount(rs.getInt("FOOLIS_COUNT"));
				fooLisVO.setFooLisPrice(rs.getInt("FOOLIS_PRICE"));
				
				list.add(fooLisVO);
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
