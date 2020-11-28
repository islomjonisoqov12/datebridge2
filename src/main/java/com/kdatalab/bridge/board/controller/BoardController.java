package com.kdatalab.bridge.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kdatalab.bridge.board.service.BoardService;
import com.kdatalab.bridge.board.dto.BoardDto;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="board/list",method = RequestMethod.GET)
	public ModelAndView boardList(@ModelAttribute("params") BoardDto params) throws Exception{
			
		ModelAndView mv = new ModelAndView("board/list.html");
		List<BoardDto> boardList = boardService.selectBoardList(params);
		mv.addObject("boardList",boardList);
		
		return mv;
	}
	
	@RequestMapping(value="board/write",method = RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "board/write.html";
	}
	
	@RequestMapping(value="board/write",method = RequestMethod.POST)
	public String insertBoard(BoardDto params) throws Exception{
		
		boardService.insertBoard(params);
		
		return "redirect:/board/list";
	}
	
	
}
