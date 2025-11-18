package com.example.rest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.backend.application.dto.BeneficioDto;
import com.example.backend.application.dto.TransferBeneficioRequest;
import com.example.backend.application.service.BeneficioApplication;
import com.example.backend.rest.BeneficioController;
import com.fasterxml.jackson.databind.ObjectMapper;


class BeneficioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BeneficioApplication app;

    @InjectMocks
    private BeneficioController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        mapper = new ObjectMapper();
    }
    private BeneficioDto dto(Long id, String nome) {
        BeneficioDto d = new BeneficioDto();
        d.setId(id);
        d.setNome(nome);
        return d;
    }


    // ---------------------------------------------------------
    // GET /api/v1/beneficios
    // ---------------------------------------------------------

    @Test
    void deveListarTodosBeneficios() throws Exception {
        when(app.findAll()).thenReturn(List.of(dto(1L, "Vale")));

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Vale"));
    }
    
    @Test
    void deveRetornarBeneficioPorId() throws Exception {
        when(app.findById(1L)).thenReturn(dto(1L, "Teste"));

        mockMvc.perform(get("/api/v1/beneficios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    void deveSalvarBeneficio() throws Exception {
        BeneficioDto body = dto(null, "Vale");

        when(app.save(any(BeneficioDto.class))).thenReturn(body);

        mockMvc.perform(post("/api/v1/beneficios")
                .content(mapper.writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Vale"));
    }

    @Test
    void deveAtualizarBeneficio() throws Exception {
        BeneficioDto body = dto(1L, "Novo Nome");

        when(app.update(eq(1L), any(BeneficioDto.class))).thenReturn(body);

        mockMvc.perform(put("/api/v1/beneficios/1")
                .content(mapper.writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }

    @Test
    void deveExcluirBeneficio() throws Exception {
        doNothing().when(app).deleteById(1L);

        mockMvc.perform(delete("/api/v1/beneficios/1"))
                .andExpect(status().isNoContent());
    }


    // ---------------------------------------------------------
    // POST /api/v1/beneficios/transfer
    // ---------------------------------------------------------

    @Test
    void deveTransferirEntreBeneficios() throws Exception {
        TransferBeneficioRequest req =
            new TransferBeneficioRequest(1L, 2L, BigDecimal.TEN);

        doNothing().when(app)
                .transfer(req.fromId(), req.toId(), req.valor());

        mockMvc.perform(post("/api/v1/beneficios/transfer")
                .content(mapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}