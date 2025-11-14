package com.example.ejb;

import java.math.BigDecimal;

import jakarta.ejb.Remote;

@Remote
public interface BeneficioEjbServiceRemote {
	
	void transfer(Long fromId, Long toId, BigDecimal amount);	
}
