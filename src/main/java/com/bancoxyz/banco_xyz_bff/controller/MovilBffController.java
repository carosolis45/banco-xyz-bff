package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.TransaccionMovilDTO;
import com.bancoxyz.bff.model.Cuenta;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movil")
@RequiredArgsConstructor
public class MovilBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    @GetMapping("/transacciones")
    public List<TransaccionMovilDTO> getTransacciones() {
        return transaccionService.obtenerTransaccionesMovil();
    }

    @GetMapping("/cuentas")
    public List<Cuenta> getCuentasResumen() {
        return cuentaService.obtenerCuentas();
    }

    @GetMapping("/saldo/{cuentaId}")
    public Double getSaldo(@PathVariable String cuentaId) {
        return cuentaService.obtenerSaldo(cuentaId).doubleValue();
    }
}