package com.kdatalab.bridge.board.service;

import java.util.List;

import com.kdatalab.bridge.board.dto.BoardDto;

public interface BoardService {

	List<BoardDto> selectBoardList(BoardDto params) throws Exception;

	boolean insertBoard(BoardDto params) throws Exception;

}
