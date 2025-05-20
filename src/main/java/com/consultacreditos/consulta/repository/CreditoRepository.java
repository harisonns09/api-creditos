package com.consultacreditos.consulta.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.consultacreditos.consulta.model.Credito;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<Credito> findByClienteId(Integer clienteId);
    // Outros métodos de consulta podem ser definidos aqui

    List<Credito> findByNumeroNfse(String numeroNfse);

    Optional<Credito> findByNumeroCredito(String numeroCredito);

    List<Credito> findByValorFaturadoBetween(BigDecimal min, BigDecimal max);

    List<Credito> findByValorFaturadoGreaterThanEqual(BigDecimal min);

    List<Credito> findByValorFaturadoLessThanEqual(BigDecimal max);

}
