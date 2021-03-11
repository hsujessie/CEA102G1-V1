package com.deposit.model;

import java.util.List;

import com.board.model.BoardVO;

public interface DepositDAO_interface {

	public void insert(DepositVO depositVO);
    public void update(DepositVO depositVO);
    public Boolean delete(Integer depNo);
    public DepositVO findByPrimaryKey(Integer depNo);

    public List<DepositVO> getAll();
	
	
	
}
