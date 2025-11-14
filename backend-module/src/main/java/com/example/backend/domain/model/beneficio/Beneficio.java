package com.example.backend.domain.model.beneficio;

import java.io.Serializable;
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
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 180258786808293080L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(nullable = false)
	private String nome;
	
	private String descricao;
	
    @Column(nullable = false)
    private BigDecimal valor = BigDecimal.ZERO;
    
    private Boolean ativo;

    @Version
    private Long version;
    
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}