package com.ticket_list.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ticket_type.model.TicTypCartVO;

public class TicLisService {
	private TicLisDAO_interface dao;
	
	public TicLisService() {
		dao = new TicLisDAO();
	}
	
	public void addTicLis(Integer ordMasNo, Integer ticTypNo, String sesSeatNo, Integer ticTypPrice, Connection con) {
		TicLisVO ticLisVO = new TicLisVO();
		
		ticLisVO.setOrdMasNo(ordMasNo);
		ticLisVO.setTicTypNo(ticTypNo);
		ticLisVO.setSesSeatNo(sesSeatNo);
		ticLisVO.setTicTypPrice(ticTypPrice);
		dao.insert(ticLisVO, con);
	}
	
	public List<TicLisVO> getByOrdMasNo(Integer ordMasNo) {
		return dao.findByOrdMasNo(ordMasNo);
	}
	
	public Set<TicTypCartVO> convertToTicTypCart(List<TicLisVO> list) {
		Set<TicTypCartVO> ticTypCart = new LinkedHashSet<TicTypCartVO>();
		TicTypCartVO ticTypCartVO = null;
		
		for (TicLisVO ticLisVO : list) {
			ticTypCartVO = new TicTypCartVO();
			ticTypCartVO.setTicTypNo(ticLisVO.getTicTypNo());
			ticTypCartVO.setTicLisPrice(ticLisVO.getTicTypPrice());
			ticTypCartVO.setTicTypCount(1);

			if (ticTypCart.contains(ticTypCartVO)) {
				for (TicTypCartVO cartVO : ticTypCart) {
					if (cartVO.equals(ticTypCartVO)) {
						cartVO.setTicTypCount(cartVO.getTicTypCount() + 1);
					}
				}
			}
			ticTypCart.add(ticTypCartVO);
			
		}
		return ticTypCart;
	}
	
	public String getSeatNo(List<TicLisVO> list) {
		String seatNo = list.stream()
				.map(TicLisVO::getSesSeatNo)
				.collect(Collectors.joining(", "));
		return seatNo;
	}
}
