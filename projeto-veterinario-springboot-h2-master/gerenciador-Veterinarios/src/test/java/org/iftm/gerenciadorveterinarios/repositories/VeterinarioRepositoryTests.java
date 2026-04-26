package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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

    // CICLO 3

    @Test
    public void retornarVeterinariosComSalarioSuperior(){

        // Arrange
        BigDecimal salario = new BigDecimal("3200.0");

        // Act
        List<Veterinario> resultado =
            veterinarioRepository.findBySalarioGreaterThan(salario);

        // Assert
        assertEquals(3, resultado.size());
    }

    @Test
    public void retornarVeterinariosComSalarioInferior(){

        // Arrange
        BigDecimal salario = new BigDecimal("3200.0");

        // Act
        List<Veterinario> resultado =
            veterinarioRepository.findBySalarioLessThan(salario);

        // Assert
        assertEquals(2, resultado.size());
    }

    @Test
    public void retornarVeterinariosComSalarioEntreFaixaDeValores(){

        // Arrange
        BigDecimal min = new BigDecimal("3000.0");
        BigDecimal max = new BigDecimal("3500.0");

        // Act
        List<Veterinario> resultado =
            veterinarioRepository.findBySalarioBetween(min, max);

        // Assert
        assertEquals(5, resultado.size());
    }

    @Test
    public void atualizarVeterinarioERefletirNasBuscas(){

        // Arrange
        List<Veterinario> lista = veterinarioRepository.findByNome("PEDRO");
        Veterinario vet = lista.get(0);

        vet.setNome("PEDRO ALTERADO");
        vet.setSalario(new BigDecimal("9999.0"));

        // Act
        veterinarioRepository.save(vet);

        // Assert (nome antigo)
        List<Veterinario> buscaAntiga =
            veterinarioRepository.findByNome("PEDRO");

        assertTrue(buscaAntiga.isEmpty());

        // Assert (nome novo)
        List<Veterinario> buscaNova =
            veterinarioRepository.findByNome("PEDRO ALTERADO");

        assertEquals(1, buscaNova.size());
        assertEquals("PEDRO ALTERADO", buscaNova.get(0).getNome());

        // Assert (salário novo)
        List<Veterinario> salarioNovo =
            veterinarioRepository.findBySalarioGreaterThan(new BigDecimal("9000"));

        assertEquals(1, salarioNovo.size());
        
    }

}