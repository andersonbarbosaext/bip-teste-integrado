package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "beneficio")
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private BigDecimal valor = BigDecimal.ZERO;

    public Beneficio() {}

    public Beneficio(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

	public void setId(Long id) {
		this.id = id;	
	}
}