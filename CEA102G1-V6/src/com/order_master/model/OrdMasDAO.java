package com.order_master.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.food_list.model.FooLisService;
import com.food_list.model.FooLisVO;
import com.ticket_list.model.TicLisService;
import com.ticket_list.model.TicLisVO;



public class OrdMasDAO implements OrdMasDAO_interface {
	private static DataSource ds;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO ORDER_MASTER(MEM_NO, SES_NO, ORDMAS_PRICE, ORDMAS_GETNO) VALUES(?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT ORDMAS_NO, MEM_NO, SES_NO, ORDMAS_DATE, ORDMAS_PRICE, ORDMAS_GETNO, ORDMAS_STATUS FROM ORDER_MASTER ORDER BY ORDMAS_NO";
	private static final String GET_ONE_STMT = "SELECT ORDMAS_NO, MEM_NO, SES_NO, ORDMAS_DATE, ORDMAS_PRICE, ORDMAS_GETNO, ORDMAS_STATUS FROM ORDER_MASTER WHERE ORDMAS_NO=?";
	
	@Override
	public void insertWithDetail(OrdMasVO ordMasVO, Set<TicLisVO> ticketCart, Set<FooLisVO> fooCart) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet key = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false); 
			
			String[] cols = {"ORDMAS_NO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setInt(1, ordMasVO.getMemNo());
			pstmt.setInt(2, ordMasVO.getSesNo());
			pstmt.setInt(3, ordMasVO.getOrdMasPrice());
			pstmt.setString(4, ordMasVO.getOrdMasGetNo());
			pstmt.executeUpdate();
			
			key = pstmt.getGeneratedKeys();
			if (key.next()) {
				Integer ordMasNo = new Integer(key.getString(1));
				FooLisService fooLisSvc = new FooLisService();
				TicLisService ticLisSvc = new TicLisService();
				for (FooLisVO fooLisVO : fooCart) {
					fooLisVO.setOrdMasNo(ordMasNo);
					fooLisSvc.addFooLis(fooLisVO, con);
				}
				for (TicLisVO ticLisVO : ticketCart) {
					ticLisVO.setOrdMasNo(ordMasNo);
					ticLisSvc.addFooLis(ticLisVO, con);
				}
				con.commit();
				con.setAutoCommit(true);
			} else {
				throw new RuntimeException("未取得自增主鍵");
			}
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					throw new RuntimeException("A rollback error occured. "
							+ e.getMessage());
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
	public Set<OrdMasVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdMasVO findByprimarykey(Integer ordMasNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
