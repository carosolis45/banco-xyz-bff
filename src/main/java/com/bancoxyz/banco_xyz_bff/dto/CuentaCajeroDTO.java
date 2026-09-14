package com.bancoxyz.bff.dto;

import com.bancoxyz.bff.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para exponer datos de Cuenta en el BFF Cajero.
 * Solo incluye campos operativos para operaciones críticas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaCajeroDTO {

    private String cuentaId;
    private String nombre;
    private BigDecimal saldo;

    /**
     * Constructor que mapea desde la entidad Cuenta.
     */
    public CuentaCajeroDTO(Cuenta cuenta) {
        this.cuentaId = cuenta.getCuentaId();
        this.nombre = cuenta.getNombre();
        this.saldo = cuenta.getSaldo();
    }
}