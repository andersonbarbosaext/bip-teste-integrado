package com.example.backend.domain.model.beneficio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = MenssageContants.NOT_FOUND)
public class BeneficioNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7700375853241171041L;

	public BeneficioNotFoundException() {
		super(MenssageContants.NOT_FOUND);
	}
	
}
