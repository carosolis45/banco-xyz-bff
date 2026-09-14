package com.bancoxyz.bff.service;

import com.bancoxyz.bff.dto.TransaccionWebDTO;
import com.bancoxyz.bff.dto.TransaccionMovilDTO;
import com.bancoxyz.bff.dto.TransaccionCajeroDTO;
import com.bancoxyz.bff.model.Transaccion;
import com.bancoxyz.bff.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    // ==================== WEB (datos completos) ====================
    public List<TransaccionWebDTO> obtenerTransaccionesWeb() {
        return transaccionRepository.findAll().stream()
                .map(this::convertirAWebDTO)
                .collect(Collectors.toList());
    }

    private TransaccionWebDTO convertirAWebDTO(Transaccion t) {
        return new TransaccionWebDTO(
                t.getId(),
                t.getCuentaId(),
                t.getFecha(),
                t.getMonto(),
                t.getTipo(),
                t.getDescripcion(),
                t.getAnomalia(),
                "$" + t.getMonto().toString(),
                t.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }

    // ==================== MÓVIL (datos ligeros) ====================
    public List<TransaccionMovilDTO> obtenerTransaccionesMovil() {
        return transaccionRepository.findByAnomaliaFalse().stream()
                .map(t -> new TransaccionMovilDTO(
                        t.getCuentaId(),
                        t.getMonto(),
                        t.getTipo(),
                        t.getFecha(),
                        t.getDescripcion()
                ))
                .collect(Collectors.toList());
    }

    // ==================== CAJERO (datos esenciales) ====================
    public List<TransaccionCajeroDTO> obtenerTransaccionesCajero(String cuentaId) {
        return transaccionRepository.findByCuentaId(cuentaId).stream()
                .map(t -> new TransaccionCajeroDTO(
                        t.getCuentaId(),
                        t.getMonto(),
                        t.getTipo(),
                        t.getDescripcion()
                ))
                .collect(Collectors.toList());
    }
}