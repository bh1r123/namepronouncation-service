package com.wellsafrgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="DB issues")
public class DatbaseconnectivityException extends Exception{

	private static final long serialVersionUID = 1L;

	public DatbaseconnectivityException(String message){
    	super(message);
    }
}
