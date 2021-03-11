package com.deposit.model;

import java.util.List;

import com.deposit.model.DepositDAO_interface;
import com.deposit.model.DepositJDBCDAO;
import com.deposit.model.DepositVO;

public class DepositServic {
	
	private DepositDAO_interface dao;

	public DepositServic() {
		dao = new DepositJDBCDAO();
	}

	public DepositVO addBoard(Integer memNo, Integer depAmount) {

		DepositVO depositVO = new DepositVO();

		
		depositVO.setMemNo(memNo);
		depositVO.setDepamount(depAmount);
		
		
		dao.insert(depositVO);

		return depositVO;
	}

	public DepositVO updateBoard(Integer depNo, Integer memNo, Integer depAmount) {

		DepositVO depositVO = new DepositVO();
		
		depositVO.setDepNo(depNo);
		depositVO.setMemNo(memNo);
		depositVO.setDepamount(depAmount);
//		depositVO.setDepTime(depTime);
		
		dao.update(depositVO);

		return depositVO;
		
	}

	public void deleteBoard(Integer depNo) {
		dao.delete(depNo);
	}

	public DepositVO getOneBoard(Integer depNo) {
		return dao.findByPrimaryKey(depNo);
	}
//	public BoardVO getOneBoard2(Integer boatypNo) {
//		return dao.findByFK(boatypNo);
//	}

	public List<DepositVO> getAll() {
		return dao.getAll();
	}
	
//	public static void main(String[] args) {
//		BoardService dao = new BoardService();
//		
//		dao.updateBoard(1,1,"123123");
//		
//		List<BoardVO> boardVOList = dao.getAll();
//		
//		for (BoardVO boardVO : boardVOList) {
//		System.out.print(depositVO.getDepNo() + ",");
//		System.out.print(depositVO.getMemNo() + ",");
//		System.out.println(depositVO.getDepamount()+ ",");
//		System.out.println(depositVO.getDepTime());
//		
//		System.out.println("-----------------");
//		}
//	}

}
