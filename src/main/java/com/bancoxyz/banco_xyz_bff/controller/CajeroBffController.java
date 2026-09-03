package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.TransaccionCajeroDTO;
import com.bancoxyz.bff.model.Cuenta;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cajero")
@RequiredArgsConstructor
public class CajeroBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    @GetMapping("/transacciones/{cuentaId}")
    public List<TransaccionCajeroDTO> getTransacciones(@PathVariable String cuentaId) {
        return transaccionService.obtenerTransaccionesCajero(cuentaId);
    }

    @GetMapping("/saldo/{cuentaId}")
    public BigDecimal getSaldo(@PathVariable String cuentaId) {
        return cuentaService.obtenerSaldo(cuentaId);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public Cuenta getCuenta(@PathVariable String cuentaId) {
        return cuentaService.obtenerCuentaPorId(cuentaId).orElse(null);
    }
}