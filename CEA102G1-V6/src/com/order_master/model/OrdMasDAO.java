package com.order_master.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.food.model.FooCartVO;
import com.food.model.FooVO;
import com.food_list.model.FooLisService;
import com.food_list.model.FooLisVO;
import com.ticket_list.model.TicLisService;
import com.ticket_list.model.TicLisVO;
import com.ticket_type.model.TicTypCartVO;



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
	
	private static final String INSERT_STMT = "INSERT INTO ORDER_MASTER(MEM_NO, SES_NO, ORDMAS_PRICE) VALUES(?,?,?)";
	private static final String GET_ALL_STMT = "SELECT ORDMAS_NO, MEM_NO, SES_NO, ORDMAS_DATE, ORDMAS_PRICE, ORDMAS_STATUS FROM ORDER_MASTER ORDER BY ORDMAS_NO";
	private static final String GET_ONE_STMT = "SELECT ORDMAS_NO, MEM_NO, SES_NO, ORDMAS_DATE, ORDMAS_PRICE, ORDMAS_STATUS FROM ORDER_MASTER WHERE ORDMAS_NO=?";
	
	@Override
	public void insertWithDetail(OrdMasVO ordMasVO, Set<TicTypCartVO> ticTypCartSet, Set<FooCartVO> fooCartSet) {
		
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
			pstmt.executeUpdate();
			
			Integer ordMasNo = null;
			key = pstmt.getGeneratedKeys();
			if (key.next()) {
				ordMasNo = new Integer(key.getString(1));
			} else {
				throw new RuntimeException("未取得自增主鍵");
			}
			key.close();
			TicLisService ticLisSvc = new TicLisService();
			for (TicTypCartVO  ticTypCartVO: ticTypCartSet) {
				Integer ticTypNo = ticTypCartVO.getTicTypNo();
				String sesSeatNo = ticTypCartVO.getSesSeatNo();
				Integer ticTypPrice = ticTypCartVO.getTicLisPrice();
				ticLisSvc.addTicLis(ordMasNo, ticTypNo, sesSeatNo, ticTypPrice, con);
			}
			FooLisService fooLisSvc = new FooLisService();
			for (FooCartVO fooCartVO : fooCartSet) {
				Integer fooNo = fooCartVO.getFooNo();
				Integer fooLisCount = fooCartVO.getFooCount();
				Integer fooLisPrice = fooCartVO.getFooPrice();
				
				fooLisSvc.addFooLis(ordMasNo, fooNo, fooLisCount, fooLisPrice, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					throw new RuntimeException("A rollback error occured. " + e.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<OrdMasVO> getAll() {
		List<OrdMasVO> list = new ArrayList<OrdMasVO>();
		OrdMasVO ordMasVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ordMasVO = new OrdMasVO();
				
				ordMasVO.setOrdMasNo(rs.getInt("ORDMAS_NO"));
				ordMasVO.setMemNo(rs.getInt("MEM_NO"));
				ordMasVO.setSesNo(rs.getInt("SES_NO"));
				ordMasVO.setOrdMasDate(rs.getTimestamp("ORDMAS_DATE"));
				ordMasVO.setOrdMasPrice(rs.getInt("ORDMAS_PRICE"));
				ordMasVO.setOrdMasStatus(rs.getInt("ORDMAS_STATUS"));
				
				list.add(ordMasVO);
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

	@Override
	public OrdMasVO findByprimarykey(Integer ordMasNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
