package com.ssafy.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegionDto {
	private int idx;
	private String cate1;
	private String cate2;
	private String descr;
	private String img;
	private String cate1_eng;
}
