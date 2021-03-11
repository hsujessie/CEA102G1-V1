package com.comment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ComJDBCDAO implements ComDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	
	private static final String INSERT_STMT =
		"INSERT INTO COMMENT (mov_no,mem_no,com_time,com_content,com_status) VALUES (?,?,?,?,?)"; 
	private static final String GET_ALL_STMT =
		"SELECT com_no,mov_no,mem_no,com_time,com_content,com_status FROM COMMENT ORDER BY com_no";
	private static final String GET_ONE_STMT =
		"SELECT com_no,mov_no,mem_no,com_time,com_content,com_status FROM COMMENT WHERE com_no=?";
	private static final String UPDATE =
		"UPDATE COMMENT SET com_status=? WHERE com_no=?";

	@Override
	public void insert(ComVO comVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1,comVO.getMovNo());
			pstmt.setInt(2,comVO.getMemNo());
			pstmt.setTimestamp(3,comVO.getComTime());
			pstmt.setString(4,comVO.getComContent());
			pstmt.setInt(5,comVO.getComStatus());
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ComJDBCDAO insert A database error occured. " + se.getMessage());		
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
	public void update(ComVO comVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1,comVO.getComStatus());
			pstmt.setInt(2,comVO.getComNo());	
			
			pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("ComJDBCDAO update A database error occured. " + se.getMessage());
		
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
	public ComVO findByPrimaryKey(Integer comNo) {
		ComVO comVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1,comNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comVO = new ComVO();
				comVO.setComNo(rs.getInt("com_no"));
				comVO.setMovNo(rs.getInt("mov_no"));
				comVO.setMemNo(rs.getInt("mem_no"));
				comVO.setComTime(rs.getTimestamp("com_time"));
				comVO.setComContent(rs.getString("com_content"));
				comVO.setComStatus(rs.getInt("com_status"));
			}		
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
		} catch(SQLException se) {
			throw new RuntimeException("ComJDBCDAO findByPrimaryKey A database error occured. " + se.getMessage());	
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
		return comVO;
	}

	@Override
	public List<ComVO> getAll() {
		List<ComVO> list = new ArrayList<ComVO>();
		ComVO comVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				comVO = new ComVO();
				comVO.setComNo(rs.getInt("com_no"));
				comVO.setMovNo(rs.getInt("mov_no"));
				comVO.setMemNo(rs.getInt("mem_no"));
				comVO.setComTime(rs.getTimestamp("com_time"));
				comVO.setComContent(rs.getString("com_content"));
				comVO.setComStatus(rs.getInt("com_status"));
				list.add(comVO);
			}
			
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
		} catch(SQLException se) {
			throw new RuntimeException("ComJDBCDAO getAll A database error occured. " + se.getMessage());	
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
		ComJDBCDAO dao = new ComJDBCDAO();
		
		// java Timestamp
		Timestamp time= new Timestamp(System.currentTimeMillis()); //獲取系統當前時間 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = df.format(time); 
		time = Timestamp.valueOf(timeStr); 
		
		// 新增
//		ComVO comVO = new ComVO();
//		comVO.setMovNo(1);
//		comVO.setMemNo(1);
//		comVO.setComTime(time);
//		comVO.setComContent("contentTest");
//		comVO.setComStatus(0);
//		dao.insert(comVO);
		
		// 修改
//		ComVO comVO2 = new ComVO();
//		comVO2.setComStatus(0);
//		comVO2.setComNo(3);
//		dao.update(comVO2);
		
		// 查詢
//		ComVO comVO3 = dao.findByPrimaryKey(1);
//		System.out.print(comVO3.getComNo() + ",");
//		System.out.print(comVO3.getMovNo() + ",");
//		System.out.print(comVO3.getMemNo() + ",");
//		System.out.print(comVO3.getComTime() + ",");
//		System.out.print(comVO3.getComContent() + ",");
//		System.out.print(comVO3.getComStatus());
//		System.out.println("---------------------");
		
		// 查詢
//		List<ComVO> list = dao.getAll();
//		for (ComVO aCom : list) {
//			System.out.print(aCom.getComNo() + ",");
//			System.out.print(aCom.getMovNo() + ",");
//			System.out.print(aCom.getMemNo() + ",");
//			System.out.print(aCom.getComTime() + ",");
//			System.out.print(aCom.getComContent() + ",");
//			System.out.print(aCom.getComStatus());
//			System.out.println();
//		}
	}
}
