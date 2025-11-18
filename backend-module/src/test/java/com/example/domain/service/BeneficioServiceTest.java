package com.example.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.backend.domain.model.beneficio.Beneficio;
import com.example.backend.domain.model.beneficio.BeneficioProjection;
import com.example.backend.domain.repository.BeneficioRepository;
import com.example.backend.domain.service.BeneficioService;

class BeneficioServiceTest {

    @Mock
    private BeneficioRepository beneficioRepository;

    @InjectMocks
    private BeneficioService beneficioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPageable() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        BeneficioProjection p = mock(BeneficioProjection.class);

        Page<BeneficioProjection> page = new PageImpl<>(List.of(p));

        when(beneficioRepository.findAllPageable("abc", pageRequest))
                .thenReturn(page);

        Page<BeneficioProjection> result = beneficioService.findAllPageable("abc", pageRequest);

        assertEquals(1, result.getContent().size());
        verify(beneficioRepository).findAllPageable("abc", pageRequest);
    }

    @Test
    void testFindAll() {
        Beneficio b1 = new Beneficio();
        Beneficio b2 = new Beneficio();

        when(beneficioRepository.findByAtivoTrue())
                .thenReturn(Arrays.asList(b1, b2));

        List<Beneficio> result = beneficioService.findAll();

        assertEquals(2, result.size());
        verify(beneficioRepository).findByAtivoTrue();
    }

    @Test
    void testFindById() {
        Beneficio b = new Beneficio();
        b.setId(1L);

        when(beneficioRepository.findById(1L))
                .thenReturn(Optional.of(b));

        Optional<Beneficio> result = beneficioService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(beneficioRepository).findById(1L);
    }

    @Test
    void testSave() {
        Beneficio b = new Beneficio();

        beneficioService.save(b);

        assertTrue(b.getAtivo());
        verify(beneficioRepository).save(b);
    }

    @Test
    void testUpdate() {
        Beneficio b = new Beneficio();

        beneficioService.update(b);

        verify(beneficioRepository).save(b);
    }

    @Test
    void testDelete() {
        Beneficio b = new Beneficio();
        b.setAtivo(true);

        beneficioService.delete(b);

        assertFalse(b.getAtivo());
        verify(beneficioRepository).save(b);
    }
}