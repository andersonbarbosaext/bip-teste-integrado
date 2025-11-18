package com.example.application;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.backend.application.dto.BeneficioDto;
import com.example.backend.application.service.BeneficioApplication;
import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioNotFoundException;
import com.example.backend.domain.model.beneficio.BeneficioProjection;
import com.example.backend.domain.service.BeneficioService;
import com.example.backend.infra.exeption.BusinessException;
import com.example.ejb.BeneficioEjbServiceRemote;


class BeneficioApplicationTest {

    private BeneficioApplication app;
    private BeneficioService service;
    private BeneficioEjbServiceRemote ejb;

    @BeforeEach
    void setup() {
        service = mock(BeneficioService.class);
        ejb = mock(BeneficioEjbServiceRemote.class);
        app = new BeneficioApplication(service, ejb);
    }

    private Beneficio beneficio(Long id, String nome, BigDecimal valor) {
        Beneficio b = new Beneficio();
        b.setId(id);
        b.setNome(nome);
        b.setValor(valor);
        b.setAtivo(true);
        return b;
    }

    private BeneficioDto dto(Long id, String nome) {
        BeneficioDto d = new BeneficioDto();
        d.setId(id);
        d.setNome(nome);
        return d;
    }


    @Test
    void deveRetornarListaDeBeneficios() {
        Beneficio b1 = beneficio(1L, "Vale Alimentação", BigDecimal.TEN);
        Beneficio b2 = beneficio(2L, "Vale Transporte", BigDecimal.ONE);

        when(service.findAll()).thenReturn(List.of(b1, b2));

        List<BeneficioDto> result = app.findAll();

        assertEquals(2, result.size());
        assertEquals("Vale Alimentação", result.get(0).getNome());
        verify(service, times(1)).findAll();
    }


    @Test
    void deveRetornarPaginaDeBeneficios() {
        PageRequest pageable = PageRequest.of(0, 10);

        BeneficioProjection p = mock(BeneficioProjection.class);
        when(p.getId()).thenReturn(1L);

        Page<BeneficioProjection> page = new PageImpl<>(List.of(p));

        when(service.findAllPageable(any(), eq(pageable))).thenReturn(page);

        Page<BeneficioDto> result = app.findAllPageable("abc", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void deveRetornarPaginaVazia() {
        PageRequest pageable = PageRequest.of(0, 10);

        when(service.findAllPageable(any(), eq(pageable)))
                .thenReturn(Page.empty());

        Page<BeneficioDto> result = app.findAllPageable(null, pageable);

        assertTrue(result.isEmpty());
    }

    @Test
    void deveRetornarBeneficioPorId() {
        Beneficio b = beneficio(1L, "Auxílio Moradia", BigDecimal.TEN);

        when(service.findById(1L)).thenReturn(Optional.of(b));

        BeneficioDto dto = app.findById(1L);

        assertEquals("Auxílio Moradia", dto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BeneficioNotFoundException.class, () -> app.findById(1L));
    }

    @Test
    void deveSalvarBeneficio() throws BusinessException {
        BeneficioDto dto = dto(null, "Vale Refeição");
   
        doAnswer(invocation -> {
            Beneficio arg = invocation.getArgument(0);
            arg.setId(10L);
            return null;
        }).when(service).save(any(Beneficio.class));

        BeneficioDto result = app.save(dto);

        assertNotNull(result.getId());
        assertEquals("Vale Refeição", result.getNome());
        verify(service, times(1)).save(any());
    }

    @Test
    void deveRecusarNomeInvalido() {
        BeneficioDto dto = dto(null, "A");

        assertThrows(BusinessException.class, () -> app.save(dto));
    }

    @Test
    void deveAtualizarBeneficio() throws BusinessException {
        Beneficio base = beneficio(1L, "Antigo", BigDecimal.TEN);

        when(service.findById(1L)).thenReturn(Optional.of(base));

        BeneficioDto dto = dto(1L, "Novo Nome");

        BeneficioDto result = app.update(1L, dto);

        assertEquals("Novo Nome", result.getNome());
        verify(service, times(1)).update(base);
    }

    @Test
    void deveFalharAoAtualizarBeneficioInexistente() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        BeneficioDto dto = dto(1L, "Teste");

        assertThrows(BeneficioNotFoundException.class, () -> app.update(1L, dto));
    }

    @Test
    void deveDeletarBeneficio() throws BusinessException {
        Beneficio b = beneficio(1L, "XPTO", BigDecimal.TEN);

        when(service.findById(1L)).thenReturn(Optional.of(b));

        app.deleteById(1L);

        verify(service, times(1)).delete(b);
    }

    @Test
    void deveFalharAoDeletarInexistente() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> app.deleteById(1L));
    }

    @Test
    void deveChamarEJBTransfer() throws BusinessException {
        app.transfer(1L, 2L, BigDecimal.TEN);

        verify(ejb, times(1)).transfer(1L, 2L, BigDecimal.TEN);
    }

}