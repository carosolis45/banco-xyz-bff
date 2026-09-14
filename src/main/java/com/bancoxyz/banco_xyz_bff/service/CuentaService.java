package com.bancoxyz.bff.service;

import com.bancoxyz.bff.model.Cuenta;
import com.bancoxyz.bff.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> obtenerCuentaPorId(String cuentaId) {
        return cuentaRepository.findByCuentaId(cuentaId);
    }

    public BigDecimal obtenerSaldo(String cuentaId) {
        return cuentaRepository.findByCuentaId(cuentaId)
                .map(Cuenta::getSaldo)
                .orElse(BigDecimal.ZERO);
    }
}