package com.nextuple.orderpricingapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
	
	private int status;
	private String errorMessage;

}
