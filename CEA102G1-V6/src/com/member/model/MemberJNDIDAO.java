package com.member.model;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.deposit.model.DepositVO;
import com.member.model.*;


public class MemberJNDIDAO implements MemberDAO_infterface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenem");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memberVO.getMemName());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemPassword());
			pstmt.setString(4, memberVO.getMemMail());
//			pstmt.setInt(5, memberVO.getMemWallet());
//			pstmt.setInt(6, memberVO.getMemstatus());
			pstmt.setBytes(5, memberVO.getMemImg());
			pstmt.executeUpdate();

		}catch (SQLException se) {
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
			pstmt.setInt(5, memberVO.getMemWallet());
			pstmt.setInt(6, memberVO.getMemstatus());
			pstmt.setBytes(7, memberVO.getMemImg());
			pstmt.setString(8, memberVO.getMemuuid());
			pstmt.setInt(9, memberVO.getMemNo());
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
	public void updateFront(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
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

		}catch (SQLException se) {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE2);

			pstmt.setString(1, memberVO.getMemuuid());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemMail());

			pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memNo);

			pstmt.executeUpdate();
			check = true;
			
		}catch (SQLException se) {
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
				memberVO.setMemWallet(rs.getInt("mem_Wallet"));
				memberVO.setMemstatus(rs.getInt("mem_Status"));
				memberVO.setMemImg(rs.getBytes("mem_Img"));

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
	public MemberVO findByPrimaryKey2(Integer memNo) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
					memberVO.setMemstatus(rs.getInt("mem_status"));
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
	public MemberVO findByMemAccountMail(String memAccount,String memMail) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			}catch (SQLException se) {
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

			con = ds.getConnection();
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
	
	public MemberVO insertsignup(MemberVO memberVO) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_SIGNUP);
			
			
			pstmt.setString(1, memberVO.getMemName());
			pstmt.setString(2, memberVO.getMemAccount());
			pstmt.setString(3, memberVO.getMemPassword());
			pstmt.setString(4, memberVO.getMemMail());
			pstmt.setBytes(5, memberVO.getMemImg());
			pstmt.setString(6, memberVO.getMemuuid());

			pstmt.executeUpdate();	// 抓最新新增資料的ID number 
		
					}  catch (SQLException se) {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATESTATUS_STMT);
			
			pstmt.setInt(1, memberVO.getMemstatus());
			pstmt.setString(2, memberVO.getMemuuid());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
}
