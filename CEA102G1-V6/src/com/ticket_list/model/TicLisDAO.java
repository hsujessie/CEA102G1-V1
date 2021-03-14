package com.ticket_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.ticket_type.model.Ticket_typeVO;

public class TicLisDAO implements TicLisDAO_interface{
	private static final String INSERT_STMT = "INSERT INTO TICKET_LIST(ORDMAS_NO, TICTYP_NO, SES_SEATNO, TICTYP_PRICE) VALUES(?,?,?,?)";

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

	



}
