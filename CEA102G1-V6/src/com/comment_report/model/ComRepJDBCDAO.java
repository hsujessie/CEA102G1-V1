package com.comment_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ComRepJDBCDAO implements ComRepDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	
	private static final String INSERT_STMT =
		"INSERT INTO COMMENT_REPORT (com_no,mem_no,comrep_reason,comrep_time,comrep_status) VALUES (?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT comrep_no,com_no,mem_no,comrep_reason,comrep_time,comrep_status FROM COMMENT_REPORT ORDER BY comrep_no";
	private static final String GET_ONE_STMT =
		"SELECT comrep_no,com_no,mem_no,comrep_reason,comrep_time,comrep_status FROM COMMENT_REPORT WHERE comrep_no=?";
	private static final String UPDATE =
		"UPDATE COMMENT_REPORT SET comrep_status=? WHERE comrep_no=?";

	@Override
	public void insert(ComRepVO comRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,comRepVO.getComNo());
			pstmt.setInt(2,comRepVO.getMemNo());
			pstmt.setString(3,comRepVO.getComRepReason());
			pstmt.setTimestamp(4,comRepVO.getComRepTime());
			pstmt.setInt(5,comRepVO.getComRepStatus());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ComRepJDBCDAO insert A database error occured. " + se.getMessage());		
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
	public void update(ComRepVO comRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1,comRepVO.getComRepStatus());
			pstmt.setInt(2,comRepVO.getComRepNo());	
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ComRepJDBCDAO update A database error occured. " + se.getMessage());
		
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
	public ComRepVO findByPrimaryKey(Integer comRepNo) {
		ComRepVO comRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,comRepNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comRepVO = new ComRepVO();
				comRepVO.setComRepNo(rs.getInt("comrep_no"));
				comRepVO.setComNo(rs.getInt("com_no"));
				comRepVO.setMemNo(rs.getInt("mem_no"));
				comRepVO.setComRepReason(rs.getString("comrep_reason"));
				comRepVO.setComRepTime(rs.getTimestamp("comrep_time"));
				comRepVO.setComRepStatus(rs.getInt("comrep_status"));
			}		
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
		} catch(SQLException se) {
			throw new RuntimeException("ComRepJDBCDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		return comRepVO;
	}

	@Override
	public List<ComRepVO> getAll() {
		List<ComRepVO> list = new ArrayList<ComRepVO>();
		ComRepVO comRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				comRepVO = new ComRepVO();
				comRepVO.setComRepNo(rs.getInt("comrep_no"));
				comRepVO.setComNo(rs.getInt("com_no"));
				comRepVO.setMemNo(rs.getInt("mem_no"));
				comRepVO.setComRepReason(rs.getString("comrep_reason"));
				comRepVO.setComRepTime(rs.getTimestamp("comrep_time"));
				comRepVO.setComRepStatus(rs.getInt("comrep_status"));
				list.add(comRepVO);
			}
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
		} catch(SQLException se) {
			throw new RuntimeException("ComRepJDBCDAO getAll A database error occured. " + se.getMessage());	
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
		return list;
	}
	
	public static void main(String[] args) {
		ComRepJDBCDAO dao = new ComRepJDBCDAO();
		
		// java Timestamp
		Timestamp time= new Timestamp(System.currentTimeMillis()); //獲取系統當前時間 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = df.format(time); 
		time = Timestamp.valueOf(timeStr); 
		
		// 新增
		ComRepVO comRepVO = new ComRepVO();
		comRepVO.setComNo(1);
		comRepVO.setMemNo(1);
		comRepVO.setComRepReason("reasonTest");
		comRepVO.setComRepTime(time);
		comRepVO.setComRepStatus(0);
		dao.insert(comRepVO);
		
		// 修改
		ComRepVO comRepVO2 = new ComRepVO();
		comRepVO2.setComRepStatus(1);
		comRepVO2.setComRepNo(1);
		dao.update(comRepVO2);
		
		// 查詢
		ComRepVO comRepVO3 = dao.findByPrimaryKey(1);
		System.out.print(comRepVO3.getComRepNo() + ",");
		System.out.print(comRepVO3.getComNo() + ",");
		System.out.print(comRepVO3.getMemNo() + ",");
		System.out.print(comRepVO3.getComRepReason() + ",");
		System.out.print(comRepVO3.getComRepTime() + ",");
		System.out.print(comRepVO3.getComRepStatus());
		System.out.println("---------------------");
		
		// 查詢
		List<ComRepVO> list = dao.getAll();
		for (ComRepVO aComRep : list) {
			System.out.print(aComRep.getComRepNo() + ",");
			System.out.print(aComRep.getComNo() + ",");
			System.out.print(aComRep.getMemNo() + ",");
			System.out.print(aComRep.getComRepReason() + ",");
			System.out.print(aComRep.getComRepTime() + ",");
			System.out.print(aComRep.getComRepStatus());
			System.out.println();
		}
	}

	@Override
	public List<ComRepVO> findComRepByComReStatus(Integer comReStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer findRepeatedComRep(Integer comNo, Integer memNo, Integer comRepReason) {
		// TODO Auto-generated method stub
		return null;
	}

}
