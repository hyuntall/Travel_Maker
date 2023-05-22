package com.ssafy.dto;

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
public class PlaceDto {
	private int idx;
	private String name;
	private String url;
	private String latitude;
	private String longitude;
	private String address;
	private String category_code;
	private int plan_idx;
	private int date_index;
	private int index;
	private String comment;
}
