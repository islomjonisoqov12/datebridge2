package com.kdatalab.bridge.sub.paging;

import lombok.Data;

/**
 * 교육 페이지용 공통 정보
 * @author Enclouds
 * @since 2020.12.11
 */
@Data
public class CommonSubDto extends CriteriaSub {
	
	private PaginationSubInfo paginationSubInfo;
	
}
