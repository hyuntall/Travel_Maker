package com.ssafy.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlanCommentDto {
	private int idx;
	private String user_id;
	private int plan_idx;
	private String content; 
}
 