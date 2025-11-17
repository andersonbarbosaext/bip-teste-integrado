package com.example.backend.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.application.dto.BeneficioDto;
import com.example.backend.application.dto.TransferBeneficioRequest;
import com.example.backend.application.service.BeneficioApplication;
import com.example.backend.infra.exeption.BusinessException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {


	private static final Logger logger = LogManager.getLogger(BeneficioController.class);

	private final BeneficioApplication beneficioApplication;
	
	public BeneficioController(BeneficioApplication beneficioApplication) {
		this.beneficioApplication = beneficioApplication;
	}

	@GetMapping(params = {"name"})
	@Operation(summary = "Busca todos beneficios com páginação", description = "Busca todos os beneficios com páginação", tags = { "Beneficio" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BeneficioDto.class))) }),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public Page<BeneficioDto> findAll(@RequestParam(required = false) String name, Pageable paginate) {
		return beneficioApplication.findAllPageable(name, paginate);
	}
		
	@GetMapping
	@Operation(summary = "Busca todos os beneficios", description = "Busca todo os beneficio", tags = { "beneficio" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BeneficioDto.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<List<BeneficioDto>> findAll() {
		return  new ResponseEntity<>(beneficioApplication.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca beneficio por Id", description = "Busca beneficio por Id", tags = { "beneficio" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BeneficioDto.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<BeneficioDto> findById(@PathVariable Long id) {
		return new ResponseEntity<>(beneficioApplication.findById(id), HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Adiciona um novo beneficio", description = "Adiciona um novo beneficio passando uma representação JSON do Beneficio!", tags = {
			"beneficio" }, responses = {
					@ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = BeneficioDto.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
  
	public ResponseEntity<BeneficioDto> save(@Valid @RequestBody final BeneficioDto beneficioDto)
			throws BusinessException {

		logger.info("save() method started");

		beneficioApplication.save(beneficioDto);

		logger.info("save() method ended");

		return ResponseEntity.status(HttpStatus.CREATED).body(beneficioDto);
	}

	@PutMapping("/{id}") 
	@Operation(summary = "Atualiza um beneficio", description = "Atualiza um beneficio passando uma representação JSON do Beneficio", tags = {
			"beneficio" }, responses = {
					@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = BeneficioDto.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), })
	public ResponseEntity<BeneficioDto> update(@PathVariable Long id, @Valid @RequestBody BeneficioDto beneficioDto) {
		
		logger.info("update() method started");

		beneficioApplication.update(id, beneficioDto);

		logger.info("update() method ended");
		return ResponseEntity.ok(beneficioDto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um beneficio", description = "Exclui um beneficio passando o id", tags = {
			"beneficio" }, responses = { @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content), })
	public ResponseEntity<Void> delete(@PathVariable Long id) throws BusinessException {
		
		logger.info("delete() method started");
		
		beneficioApplication.deleteById(id);
		
		logger.info("delete() method ended");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/transfer")
	@Operation(
	        summary = "Transfere valor entre benefícios",
	        description = "Transfere um valor de um benefício de origem para um benefício de destino",
	        tags = { "beneficio" },
	        responses = {
	                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
	                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
	                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
	                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	        }
	)
	public ResponseEntity<Void> transfer(@Valid @RequestBody TransferBeneficioRequest request) throws BusinessException {

	    logger.info("transfer() method started. fromId={}, toId={}, valor={}",
	            request.fromId(), request.toId(), request.valor());

	    beneficioApplication.transfer(request.fromId(), request.toId(), request.valor());

	    logger.info("transfer() method ended");

	    return ResponseEntity.noContent().build();
	}
}
