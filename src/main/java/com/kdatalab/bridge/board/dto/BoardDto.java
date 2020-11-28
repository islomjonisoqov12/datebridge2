package com.kdatalab.bridge.board.dto;

import lombok.Data;

@Data
public class BoardDto extends CommonDto{
	
	private Integer seq;
	private Integer bbsId;
	private String title;
	private String noticeat;
	private String atchmnfl;
	private String cn;
	private String regUser;
	private String regDt;
	private String modUser;
	private String modDt;
	
}
