package com.example.backend.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioProjection;
import com.example.backend.domain.repository.BeneficioRepository;

@Service
@Transactional
public class BeneficioService {
	
	private final BeneficioRepository beneficioRepository;

	public BeneficioService(BeneficioRepository beneficioRepository) {
		super();
		this.beneficioRepository = beneficioRepository;
	}

	public Page<BeneficioProjection> findAllPageable(String name, Pageable paginate) {
		return beneficioRepository.findAllPageable(name, paginate);		
	}
	
	public List<Beneficio> findAll() {
		return beneficioRepository.findByAtivoTrue();		
	}
	
	public Optional<Beneficio> findById(Long id){
		return beneficioRepository.findById(id);
	}	
	
	public void save(Beneficio beneficio) {
		beneficio.setAtivo(true);
		beneficioRepository.save(beneficio);		
	}

	public void update(Beneficio beneficio) {
		beneficioRepository.save(beneficio);
	}

	public void delete(Beneficio beneficio) {	
		beneficio.setAtivo(false);
		beneficioRepository.save(beneficio);
	}	
}
