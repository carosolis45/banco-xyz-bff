package com.bancoxyz.banco_xyz_bff.exception;

/**
 * Excepción lanzada cuando una solicitud contiene datos inválidos.
 * Se traduce automáticamente a un HTTP 400 por el GlobalExceptionHandler.
 */
public class SolicitudInvalidaException extends RuntimeException {

    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }
}