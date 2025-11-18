package com.example.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.backend.infra.exeption.BusinessException;
import com.example.backend.infra.exeption.FieldError;


class BusinessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        BusinessException ex = new BusinessException("Erro de teste");

        assertEquals("Erro de teste", ex.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithException() {
        Exception cause = new RuntimeException("Erro interno");

        BusinessException ex = new BusinessException(cause);

        assertEquals(cause, ex.getCause());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithExceptionAndStatus() {
        Exception cause = new RuntimeException("Erro interno");

        BusinessException ex = new BusinessException(cause, HttpStatus.BAD_REQUEST);

        assertEquals(cause, ex.getCause());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageAndException() {
        Exception cause = new RuntimeException("Erro 500");

        BusinessException ex = new BusinessException("Falha", cause);

        assertEquals("Falha", ex.getMessage());
        assertEquals(cause, ex.getCause());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageExceptionAndStatus() {
        Exception cause = new RuntimeException("Erro");

        BusinessException ex = new BusinessException("Falhou", cause, HttpStatus.NOT_FOUND);

        assertEquals("Falhou", ex.getMessage());
        assertEquals(cause, ex.getCause());
        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageAndStatus() {
        BusinessException ex = new BusinessException("Erro custom", HttpStatus.UNAUTHORIZED);

        assertEquals("Erro custom", ex.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getHttpStatus());
    }

    @Test
    void testConstructorWithFieldErrorsAndStatus() {
        FieldError f1 = new FieldError("obj", "campo");
        List<FieldError> list = List.of(f1);

        BusinessException ex = new BusinessException(list, HttpStatus.BAD_REQUEST);

        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertEquals(list, ex.getFieldErrors());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    @Test
    void testConstructorWithMessageFieldErrorsAndStatus() {
        FieldError f1 = new FieldError("obj", "campo");
        List<FieldError> list = List.of(f1);

        BusinessException ex = new BusinessException("Falhou", list, HttpStatus.CONFLICT);

        assertEquals("Falhou", ex.getMessage());
        assertEquals(list, ex.getFieldErrors());
        assertEquals(HttpStatus.CONFLICT, ex.getHttpStatus());
    }

    @Test
    void testSetters() {
        BusinessException ex = new BusinessException("Erro");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
        assertNotNull(ex.getFieldErrors());
    }
}
