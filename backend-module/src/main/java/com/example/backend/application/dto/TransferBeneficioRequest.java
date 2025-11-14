package com.example.backend.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferBeneficioRequest(

        @NotNull(message = "O id de origem é obrigatório")
        Long fromId,

        @NotNull(message = "O id de destino é obrigatório")
        Long toId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor
) {}