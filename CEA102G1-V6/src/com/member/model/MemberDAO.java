package com.member.model;


	import java.util.*;
	import java.sql.*;

	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import javax.sql.DataSource;

	import com.deposit.model.DepositVO;
	import com.member.model.*;


	public class MemberDAO implements MemberDAO_infterface{

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
				"INSERT INTO member (mem_Name,mem_Account,mem_Password,mem_Mail,mem_Wallet,mem_Ststus,mem_Img) VALUES (?, ?, ?, ?, ?, ?, ?)";
		private static final String DELETE = 
				"DELETE FROM member where mem_No = ?";
		private static final String UPDATE = 
				"UPDATE member set mem_Name=?, mem_Account=? mem_Password=? mem_Mail=? mem_Wallet=? mem_Ststus=? mem_Img=? where mem_No = ?";
		private static final String GET_ALL_STMT = 
				"SELECT  mem_No, mem_Name, mem_Account, mem_Password, mem_Mail, mem_Wallet, mem_Ststus, mem_Img, FROM member";
		 private static final String GET_ONE_STMT = 
				    "SELECT  mem_No, mem_Name, mem_Account, mem_Password, mem_Mail, mem_Wallet, mem_Status, mem_Img FROM member where mem_No = ?";
		private static final String GET_ONE_LOGIN = 
				"SELECT mem_no,mem_name,mem_account,mem_password,mem_mail,mem_wallet,mem_status,mem_img FROM member where mem_Account = ? and mem_Password = ?";
		
		@Override
		public void insert(MemberVO memberVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, memberVO.getMemName());
				pstmt.setString(2, memberVO.getMemAccount());
				pstmt.setString(3, memberVO.getMemPassword());
				pstmt.setString(4, memberVO.getMemMail());
//				pstmt.setInt(5, memberVO.getMemWallet());
//				pstmt.setInt(6, memberVO.getMemststus());
//				pstmt.setBytes(7, memberVO.getMemImg());
				
//				pstmt.executeUpdate("set auto_increment_offset=1;");
//				pstmt.executeUpdate("set auto_increment_increment=1;");
				
				pstmt.executeUpdate();

			}
				
			catch (SQLException se) {
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, memberVO.getMemName());
				pstmt.setString(2, memberVO.getMemAccount());
				pstmt.setString(3, memberVO.getMemPassword());
				pstmt.setString(4, memberVO.getMemMail());
//				pstmt.setInt(5, memberVO.getMemWallet());
//				pstmt.setInt(6, memberVO.getMemststus());
//				pstmt.setBytes(7, memberVO.getMemImg());
				pstmt.setInt(8, memberVO.getMemNo());
				
				pstmt.executeUpdate();
			}
			 catch (SQLException se) {
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
			int updateCount_EMPs = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
//				con.setAutoCommit(false);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, memNo);

//				pstmt.executeUpdate();
				updateCount_EMPs = pstmt.executeUpdate();
				
//				con.commit();
//				con.setAutoCommit(true);
				System.out.println("�R�����i�s��" + memNo + "��,�@���X��" + updateCount_EMPs
						+ "���i�P�ɳQ�R��");
				check = true;
			
			}  
			catch (SQLException se) {
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

				con = ds.getConnection();
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
//					memberVO.setMemWallet(rs.getInt("mem_Wallet"));
//					memberVO.setMemststus(rs.getInt("mem_Ststus"));
//					memberVO.setMemImg(rs.getBytes("mem_Img"));
				}
			}
				catch (SQLException se) {
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
				
				con = ds.getConnection();
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					memberVO = new MemberVO();
					memberVO.setMemNo(rs.getInt("mem_No"));
					memberVO.setMemName(rs.getString("mem_Name"));
					memberVO.setMemAccount(rs.getString("mem_Account"));
					memberVO.setMemPassword(rs.getString("mem_Password"));
					memberVO.setMemMail(rs.getString("mem_Mail"));
//					memberVO.setMemWallet(rs.getInt("mem_Wallet"));
//					memberVO.setMemststus(rs.getInt("mem_Ststus"));
//					memberVO.setMemImg(rs.getBytes("mem_Img"));
					list.add(memberVO); 
				}
			}
			  catch (SQLException se) {
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
