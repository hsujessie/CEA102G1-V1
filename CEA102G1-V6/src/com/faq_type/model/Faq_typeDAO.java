package com.faq_type.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.faq.model.FaqVO;




public class Faq_typeDAO implements Faq_typeDAO_interface{
	
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
			"INSERT INTO faq_type (faqtyp_name) VALUES (?)";
	private static final String GET_ALL_STMT =
			"SELECT * FROM faq_type order by faqtyp_no";
	private static final String GET_ONE_STMT =
			"SELECT * FROM faq_type where faqtyp_no = ?";
	private static final String DELETE =
			"DELETE FROM faq_type where faqtyp_no = ?";
	private static final String UPDATE = 
			"UPDATE faq_type set faqtyp_name= ? where faqtyp_no= ?";
	private static final String GET_Faq_ByFaqtyp_no_STMT =
			"SELECT * FROM faq where faqtyp_no = ? order by faq_no";
	
	@Override
	public void insert(Faq_typeVO faq_typeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, faq_typeVO.getFaqtyp_name());
			
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
	public void update(Faq_typeVO faq_typeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, faq_typeVO.getFaqtyp_name());
			pstmt.setInt(2, faq_typeVO.getFaqtyp_no());
			
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
	public void delete(Integer faqtyp_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Faq_typeVO findByPrimaryKey(Integer faqtyp_no) {
		
		Faq_typeVO faq_typeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, faqtyp_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faq_typeVO = new Faq_typeVO();
				faq_typeVO.setFaqtyp_no(rs.getInt("faqtyp_no"));
				faq_typeVO.setFaqtyp_name(rs.getString("faqtyp_name"));
				
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
		return faq_typeVO;
	}

	@Override
	public List<Faq_typeVO> getAll() {
		List<Faq_typeVO> list = new ArrayList<Faq_typeVO>();
		Faq_typeVO faq_typeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				faq_typeVO = new Faq_typeVO();
				faq_typeVO.setFaqtyp_no(rs.getInt("faqtyp_no"));
				faq_typeVO.setFaqtyp_name(rs.getString("faqtyp_name"));
				
				list.add(faq_typeVO);
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

	@Override
	public Set<FaqVO> getFaqsByFaqtyp_no(Integer faqtyp_no) {
		Set<FaqVO> set = new LinkedHashSet<FaqVO>();
		FaqVO faqVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Faq_ByFaqtyp_no_STMT);
			pstmt.setInt(1, faqtyp_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				faqVO = new FaqVO();
				faqVO.setFaq_no(rs.getInt("faq_no"));
				faqVO.setFaqtyp_no(rs.getInt("faqtyp_no"));
				faqVO.setFaq_question(rs.getString("faq_question"));
				faqVO.setFaq_answer(rs.getString("faq_answer"));
				set.add(faqVO);							
			}
		} catch  (SQLException se) {
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
		return set;
	}



}
