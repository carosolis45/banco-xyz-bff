package com.bancoxyz.banco_xyz_bff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para todos los BFF.
 * Estandariza las respuestas de error para Web, Móvil y Cajero.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de recurso no encontrado (HTTP 404).
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(
            RecursoNoEncontradoException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de solicitud inválida (HTTP 400).
     */
    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleSolicitudInvalida(
            SolicitudInvalidaException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    /**
     * Maneja errores de validación de @Valid (HTTP 400).
     * Devuelve un mapa con los campos inválidos y sus mensajes.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erroresCampos.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("mensaje", "Uno o más campos son inválidos");
        respuesta.put("campos", erroresCampos);
        respuesta.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier excepción no controlada (HTTP 500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + ex.getMessage(),
                request
        );
    }

    /**
     * Método auxiliar para construir respuestas de error estandarizadas.
     */
    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus status, String mensaje, WebRequest request) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", status.value());
        respuesta.put("error", status.getReasonPhrase());
        respuesta.put("mensaje", mensaje);
        respuesta.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(respuesta, status);
    }
}