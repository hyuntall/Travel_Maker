package com.ssafy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
	private String id;
	private String password;
	private String name;
	private int age;
	private boolean is_admin;
	private String phone_number;
	private char gender;
	private String image;
}
