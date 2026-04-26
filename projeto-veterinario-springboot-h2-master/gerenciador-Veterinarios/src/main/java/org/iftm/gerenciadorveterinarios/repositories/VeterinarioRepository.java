package org.iftm.gerenciadorveterinarios.repositories;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

    List<Veterinario> findByNomeContains(String nome);

    List<Veterinario> findByNomeIgnoreCase(String nome);

}
