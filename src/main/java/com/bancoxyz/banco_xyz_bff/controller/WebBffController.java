package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.TransaccionWebDTO;
import com.bancoxyz.bff.model.Cuenta;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/web")
@RequiredArgsConstructor
public class WebBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    @GetMapping("/transacciones")
    public List<TransaccionWebDTO> getTransacciones() {
        return transaccionService.obtenerTransaccionesWeb();
    }

    @GetMapping("/cuentas")
    public List<Cuenta> getCuentas() {
        return cuentaService.obtenerCuentas();
    }

    @GetMapping("/cuentas/{id}")
    public Cuenta getCuenta(@PathVariable String id) {
        return cuentaService.obtenerCuentaPorId(id).orElse(null);
    }

    // Método para la raíz - Devuelve información en JSON
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("nombre", "Banco XYZ BFF");
        response.put("version", "1.0.0");
        response.put("estado", "activo");
        response.put("descripcion", "Backend For Frontend para servicios bancarios");
        response.put("endpoints", List.of(
            "/api/web/cuentas",
            "/api/web/transacciones",
            "/api/web/cuentas/{id}"
        ));
        return response;
    }
}