package com.kdatalab.bridge.board.controller;

import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.board.dto.BoardDto;
import com.kdatalab.bridge.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController extends BaseController {
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 리스트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board",method = RequestMethod.GET)
	public ModelAndView boardList(@ModelAttribute("params") BoardDto params) throws Exception{
			
		ModelAndView mv = new ModelAndView("board/list.html");
		List<BoardDto> boardList = boardService.selectBoardList(params);
		mv.addObject("boardList",boardList);
		
		return mv;
	}
	
	/**
	 * 수정화면 불러오기
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board/write/{seq}",method = RequestMethod.GET)
	public ModelAndView boardWrite(@PathVariable("seq") int seq) throws Exception{
		
		ModelAndView mv = new ModelAndView("board/write.html");
		BoardDto board = boardService.selectBoard(seq);
		mv.addObject("board",board);
		
		return mv;
	}
	
	
	/**
	 * 새글쓰기 화면 불러오기
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board/write",method = RequestMethod.GET)
	public ModelAndView boardWrite(BoardDto params) throws Exception{
		ModelAndView mv = new ModelAndView("board/write.html");
		BoardDto board = new BoardDto();
		mv.addObject("board",board);
		
		return mv;
	}
	
	/**
	 * 새글등록,수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board/write",method = RequestMethod.POST)
	public String boardInsert(BoardDto params) throws Exception{
		boardService.insertBoard(params);
		return "redirect:/board";
	}
	
	/**
	 * 삭제하기
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board/write/{seq}",method = RequestMethod.DELETE)
	public String boardDelete(@PathVariable("seq") int seq) throws Exception{
		
		boardService.deleteBoard(seq);
		return "redirect:/board";
		
	}
	
	/**
	 * 글보기
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="board/{seq}",method = RequestMethod.GET)
	public ModelAndView viewBoard(@PathVariable("seq") int seq) throws Exception{
		ModelAndView mv = new ModelAndView("board/view.html");
		BoardDto board = boardService.selectBoard(seq);
		mv.addObject("board",board);
		return mv;
	}
	
	
}
