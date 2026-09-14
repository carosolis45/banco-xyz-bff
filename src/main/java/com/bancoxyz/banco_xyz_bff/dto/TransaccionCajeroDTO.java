package com.bancoxyz.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionCajeroDTO {
    private String cuentaId;
    private BigDecimal monto;
    private String tipo;
    private String descripcion;
}