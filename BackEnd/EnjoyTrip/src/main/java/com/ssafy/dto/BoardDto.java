package com.ssafy.dto;

import java.sql.Timestamp;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {
	private int idx;
	private String user_id;
	private String title;
	private String content; 
	private Timestamp written_date;
	private Timestamp updated_date;
	private boolean is_notice;
	private int view_count;
	private String image;
}
 