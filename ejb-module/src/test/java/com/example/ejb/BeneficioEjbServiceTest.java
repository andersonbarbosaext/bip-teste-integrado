package com.example.ejb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.entity.Beneficio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

class BeneficioEjbServiceTest {
    
    private BeneficioEjbService service;
    private EntityManager em;

    @BeforeEach
    void setup() {
        em = mock(EntityManager.class);
        service = new BeneficioEjbService();

        // Injeção do mock no campo privado "em"
        try {
            var field = BeneficioEjbService.class.getDeclaredField("em");
            field.setAccessible(true);
            field.set(service, em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Beneficio beneficio(Long id, BigDecimal valor) {
        Beneficio b = new Beneficio();
        b.setId(id);
        b.setValor(valor);
        return b;
    }

    // ------------------------------------------
    // TESTES DE VALIDAÇÃO
    // ------------------------------------------

    @Test
    void deveRejeitarValorZeroOuNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            service.transfer(1L, 2L, BigDecimal.ZERO)
        );
        assertThrows(IllegalArgumentException.class, () ->
            service.transfer(1L, 2L, BigDecimal.valueOf(-10))
        );
    }

    @Test
    void deveRejeitarMesmaOrigemEDestino() {
        assertThrows(IllegalArgumentException.class, () ->
            service.transfer(1L, 1L, BigDecimal.TEN)
        );
    }

    @Test
    void deveLancarErroQuandoOrigemNaoExiste() {
        when(em.find(Beneficio.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT))
            .thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
            service.transfer(1L, 2L, BigDecimal.TEN)
        );
    }

    @Test
    void deveLancarErroQuandoDestinoNaoExiste() {
        when(em.find(Beneficio.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT))
            .thenReturn(beneficio(1L, BigDecimal.valueOf(500)));

        when(em.find(Beneficio.class, 2L, LockModeType.OPTIMISTIC_FORCE_INCREMENT))
            .thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
            service.transfer(1L, 2L, BigDecimal.TEN)
        );
    }

    @Test
    void deveRejeitarSaldoInsuficiente() {
        Beneficio from = beneficio(1L, BigDecimal.valueOf(5));
        Beneficio to = beneficio(2L, BigDecimal.valueOf(100));

        when(em.find(Beneficio.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT)).thenReturn(from);
        when(em.find(Beneficio.class, 2L, LockModeType.OPTIMISTIC_FORCE_INCREMENT)).thenReturn(to);

        assertThrows(IllegalStateException.class, () ->
            service.transfer(1L, 2L, BigDecimal.TEN)
        );
    }

    // ------------------------------------------
    // TESTE DE SUCESSO
    // ------------------------------------------

    @Test
    void deveTransferirComSucesso() {
        Beneficio from = beneficio(1L, BigDecimal.valueOf(500));
        Beneficio to = beneficio(2L, BigDecimal.valueOf(100));
        BigDecimal amount = BigDecimal.valueOf(200);

        when(em.find(Beneficio.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT)).thenReturn(from);
        when(em.find(Beneficio.class, 2L, LockModeType.OPTIMISTIC_FORCE_INCREMENT)).thenReturn(to);

        service.transfer(1L, 2L, amount);

        assertEquals(BigDecimal.valueOf(300), from.getValor());
        assertEquals(BigDecimal.valueOf(300), to.getValor());

        verify(em, times(1)).merge(from);
        verify(em, times(1)).merge(to);
    }
}