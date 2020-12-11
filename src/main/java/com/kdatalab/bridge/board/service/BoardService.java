package com.kdatalab.bridge.board.service;

import com.kdatalab.bridge.board.dto.BoardDto;

import java.util.List;

public interface BoardService {

	List<BoardDto> selectBoardList(BoardDto params) throws Exception;

	boolean insertBoard(BoardDto params) throws Exception;

	BoardDto selectBoard(int seq) throws Exception ;

	boolean deleteBoard(int seq) throws Exception;

}
