package com.safehiring.ems.model.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserVo {

	private String username;
	private String userpwd;

	private boolean isPaid;
	private Set<String> roles;


}