package com.member.model;



import com.board.model.BoardJDBCDAO;
import com.board.model.BoardVO;
import com.member.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
public class MemberJDBCDAO implements MemberDAO_infterface{

	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/seenema?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "12qwaszx";
	
	private static final String INSERT_STMT = 
			"INSERT INTO member (mem_Name,mem_Account,mem_Password,mem_Mail,mem_Img) VALUES (?, ?, ?, ?, ?)";
	private static final String INSERT_SIGNUP = 
			"INSERT INTO member (mem_Name, mem_Account,mem_Password,mem_Mail,mem_Img,mem_uuid) VALUES (?, ?, ?, ?, ?,?)";
	private static final String DELETE = 
			"DELETE FROM member where mem_No = ?";
	
	private static final String UPDATE =
			"UPDATE member set mem_Name=?, mem_Account=?, mem_Password=? ,mem_Mail=? ,mem_Wallet=?, mem_Status=? ,mem_img=? ,mem_uuid=? where mem_No = ?";
	private static final String UPDATE2 =
			"UPDATE member set  mem_uuid=? where mem_Account=? and mem_Mail=?";
	
	private static final String GET_ALL_STMT = 
			"SELECT  mem_No, mem_Name, mem_Account, mem_Password, mem_Mail, mem_Wallet, mem_Status, mem_Img  FROM member";
	private static final String GET_ONE_STMT = 
			"SELECT mem_No,mem_Name, mem_Account, mem_Password, mem_Mail, mem_Wallet, mem_Status, mem_Img  FROM member where mem_No = ?";
	private static final String GET_ONE_FRONT_STMT = 
			"SELECT mem_No,mem_Name, mem_Account, mem_Password, mem_Mail, mem_Wallet, mem_Img  FROM member where mem_No = ?";
	private static final String GET_ONE_LOGIN = 
			"SELECT mem_no,mem_name,mem_account,mem_password,mem_mail,mem_wallet,mem_status,mem_img FROM member where mem_Account = ? and mem_Password = ?";		
	private static final String GET_ONE_FORGOT = 
			"SELECT mem_no,mem_name,mem_account,mem_password,mem_mail,mem_wallet,mem_status,mem_img FROM member where mem_Account = ? and mem_Mail = ?";
	private static final String GET_ALL_FORGOT = 
			"SELECT mem_no,mem_name,mem_account,mem_password,mem_mail,mem_wallet,mem_status,mem_img FROM member where mem_Uuid = ?";
	private static final String UPDATESTATUS_STMT = 
			"UPDATE member set mem_Status=? where mem_Uuid = ?";
	
	
	
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
			pstmt.setBytes(5, memberVO.getMemImg());
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
			pstmt.setInt(6, memberVO.getMemstatus());
			pstmt.setBytes(7, memberVO.getMemImg());
			pstmt.setString(8, memberVO.getMemuuid());
			pstmt.setInt(9, memberVO.getMemNo());
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
	public void updateFront(MemberVO memberVO) {
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
			pstmt.setInt(6, memberVO.getMemstatus());
			pstmt.setBytes(7, memberVO.getMemImg());
			pstmt.setString(8, memberVO.getMemuuid());
			pstmt.setInt(9, memberVO.getMemNo());
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
	public void updateUuid(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE2);

			pstmt.setString(1, memberVO.getMemuuid());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemMail());

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
				memberVO.setMemstatus(rs.getInt("mem_Status"));
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
	public MemberVO findByPrimaryKey2(Integer memNo) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_FRONT_STMT);

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
				memberVO.setMemstatus(rs.getInt("mem_status"));
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
	public MemberVO findByMemAccountMail(String memAccount,String memMail) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_FORGOT );

			pstmt.setString(1, memAccount);
			pstmt.setString(2, memMail);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				memberVO = new MemberVO();
				memberVO.setMemNo(rs.getInt("mem_no"));
				memberVO.setMemName(rs.getString("mem_name"));
				memberVO.setMemAccount(rs.getString("mem_account"));
				memberVO.setMemPassword(rs.getString("mem_password"));
				memberVO.setMemMail(rs.getString("mem_mail"));
				memberVO.setMemWallet(rs.getInt("mem_wallet"));
				memberVO.setMemstatus(rs.getInt("mem_status"));
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
	public MemberVO findByMemUuid(String memUuid) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_FORGOT );

			pstmt.setString(1, memUuid);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				memberVO = new MemberVO();
				memberVO.setMemNo(rs.getInt("mem_no"));
				memberVO.setMemName(rs.getString("mem_name"));
				memberVO.setMemAccount(rs.getString("mem_account"));
				memberVO.setMemPassword(rs.getString("mem_password"));
				memberVO.setMemMail(rs.getString("mem_mail"));
				memberVO.setMemWallet(rs.getInt("mem_wallet"));
				memberVO.setMemstatus(rs.getInt("mem_status"));
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
				memberVO.setMemstatus(rs.getInt("mem_Status"));
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
	
	public MemberVO insertsignup(MemberVO memberVO) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_SIGNUP);
			
			
			pstmt.setString(1, memberVO.getMemName());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemPassword());
			pstmt.setString(4, memberVO.getMemMail());
			pstmt.setBytes(5, memberVO.getMemImg());
			pstmt.setString(6, memberVO.getMemuuid());

			pstmt.executeUpdate();	// 抓最新新增資料的ID number 

					} catch (ClassNotFoundException e) {
							throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());

					} catch (SQLException se) {
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
					return memberVO;
				}
	
	
	@Override
	public void updateStatus(MemberVO memberVO)  {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATESTATUS_STMT);
			
			pstmt.setInt(1, memberVO.getMemstatus());
			pstmt.setString(2, memberVO.getMemuuid());
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
			+ e.getMessage());


		} catch (SQLException se) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	@Override    
//	public List<MemberVO> getAll(Map<String, String[]> map) {
//		List<MemberVO> list = new ArrayList<MemberVO>();
//		MemberVO memberVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//	
//	try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			String finalSQL = "select * from member "
//		          + jdbcUtil_CompositeQuery_member.get_WhereCondition(map)
//		          + "order by empno";
//			pstmt = con.prepareStatement(finalSQL);
//			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
//			rs = pstmt.executeQuery();
//	
//			while (rs.next()) {
//				empVO = new EmpVO();
//				empVO.setEmpno(rs.getInt("empno"));
//				empVO.setEname(rs.getString("ename"));
//				empVO.setJob(rs.getString("job"));
//				empVO.setHiredate(rs.getDate("hiredate"));
//				empVO.setSal(rs.getDouble("sal"));
//				empVO.setComm(rs.getDouble("comm"));
//				empVO.setDeptno(rs.getInt("deptno"));
//				list.add(empVO); // Store the row in the List
//			}
//	
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//}
	
	
	
	
	
	
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
				MemberVO memberchang = new MemberVO();
				
				memberchang.setMemName("鄒捷輪");
				memberchang.setMemAccount("testfinish");
				memberchang.setMemPassword("testfinish");
				memberchang.setMemMail("testfinish@gmail.com");
				memberchang.setMemAccount("testfinish@gmail.com");
				memberchang.setMemWallet(1);
				memberchang.setMemstatus(1);
				memberchang.setMemNo(5);
				dao.update(memberchang);
				System.out.println("動作完成");
				
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

