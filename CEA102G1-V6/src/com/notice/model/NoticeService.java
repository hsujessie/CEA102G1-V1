package com.notice.model;

import java.util.List;


public class NoticeService {
	private NoticeDAO_interface dao;
	
	public NoticeService() {
		dao = new NoticeDAO();
	}
	
	public NoticeVO addNotice(Integer mem_no , String not_content) {
		
		NoticeVO noticeVO = new NoticeVO();
		
		noticeVO.setMem_no(mem_no);
		noticeVO.setNot_content(not_content);
		dao.insert(noticeVO);
		return noticeVO;
	}
	
	public NoticeVO updateNotice(Integer not_no) {
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setNot_no(not_no);
		dao.update(noticeVO);
		
		return dao.findByPrimaryKey(not_no);
	}
	
	public List<NoticeVO> getAll() {
		return dao.getAll();
	}
	public List<NoticeVO> getNoticeByMemno(Integer mem_no) {
		return dao.getNoticeByMemno(mem_no);
	}
}
