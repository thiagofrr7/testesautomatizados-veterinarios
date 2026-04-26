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

    // CICLO 1

    @Test
    public void buscaPorNomeMinusculoExata(){

        // Arrange
        String nomeTeste = "pedro";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNome(nomeTeste);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void buscaPorNomeMaiusculoExata(){

        // Arrange
        String nomeTeste = "PEDRO";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNome(nomeTeste);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("PEDRO", resultado.get(0).getNome());
    }

    @Test
    public void buscaPorNomeInexistenteCaseInsensitive(){

        // Arrange
        String nomeTeste = "jose";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeIgnoreCase(nomeTeste);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void buscaPorNomeExistenteCaseInsensitive(){

        // Arrange
        String nomeTeste = "joao";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeIgnoreCase(nomeTeste);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("JOAO", resultado.get(0).getNome());
    }

    // CICLO 2 

    @Test
    public void buscaSilabaRo(){

        // Arrange
        String nomeTeste = "ro";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeContainingIgnoreCase(nomeTeste);;

        // Assert
        assertEquals(2, resultado.size());

    }

    @Test
    public void buscaNomeMaria(){

        // Arrange
        String nomeTeste = "Maria";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeContainingIgnoreCase(nomeTeste);;

        // Assert
        assertTrue(resultado.isEmpty());

    }

    @Test
    public void buscaStringVazia(){

        // Arrange
        String nomeTeste = "";

        // Act
        List<Veterinario> resultado = veterinarioRepository.findByNomeContainingIgnoreCase(nomeTeste);;

        // Assert
        assertEquals(6, resultado.size()); 

    }

}