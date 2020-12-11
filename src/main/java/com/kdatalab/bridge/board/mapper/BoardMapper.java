package com.kdatalab.bridge.board.mapper;

import com.kdatalab.bridge.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> selectBoardList(BoardDto params) throws Exception;
	
	int selectBoardTotalCount(BoardDto params) throws Exception;
	
	boolean insertBoard(BoardDto params) throws Exception;
	
	BoardDto selectBoard(int seq) throws Exception;

	boolean deleteBoard(int seq) throws Exception;
}
