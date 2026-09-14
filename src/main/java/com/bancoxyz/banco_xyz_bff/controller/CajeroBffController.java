package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.CuentaCajeroDTO;
import com.bancoxyz.bff.dto.SaldoCajeroDTO;
import com.bancoxyz.bff.dto.TransaccionCajeroDTO;
import com.bancoxyz.banco_xyz_bff.exception.RecursoNoEncontradoException;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cajero")
@RequiredArgsConstructor
public class CajeroBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    /**
     * Transacciones de una cuenta específica.
     */
    @GetMapping("/transacciones/{cuentaId}")
    public ResponseEntity<List<TransaccionCajeroDTO>> getTransacciones(
            @PathVariable String cuentaId) {
        List<TransaccionCajeroDTO> transacciones =
                transaccionService.obtenerTransaccionesCajero(cuentaId);
        return ResponseEntity.ok(transacciones);
    }

    /**
     * Saldo de una cuenta específica.
     * Si la cuenta no existe, se lanza RecursoNoEncontradoException (404).
     */
    @GetMapping("/saldo/{cuentaId}")
    public ResponseEntity<SaldoCajeroDTO> getSaldo(@PathVariable String cuentaId) {
        // Verificar que la cuenta existe
        if (cuentaService.obtenerCuentaPorId(cuentaId).isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "Cuenta no encontrada con ID: " + cuentaId);
        }

        BigDecimal saldo = cuentaService.obtenerSaldo(cuentaId);
        SaldoCajeroDTO response = new SaldoCajeroDTO(cuentaId, saldo);
        return ResponseEntity.ok(response);
    }

    /**
     * Detalle completo de una cuenta.
     * Si la cuenta no existe, se lanza RecursoNoEncontradoException (404).
     */
    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<CuentaCajeroDTO> getCuenta(@PathVariable String cuentaId) {
        CuentaCajeroDTO cuenta = cuentaService.obtenerCuentaPorId(cuentaId)
                .map(CuentaCajeroDTO::new)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta no encontrada con ID: " + cuentaId));
        return ResponseEntity.ok(cuenta);
    }
}