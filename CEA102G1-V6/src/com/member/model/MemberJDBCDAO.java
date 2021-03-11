package com.member.model;



import com.member.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MemberJDBCDAO implements MemberDAO_infterface{

	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			
			"INSERT INTO member (mem_Name,mem_Account,mem_Password,mem_Mail,mem_Wallet,mem_Ststus,mem_Img) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE = 
			"DELETE FROM member where mem_No = ?";
	private static final String UPDATE =
//			 mem_Wallet=? mem_Ststus=? mem_Img=? where mem_No = ?"
			"UPDATE member set mem_Name=?, mem_Account=?, mem_Password=? ,mem_Mail=? where mem_No = ?";
	private static final String GET_ALL_STMT = 
//			, mem_Wallet=?, mem_Ststus=?, mem_Img=? 
			"SELECT  mem_No, mem_Name, mem_Account, mem_Password, mem_Mail FROM member";
	private static final String GET_ONE_STMT = 
//			, mem_Wallet=?, mem_Ststus=?, mem_Img=? 
			"SELECT mem_No,mem_Name, mem_Account, mem_Password, mem_Mail FROM member where mem_No = ?";
	private static final String GET_ONE_LOGIN = 
			"SELECT mem_no,mem_name,mem_account,mem_password,mem_mail,mem_wallet,mem_status,mem_img FROM member where mem_Account = ? and mem_Password = ?";		
	
	@Override
	public void insert(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, memberVO.getMemName());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemPassword());
			pstmt.setString(4, memberVO.getMemMail());
			pstmt.setInt(5, memberVO.getMemWallet());
			pstmt.setInt(6, memberVO.getMemststus());
			pstmt.setBytes(7, memberVO.getMemImg());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memberVO.getMemName());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemPassword());
			pstmt.setString(4, memberVO.getMemMail());
			pstmt.setInt(5, memberVO.getMemWallet());
			pstmt.setInt(6, memberVO.getMemststus());
			pstmt.setBytes(7, memberVO.getMemImg());
			pstmt.setInt(8, memberVO.getMemNo());
//			pstmt.setInt(5, memberVO.getMemNo());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public Boolean delete(Integer memNo) {
		Boolean check = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memNo);

			pstmt.executeUpdate();
			check = true;
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
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
		return check;

	}

	@Override
	public MemberVO findByPrimaryKey(Integer memNo) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				memberVO = new MemberVO();
				memberVO.setMemNo(rs.getInt("mem_No"));
				memberVO.setMemName(rs.getString("mem_Name"));
				memberVO.setMemAccount(rs.getString("mem_Account"));
				memberVO.setMemPassword(rs.getString("mem_Password"));
				memberVO.setMemMail(rs.getString("mem_Mail"));
				memberVO.setMemWallet(rs.getInt("mem_Wallet"));
				memberVO.setMemststus(rs.getInt("mem_Ststus"));
				memberVO.setMemImg(rs.getBytes("mem_Img"));
				
				
				
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return memberVO;
	}
	
	@Override
	public MemberVO findByMemAccount(String memAccount,String memPassword) {
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_LOGIN);

			pstmt.setString(1, memAccount);
			pstmt.setString(2, memPassword);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				memberVO = new MemberVO();
				memberVO.setMemNo(rs.getInt("mem_no"));
				memberVO.setMemName(rs.getString("mem_name"));
				memberVO.setMemAccount(rs.getString("mem_account"));
				memberVO.setMemPassword(rs.getString("mem_password"));
				memberVO.setMemMail(rs.getString("mem_mail"));
				memberVO.setMemWallet(rs.getInt("mem_wallet"));
				memberVO.setMemststus(rs.getInt("mem_status"));
				memberVO.setMemImg(rs.getBytes("mem_img"));
			}

			} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			
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
		return memberVO;
	}
	
	
	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				memberVO = new MemberVO();
							
				memberVO.setMemNo(rs.getInt("mem_No"));
				memberVO.setMemName(rs.getString("mem_Name"));
				memberVO.setMemAccount(rs.getString("mem_Account"));
				memberVO.setMemPassword(rs.getString("mem_Password"));
				memberVO.setMemMail(rs.getString("mem_Mail"));
				memberVO.setMemWallet(rs.getInt("mem_Wallet"));
				memberVO.setMemststus(rs.getInt("mem_Ststus"));
				memberVO.setMemImg(rs.getBytes("mem_Img"));
				
				list.add(memberVO); 
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
//================================================================	
	
	
	public static void main(String[] args) {
		
				MemberJDBCDAO dao = new MemberJDBCDAO();
				
//				 新增
//				MemberVO memberVO = new MemberVO();
//				memberVO.setMemName("吾蹤現");
//				memberVO.setMemAccount("test321");
//				memberVO.setMemPassword("test321");
//				memberVO.setMemMail("test321@gmail.com");
//				dao.insert(memberVO);
//				System.out.println("動作完成");
				
				
//				Boolean check = dao.delete(7);
//				System.out.println(check);
				
				//修改
//				MemberVO memberchang = new MemberVO();
//				
//				memberchang.setMemName("鄒捷輪");
//				memberchang.setMemAccount("testfinish");
//				memberchang.setMemPassword("testfinish");
//				memberchang.setMemMail("testfinish@gmail.com");
//				memberchang.setMemNo(6);
//				dao.update(memberchang);
//				System.out.println("動作完成");
				
//				 查詢
//				MemberVO Membergetone = dao.findByPrimaryKey(3);
//					System.out.print(Membergetone.getMemNo() + ",");
//					System.out.print(Membergetone.getMemName() + ",");
//					System.out.print(Membergetone.getMemAccount() + ",");
//					System.out.print(Membergetone.getMemPassword() + ",");
//					System.out.println(Membergetone.getMemMail());
//					System.out.println("動作完成");
		
//				List<MemberVO> MemList = dao.getAll();
//				for (MemberVO MemBox : MemList) {
//					System.out.print(MemBox.getMemNo() + ",");
//					System.out.print(MemBox.getMemName() + ",");
//					System.out.print(MemBox.getMemAccount()+ ",");
//					System.out.print(MemBox.getMemPassword()+ ",");
//					System.out.print(MemBox.getMemMail());
//				System.out.println("動作完成");
//				}
				// 刪除
//				dao.delete(6);
//				System.out.println("----------------------------------------------------");
				
				// login
//				dao.findByMemAccount();
//				System.out.println("----------------------------------------------------");
				
				
				
			}
	
	
}
