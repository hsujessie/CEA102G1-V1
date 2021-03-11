package com.board_type.model;

import java.util.*;

public interface BoardTypeDAO_interface {
          public void insert(BoardTypeVO boardtypeVO);
          public void update(BoardTypeVO boardtypeVO);
          public void delete(Integer boatypNo);
          public BoardTypeVO findByPrimaryKey(Integer boatypNo);
          public List<BoardTypeVO> getAll();

//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
