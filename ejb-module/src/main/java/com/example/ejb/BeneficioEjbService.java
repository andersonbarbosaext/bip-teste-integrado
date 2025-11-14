package com.example.ejb;

import java.math.BigDecimal;

import com.example.entity.Beneficio;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BeneficioEjbService implements BeneficioEjbServiceRemote {

	@PersistenceContext(unitName = "ejb-beneficioPU")
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
    	
    	if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Origem e destino devem ser diferentes");
        }
        
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        
        
        if (from == null) {
            throw new IllegalArgumentException("Benefício de origem não encontrado");
        }

        if (to == null) {
            throw new IllegalArgumentException("Benefício de destino não encontrado");
        }
        
        // VALIDAÇÃO DE SALDO
        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente no benefício de origem");
        }
        
        // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
