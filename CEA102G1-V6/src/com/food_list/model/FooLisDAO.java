package com.food_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FooLisDAO implements FooLisDAO_interface {
	private static final String INSERT_STMT = "INSERT INTO FOOD_LIST(ORDMAS_NO, FOO_NO, FOOLIS_COUNT, FOOLIS_PRICE) VALUES(?,?,?,?)";
	
	
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

}
