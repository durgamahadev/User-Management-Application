package com.ashokit.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UnlockEntry {
	private String email;
	private String temporaryPassword;
	private String newPassword;
	private String confirmPassword;

}
