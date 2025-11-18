package com.example.backend.application.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.application.dto.BeneficioDto;
import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioNotFoundException;
import com.example.backend.domain.model.beneficio.BeneficioProjection;
import com.example.backend.domain.service.BeneficioService;
import com.example.backend.infra.exeption.BusinessException;
import com.example.backend.rest.BeneficioController;
import com.example.ejb.BeneficioEjbServiceRemote;

@Service
public class BeneficioApplication {

	private final BeneficioService beneficioService;

	private final BeneficioEjbServiceRemote beneficioEjbServiceRemote;

	public BeneficioApplication(BeneficioService beneficioService,
			BeneficioEjbServiceRemote beneficioEjbServiceRemote) {
		this.beneficioService = beneficioService;
		this.beneficioEjbServiceRemote = beneficioEjbServiceRemote;

	}

	public Page<BeneficioDto> findAllPageable(String name, Pageable paginate) {

		if (name != null) {
			name = name.toLowerCase().replace(" ", "%");
		}

		Page<BeneficioProjection> list = beneficioService.findAllPageable(name, paginate);

		if (!list.isEmpty()) {
			Page<BeneficioDto> positions = list.map(BeneficioDto::new);
			positions.stream().forEach(x -> x.add(linkTo(methodOn(BeneficioController.class).findById(x.getId())).withRel("1111").withSelfRel()));
			return positions;
		}

		return Page.empty();
	}

	public List<BeneficioDto> findAll() {
		List<Beneficio> list = beneficioService.findAll();

		List<BeneficioDto> dtos = list.stream().map(BeneficioDto::new).toList();
		dtos.forEach(x -> x.add(linkTo(methodOn(BeneficioController.class).findById(x.getId())).withSelfRel()));
		return dtos;
	}

	public BeneficioDto findById(Long id) throws BeneficioNotFoundException {

		Optional<Beneficio> beneficio = beneficioService.findById(id);

		if (!beneficio.isPresent())
			throw new BeneficioNotFoundException();

		var dto = new BeneficioDto(beneficio.get());
		dto.add(linkTo(methodOn(BeneficioController.class).findById(id)).withSelfRel());
		return dto;
	}

	public BeneficioDto save(BeneficioDto beneficioDto) throws BusinessException {

		validateCreate(beneficioDto);

		Beneficio beneficio = beneficioDto.buildPosition();

		beneficioService.save(beneficio);
		BeanUtils.copyProperties(beneficio, beneficioDto);
		beneficioDto.add(linkTo(methodOn(BeneficioController.class).findById(beneficioDto.getId())).withSelfRel());
		return beneficioDto;

	}

	public BeneficioDto update(Long id, BeneficioDto beneficioDto) throws BusinessException {

		validateUpdate(beneficioDto);

		Beneficio base = beneficioService.findById(id).orElseThrow(BeneficioNotFoundException::new);

		Beneficio beneficio = beneficioDto.buildPosition();

		BeanUtils.copyProperties(beneficio, base, "id", "ativo");

		beneficioService.update(base);

		return beneficioDto;
	}

	public void deleteById(Long id) throws BusinessException {
		try {
			Beneficio base = beneficioService.findById(id).orElseThrow(BeneficioNotFoundException::new);
			beneficioService.delete(base);
		}catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	private void validateFields(BeneficioDto beneficioDto) throws BusinessException {
		if (beneficioDto.getNome().length() < 2) {
			throw new BusinessException("O nome do beneficio deve conter 2 ou mais caracteres!");
		}
	}

	private void validateUpdate(BeneficioDto beneficioDto) throws BusinessException {
		validateFields(beneficioDto);
	}

	private void validateCreate(BeneficioDto beneficioDto) throws BusinessException {
		validateFields(beneficioDto);
	}

	public void transfer(Long fromId, Long toId, BigDecimal valor) throws BusinessException {
		try {
			beneficioEjbServiceRemote.transfer(fromId, toId, valor);				
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

}
