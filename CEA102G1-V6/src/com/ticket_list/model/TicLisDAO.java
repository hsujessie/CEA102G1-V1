package com.ticket_list.model;

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

import com.food_list.model.FooLisVO;



public class TicLisDAO implements TicLisDAO_interface{
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO TICKET_LIST(ORDMAS_NO, TICTYP_NO, SES_SEATNO, TICTYP_PRICE) VALUES(?,?,?,?)";
	private static final String GET_BYORDMASNO_STMT = "SELECT TICLIS_NO, ORDMAS_NO, TICTYP_NO, SES_SEATNO, TICTYP_PRICE FROM TICKET_LIST WHERE ORDMAS_NO=?" ;
	@Override
	public void insert(TicLisVO ticLisVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, ticLisVO.getOrdMasNo());
			pstmt.setInt(2, ticLisVO.getTicTypNo());
			pstmt.setString(3, ticLisVO.getSesSeatNo());
			pstmt.setInt(4, ticLisVO.getTicTypPrice());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}		
	}

	@Override
	public List<TicLisVO> findByOrdMasNo(Integer ordMasNo) {
		List<TicLisVO> list = new ArrayList<TicLisVO>();
		TicLisVO ticLisVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYORDMASNO_STMT);
			
			pstmt.setInt(1, ordMasNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ticLisVO = new TicLisVO();
				
				ticLisVO.setTicLisNo(rs.getInt("TICLIS_NO"));
				ticLisVO.setOrdMasNo(rs.getInt("ORDMAS_NO"));
				ticLisVO.setTicTypNo(rs.getInt("TICTYP_NO"));
				ticLisVO.setSesSeatNo(rs.getString("SES_SEATNO"));
				ticLisVO.setTicTypPrice(rs.getInt("TICTYP_PRICE"));
				
				list.add(ticLisVO);
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
