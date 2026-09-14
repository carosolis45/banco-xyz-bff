package com.bancoxyz.banco_xyz_bff.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no existe.
 * Se traduce automáticamente a un HTTP 404 por el GlobalExceptionHandler.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}