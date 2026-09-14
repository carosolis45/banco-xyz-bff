package com.bancoxyz.bff.controller;

import com.bancoxyz.bff.dto.CuentaWebDTO;
import com.bancoxyz.bff.dto.TransaccionWebDTO;
import com.bancoxyz.banco_xyz_bff.exception.RecursoNoEncontradoException;
import com.bancoxyz.bff.service.CuentaService;
import com.bancoxyz.bff.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/web")
@RequiredArgsConstructor
public class WebBffController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    /**
     * Lista completa de transacciones para el frontend Web.
     */
    @GetMapping("/transacciones")
    public ResponseEntity<List<TransaccionWebDTO>> getTransacciones() {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesWeb());
    }

    /**
     * Lista completa de cuentas (usa DTO, no la entidad JPA).
     */
    @GetMapping("/cuentas")
    public ResponseEntity<List<CuentaWebDTO>> getCuentas() {
        List<CuentaWebDTO> cuentas = cuentaService.obtenerCuentas()
                .stream()
                .map(CuentaWebDTO::new)
                .toList();
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Detalle de una cuenta específica.
     * Si no existe, se lanza RecursoNoEncontradoException (404).
     */
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<CuentaWebDTO> getCuenta(@PathVariable String id) {
        CuentaWebDTO cuenta = cuentaService.obtenerCuentaPorId(id)
                .map(CuentaWebDTO::new)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta no encontrada con ID: " + id));
        return ResponseEntity.ok(cuenta);
    }

    /**
     * Página de bienvenida del BFF Web.
     */
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