package com.board.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.board.model.BoardJNDIDAO;
import com.board.model.BoardVO;

public class BoardJNDIDAO implements BoardDAO_interface {

	
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
			"INSERT INTO board (boatyp_No,boa_Content) VALUES (?, ?)";
	private static final String DELETE = 
			"DELETE FROM board where boa_No = ?";
	private static final String UPDATE = 
			"UPDATE board set boatyp_No=?, boa_Content=? where boa_No = ?";
	private static final String GET_ALL_STMT = 
			"SELECT  boa_No, boatyp_No, boa_Content, boa_Time FROM board";
	private static final String GET_ONE_STMT = 
			"SELECT boa_No , boatyp_No, boa_Content, boa_Time FROM board where boa_No = ?";
	private static final String GET_ONE_STMT2 = 
			"SELECT boa_NO , boatyp_NO, boa_CONTENT, boa_Time FROM board where boatyp_NO = ?";

	
				@Override
				public void insert(BoardVO boardVO) {
			
					Connection con = null;
					PreparedStatement pstmt = null;
			
					try {
			
						con = ds.getConnection();
						pstmt = con.prepareStatement(INSERT_STMT);
						
						pstmt.setInt(1, boardVO.getBoatypNo());
						pstmt.setString(2, boardVO.getBoaContent());
						pstmt.executeUpdate("set auto_increment_offset=1;");
						pstmt.executeUpdate("set auto_increment_increment=1;");
						
						pstmt.executeUpdate();
					}
						
					catch (SQLException se) {
						throw new RuntimeException("A database error occured. "
								+ se.getMessage());
						
					} 
					finally {
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
				public void update(BoardVO boardVO) {
			
					Connection con = null;
					PreparedStatement pstmt = null;
			
					try {
			
						con = ds.getConnection();
						pstmt = con.prepareStatement(UPDATE);
			
						pstmt.setInt(1, boardVO.getBoatypNo());
						pstmt.setString(2, boardVO.getBoaContent());
						pstmt.setInt(3, boardVO.getBoaNo());
			
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
				public Boolean delete(Integer boaNo) {
					Boolean check = false;
					int updateCount_EMPs = 0;
					Connection con = null;
					PreparedStatement pstmt = null;
			
					try {
			
						con = ds.getConnection();
						
						
						con.setAutoCommit(false);
						pstmt = con.prepareStatement(DELETE);
			
						pstmt.setInt(1, boaNo);
			
			
						updateCount_EMPs = pstmt.executeUpdate();
						
						con.commit();
						con.setAutoCommit(true);
						System.out.println("刪除公告編號" + boaNo + "時,共有幾筆" + updateCount_EMPs
								+ "公告同時被刪除");
						check = true;
					
					}  
					catch (SQLException se) {
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
					return check;
			
				}
			
				@Override
				public BoardVO findByPrimaryKey(Integer boaNo) {
			
					BoardVO boardVO = null;
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
			
					try {
			
						con = ds.getConnection();
						pstmt = con.prepareStatement(GET_ONE_STMT);
			
						pstmt.setInt(1, boaNo);
			
						rs = pstmt.executeQuery();
			
						while (rs.next()) {
							
							boardVO = new BoardVO();
							boardVO.setBoaNo(rs.getInt("boa_No"));
							boardVO.setBoatypNo(rs.getInt("boatyp_No"));
							boardVO.setBoaContent(rs.getString("boa_Content"));
							boardVO.setBoaTime(rs.getDate("boa_Time"));
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
					return boardVO;
				}
			
				@Override
				public List<BoardVO> getAll() {
					List<BoardVO> list = new ArrayList<BoardVO>();
					BoardVO boardVO = null;
			
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
			
					try {
			
						con = ds.getConnection();
						pstmt = con.prepareStatement(GET_ALL_STMT);
						rs = pstmt.executeQuery();
			
						while (rs.next()) {
							
							boardVO = new BoardVO();
							boardVO.setBoaNo(rs.getInt("boa_No"));
							boardVO.setBoatypNo(rs.getInt("boatyp_No"));
							boardVO.setBoaContent(rs.getString("boa_Content"));
							boardVO.setBoaTime(rs.getDate("boa_Time"));
							
							list.add(boardVO); 
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
					return list;
				}
				
				
				@Override
				public List<BoardVO> findByFK(Integer boatypNo) {
					List<BoardVO> list = new ArrayList<BoardVO>();
					BoardVO boardVO = null;
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;

					try {
						con = ds.getConnection();
						pstmt = con.prepareStatement(GET_ONE_STMT2);

						pstmt.setInt(1, boatypNo);

						rs = pstmt.executeQuery();

						while (rs.next()) {
							
							boardVO = new BoardVO();
							
							boardVO.setBoatypNo(rs.getInt("boatyp_No"));
							boardVO.setBoaNo(rs.getInt("boa_No"));
							boardVO.setBoaContent(rs.getString("boa_Content"));
							boardVO.setBoaTime(rs.getDate("boa_Time"));
							
							list.add(boardVO);
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
					return list;
				}

	
	
	
	
	
	
}