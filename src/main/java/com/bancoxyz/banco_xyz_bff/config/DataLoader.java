package com.bancoxyz.bff.config;

import com.bancoxyz.bff.model.Cuenta;
import com.bancoxyz.bff.model.Transaccion;
import com.bancoxyz.bff.repository.CuentaRepository;
import com.bancoxyz.bff.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("📥 Cargando datos desde CSV...");

        cargarCuentas();
        cargarTransacciones();

        log.info("✅ Datos cargados exitosamente!");
        log.info("📊 Cuentas: {}", cuentaRepository.count());
        log.info("📊 Transacciones: {}", transaccionRepository.count());
    }

    private void cargarCuentas() throws Exception {
        if (cuentaRepository.count() > 0) {
            log.info("⏭️ Cuentas ya cargadas, saltando...");
            return;
        }

        List<Cuenta> cuentas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data/intereses.csv").getInputStream()))) {

            String line = reader.readLine(); // Skip header
            int count = 0;

            while ((line = reader.readLine()) != null && count < 20) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    try {
                        Cuenta cuenta = new Cuenta();
                        cuenta.setCuentaId(parts[0].trim());
                        cuenta.setNombre(parts[1].trim());
                        cuenta.setSaldo(parseBigDecimal(parts[2]));
                        cuenta.setEdad(parseInteger(parts[3]));
                        cuenta.setTipo(parts[4].trim());
                        cuentas.add(cuenta);
                        count++;
                    } catch (Exception e) {
                        log.warn("⚠️ Error al procesar cuenta: {}", line);
                    }
                }
            }
        }
        cuentaRepository.saveAll(cuentas);
        log.info("✅ Cuentas cargadas: {}", cuentas.size());
    }

    private void cargarTransacciones() throws Exception {
        if (transaccionRepository.count() > 0) {
            log.info("⏭️ Transacciones ya cargadas, saltando...");
            return;
        }

        List<Transaccion> transacciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data/transacciones.csv").getInputStream()))) {

            String line = reader.readLine(); // Skip header
            int count = 0;

            while ((line = reader.readLine()) != null && count < 20) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        Transaccion transaccion = new Transaccion();
                        transaccion.setCuentaId(parts[0].trim());
                        transaccion.setFecha(parseFecha(parts[1]));
                        transaccion.setMonto(parseBigDecimal(parts[2]));
                        transaccion.setTipo(parts[3].trim());
                        transaccion.setDescripcion(parts.length > 4 ? parts[4].trim() : "Sin descripción");
                        transacciones.add(transaccion);
                        count++;
                    } catch (Exception e) {
                        log.warn("⚠️ Error al procesar transacción: {}", line);
                    }
                }
            }
        }
        transaccionRepository.saveAll(transacciones);
        log.info("✅ Transacciones cargadas: {}", transacciones.size());
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) return BigDecimal.ZERO;
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private LocalDate parseFecha(String value) {
        if (value == null || value.trim().isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(value.trim());
        } catch (Exception e) {
            try {
                return LocalDate.parse(value.trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            } catch (Exception ex) {
                try {
                    return LocalDate.parse(value.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (Exception ex2) {
                    return LocalDate.now();
                }
            }
        }
    }
}