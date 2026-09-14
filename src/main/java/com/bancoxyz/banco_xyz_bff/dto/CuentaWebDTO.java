package com.bancoxyz.bff.dto;

import com.bancoxyz.bff.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para exponer datos de Cuenta en el BFF Web.
 * Evita exponer la entidad JPA directamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaWebDTO {

    private String cuentaId;
    private String nombre;
    private BigDecimal saldo;
    private Integer edad;
    private String tipo;

    /**
     * Constructor que mapea desde la entidad Cuenta.
     */
    public CuentaWebDTO(Cuenta cuenta) {
        this.cuentaId = cuenta.getCuentaId();
        this.nombre = cuenta.getNombre();
        this.saldo = cuenta.getSaldo();
        this.edad = cuenta.getEdad();
        this.tipo = cuenta.getTipo();
    }
}