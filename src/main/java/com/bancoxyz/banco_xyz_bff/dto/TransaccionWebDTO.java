package com.bancoxyz.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionWebDTO {
    private Long id;
    private String cuentaId;
    private LocalDate fecha;
    private BigDecimal monto;
    private String tipo;
    private String descripcion;
    private Boolean anomalia;
    private String saldoFormateado;
    private String fechaFormateada;
}