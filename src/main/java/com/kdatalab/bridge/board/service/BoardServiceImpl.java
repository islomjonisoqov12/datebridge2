package com.kdatalab.bridge.board.service;

import com.kdatalab.bridge.board.dto.BoardDto;
import com.kdatalab.bridge.board.repository.BoardRepository;
import com.kdatalab.bridge.board.paging.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	@Override
	public List<BoardDto> selectBoardList(BoardDto params) throws Exception {
		// TODO Auto-generated method stub
		
		int boardTotalCount = boardRepository.selectBoardTotalCount(params);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);
		params.setPaginationInfo(paginationInfo);
		
		List<BoardDto> boardList = boardRepository.selectBoardList(params.getPaginationInfo().getFirstRecordIndex(), params.getRecordsPerPage());
		
		return boardList;
	}


	@Override
	public boolean insertBoard(BoardDto params) throws Exception {
		// TODO Auto-generated method stub
		return boardRepository.insertBoard(params.getSeq(), params.getBbsId(), params.getTitle(), params.getNoticeat(), params.getAtchmnfl(), params.getCn(), params.getRegUser(),params.getModUser());
	}


	@Override
	public BoardDto selectBoard(int seq) throws Exception {
		// TODO Auto-generated method stub
		return boardRepository.selectBoard(seq) ;
	}


	@Override
	public boolean deleteBoard(int seq) throws Exception {
		// TODO Auto-generated method stub
		return boardRepository.deleteBoard(seq);
	}

}
