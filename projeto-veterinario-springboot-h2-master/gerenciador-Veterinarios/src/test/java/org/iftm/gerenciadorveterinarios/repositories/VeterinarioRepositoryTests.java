package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VeterinarioRepositoryTests {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Test
    public void deveRetornarVazio_quandoBuscarNomeExatoEmMinusculo(){

        // Arrange
        String nomeTeste = "pedro";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNome(nomeTeste);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarVeterinario_quandoBuscarNomeExatoMaiusculo(){

        // Arrange
        String nomeTeste = "PEDRO";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNome(nomeTeste);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("PEDRO", resultado.get(0).getNome());
    }

    @Test
    public void deveRetornarVazio_quandoBuscarNomeInexistenteIgnoreCase(){

        // Arrange
        String nomeTeste = "jose";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeIgnoreCase(nomeTeste);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarVeterinario_quandoBuscarNomeIgnoreCase(){

        // Arrange
        String nomeTeste = "joao";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeIgnoreCase(nomeTeste);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("JOAO", resultado.get(0).getNome());
    }
}