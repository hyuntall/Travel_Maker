package com.ssafy.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QnaDto {
	private int id;
	private String title;
	private String content;
	private int userId;
}
