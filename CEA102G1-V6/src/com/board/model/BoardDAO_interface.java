package com.board.model;

import java.util.*;

public interface BoardDAO_interface {
          public void insert(BoardVO boardVO);
          public void update(BoardVO boardVO);
          public Boolean delete(Integer boaNo);
          public BoardVO findByPrimaryKey(Integer boaNo);
          public List<BoardVO> findByFK(Integer boatypNo);
          public List<BoardVO> getAll();
//          

}
