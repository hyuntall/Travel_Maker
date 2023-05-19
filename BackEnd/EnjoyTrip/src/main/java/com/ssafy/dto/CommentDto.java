package com.ssafy.dto;

import java.sql.Timestamp;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {
	private int idx;
	private String user_id;
	private int board_idx;
	private String content; 
	private Timestamp written_date;
	private Timestamp updated_date;
}
 