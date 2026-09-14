package com.bancoxyz.bff.dto;

import com.bancoxyz.bff.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO ligero para exponer datos de Cuenta en el BFF Móvil.
 * Solo incluye campos esenciales para reducir el consumo de ancho de banda.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMovilDTO {

    private String cuentaId;
    private String nombre;
    private BigDecimal saldo;

    /**
     * Constructor que mapea desde la entidad Cuenta.
     */
    public CuentaMovilDTO(Cuenta cuenta) {
        this.cuentaId = cuenta.getCuentaId();
        this.nombre = cuenta.getNombre();
        this.saldo = cuenta.getSaldo();
    }
}