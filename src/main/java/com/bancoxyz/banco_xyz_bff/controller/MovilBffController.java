package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.CuentaMovilDTO;
import com.bancoxyz.bff.dto.SaldoMovilDTO;
import com.bancoxyz.bff.dto.TransaccionMovilDTO;
import com.bancoxyz.banco_xyz_bff.exception.RecursoNoEncontradoException;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/movil")
@RequiredArgsConstructor
public class MovilBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    /**
     * Transacciones en formato ligero para el frontend Móvil.
     */
    @GetMapping("/transacciones")
    public ResponseEntity<List<TransaccionMovilDTO>> getTransacciones() {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesMovil());
    }

    /**
     * Resumen de cuentas (versión ligera, solo campos esenciales).
     */
    @GetMapping("/cuentas")
    public ResponseEntity<List<CuentaMovilDTO>> getCuentasResumen() {
        List<CuentaMovilDTO> cuentas = cuentaService.obtenerCuentas()
                .stream()
                .map(CuentaMovilDTO::new)
                .toList();
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Saldo de una cuenta específica.
     * Si la cuenta no existe, se lanza RecursoNoEncontradoException (404).
     */
    @GetMapping("/saldo/{cuentaId}")
    public ResponseEntity<SaldoMovilDTO> getSaldo(@PathVariable String cuentaId) {
        BigDecimal saldo = cuentaService.obtenerSaldo(cuentaId);

        // Verificar que la cuenta exista
        if (cuentaService.obtenerCuentaPorId(cuentaId).isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "Cuenta no encontrada con ID: " + cuentaId);
        }

        SaldoMovilDTO response = new SaldoMovilDTO(cuentaId, saldo);
        return ResponseEntity.ok(response);
    }
}