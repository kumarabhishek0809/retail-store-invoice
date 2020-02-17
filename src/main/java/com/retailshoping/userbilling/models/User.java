package com.retailshoping.userbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

	private UserType type;
	private LocalDate dateOfRegistration;
}
