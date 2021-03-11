package com.client_service.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Client_serviceDAO implements Client_serviceDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String INSERT_STMT =
			"INSERT INTO Client_service (mem_no , cliser_content , cliser_who) VALUES(?, ?, ?)";
	private static final String GET_ONE_STMT =
			"SELECT * FROM Client_service where cliser_no = ?";
	private static final String GET_CLIENT_SERVICE_BYMEM_ID_STMT =
			"SELECT * FROM Client_service where mem_no = ? order by cliser_no";
	@Override
	public void insert(Client_serviceVO client_serviceVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, client_serviceVO.getMem_no());
			pstmt.setString(2, client_serviceVO.getCliser_content());
			pstmt.setInt(3, client_serviceVO.getCliser_who());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public Client_serviceVO findByPrimaryKey(Integer cliser_no) {
		Client_serviceVO client_serviceVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1,cliser_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				client_serviceVO = new Client_serviceVO();				
				client_serviceVO.setCliser_no(rs.getInt("cliser_no"));
				client_serviceVO.setMem_no(rs.getInt("mem_no"));
				client_serviceVO.setCliser_content(rs.getString("cliser_content"));
				client_serviceVO.setCliser_time(rs.getTimestamp("cliser_time"));
				client_serviceVO.setCliser_who(rs.getInt("cliser_who"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return client_serviceVO;
	}
	@Override
	public List<Client_serviceVO> getClient_serviceByMem_no(Integer mem_no) {
		List<Client_serviceVO> list = new ArrayList<Client_serviceVO>();
		Client_serviceVO client_serviceVO =null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CLIENT_SERVICE_BYMEM_ID_STMT);
			pstmt.setInt(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				client_serviceVO = new Client_serviceVO();				
				client_serviceVO.setCliser_no(rs.getInt("cliser_no"));
				client_serviceVO.setMem_no(rs.getInt("mem_no"));
				client_serviceVO.setCliser_content(rs.getString("cliser_content"));
				client_serviceVO.setCliser_time(rs.getTimestamp("cliser_time"));
				client_serviceVO.setCliser_who(rs.getInt("cliser_who"));
				list.add(client_serviceVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
