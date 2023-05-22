package com.ssafy.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanDto {
	private int idx;
	private String user_id;
	private String title;
	private Timestamp start_date;
	private Timestamp end_date;
	private int recommended;
	private String content;
	private List<PlaceDto> places;
}
