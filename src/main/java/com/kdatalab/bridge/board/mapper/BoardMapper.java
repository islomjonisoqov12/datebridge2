package com.kdatalab.bridge.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kdatalab.bridge.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> selectBoardList(BoardDto params) throws Exception;
	
	int selectBoardTotalCount(BoardDto params) throws Exception;
	
	boolean insertBoard(BoardDto params) throws Exception;
	
	BoardDto selectBoard(int seq) throws Exception;

	boolean deleteBoard(int seq) throws Exception;
}
