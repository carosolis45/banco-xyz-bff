package com.bancoxyz.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para exponer el saldo en el BFF Cajero.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoCajeroDTO {

    private String cuentaId;
    private BigDecimal saldo;
}