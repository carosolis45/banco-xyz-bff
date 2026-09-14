package com.bancoxyz.bff.repository;

import com.bancoxyz.bff.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaId(String cuentaId);
    List<Transaccion> findByAnomaliaFalse();
}