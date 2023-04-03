package com.kdatalab.bridge.board.dto;

import com.kdatalab.bridge.board.paging.Criteria;
import com.kdatalab.bridge.board.paging.PaginationInfo;
import lombok.Data;

@Data
public class CommonDto extends Criteria {
	
	private PaginationInfo paginationInfo;
	
	
	
}
