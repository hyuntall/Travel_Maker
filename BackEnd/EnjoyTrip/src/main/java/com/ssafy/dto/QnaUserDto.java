package com.ssafy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QnaUserDto {
	private int idx;
	private String title;
	private String content;
	private String user_id;
}
