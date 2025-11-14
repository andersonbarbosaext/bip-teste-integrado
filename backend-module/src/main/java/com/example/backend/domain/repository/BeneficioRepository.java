package com.example.backend.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioProjection;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

	@Query(value = "SELECT "
	        + " ben.id, "
	        + " ben.nome, "
	        + " ben.ativo, "
	        + " ben.descricao, "
	        + " ben.valor "
	        + " FROM beneficio ben "
	        + " WHERE ben.ativo = true "
	        + " AND (:nome IS NULL OR ben.nome ILIKE CONCAT('%', :nome, '%'))",
	       nativeQuery = true,
	       countQuery = "SELECT count(*) "
	        + " FROM beneficio ben "
	        + " WHERE ben.ativo = true "
	        + " AND (:nome IS NULL OR ben.nome ILIKE CONCAT('%', :nome, '%'))")
	Page<BeneficioProjection> findAllPageable(@Param("nome") String nome, Pageable paginate);

	List<Beneficio> findByAtivoTrue();
	
}
