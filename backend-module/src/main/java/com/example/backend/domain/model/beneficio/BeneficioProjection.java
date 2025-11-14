package com.example.backend.domain.model.beneficio;

import java.math.BigDecimal;

public interface BeneficioProjection {

	Long getId();
	
	String getNome();
	
	String getDescricao();

	BigDecimal getValor();
	
	Boolean getAtivo();
}
