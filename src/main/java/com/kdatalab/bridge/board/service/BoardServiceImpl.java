package com.kdatalab.bridge.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdatalab.bridge.board.dto.BoardDto;
import com.kdatalab.bridge.board.mapper.BoardMapper;
import com.kdatalab.bridge.board.paging.PaginationInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	@Override
	public List<BoardDto> selectBoardList(BoardDto params) throws Exception {
		// TODO Auto-generated method stub
		
		int boardTotalCount = boardMapper.selectBoardTotalCount(params);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);
		params.setPaginationInfo(paginationInfo);
		
		List<BoardDto> boardList = boardMapper.selectBoardList(params);
		
		return boardList;
	}


	@Override
	public boolean insertBoard(BoardDto params) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.insertBoard(params);
	}


	@Override
	public BoardDto selectBoard(int seq) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectBoard(seq) ;
	}


	@Override
	public boolean deleteBoard(int seq) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.deleteBoard(seq);
	}

}
