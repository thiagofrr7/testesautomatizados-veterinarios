package org.iftm.gerenciadorveterinarios.repositories;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

    List<Veterinario> findByNome(String nome);

    List<Veterinario> findByNomeIgnoreCase(String nome);

    List<Veterinario> findByNomeContainingIgnoreCase(String nome);

    List<Veterinario> findBySalarioGreaterThan(BigDecimal salario);

    List<Veterinario> findBySalarioLessThan(BigDecimal salario);

    List<Veterinario> findBySalarioBetween(BigDecimal min, BigDecimal max);

}
