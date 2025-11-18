package com.example.backend.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioProjection;

import jakarta.validation.constraints.NotNull;

public class BeneficioDto extends RepresentationModel<BeneficioDto> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7815263268323307680L;

	private Long id;

	@NotNull(message = "O nome é obrigatório")
	private String nome;

	private String descricao;

	@NotNull(message = "A valor é obrigatório")
	private BigDecimal valor = BigDecimal.ZERO;

	private Boolean ativo;

	public BeneficioDto() {
	}

	public BeneficioDto(Beneficio entity) {
		id = entity.getId();
		ativo = entity.getAtivo();
		valor = entity.getValor();
		nome = entity.getNome();
		descricao = entity.getDescricao();
	}

	public BeneficioDto(BeneficioProjection projection) {
		id = projection.getId();
		ativo = projection.getAtivo();
		valor = projection.getValor();
		nome = projection.getNome();
		descricao = projection.getDescricao();
	}

	public Beneficio buildPosition() {
		Beneficio position = new Beneficio();
		position.setId(id);
		position.setNome(nome);
		position.setDescricao(descricao);
		position.setValor(valor);
		position.setAtivo(ativo);
		return position;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ativo, descricao, id, nome, valor);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeneficioDto other = (BeneficioDto) obj;
		return Objects.equals(ativo, other.ativo) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(valor, other.valor);
	}

}
