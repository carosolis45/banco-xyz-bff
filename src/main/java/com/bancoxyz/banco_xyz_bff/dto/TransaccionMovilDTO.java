package com.bancoxyz.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionMovilDTO {
    private String cuentaId;
    private BigDecimal monto;
    private String tipo;
    private LocalDate fecha;
    private String descripcion;
}