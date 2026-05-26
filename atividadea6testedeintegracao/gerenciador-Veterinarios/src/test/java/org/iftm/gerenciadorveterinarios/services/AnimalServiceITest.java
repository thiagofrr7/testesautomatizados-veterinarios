package org.iftm.gerenciadorveterinarios.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
@Transactional
public class AnimalServiceITest {

    @Autowired
    private AnimalService service;

    @Autowired
    private AnimalRepository repository;

    @Test
    void deveBuscarAnimalPorIdComSucessoComLimiteCaracteres() {

        // Arrange
        Long idExistente = 1L;

        // Act
        Optional<Animal> resultado = service.buscaAnimaisPeloId(idExistente);

        // Assert
        assertTrue(resultado.isPresent());

        // Verifica truncamento do nome
        assertEquals("Luna", resultado.get().getNome());
    }

    @Test
    void deveSalvarAnimalNoBancoDeDados() {

        // Arrange
        Animal novoAnimal = new Animal(null, "Hulk", "Cachorro", 5, false);

        // Act
        Animal salvo = service.cadastrar(novoAnimal);

        // Assert
        assertNotNull(salvo.getId());
        assertEquals("Hulk", salvo.getNome());
        assertTrue(salvo.isInternado());

        Optional<Animal> animalNoBanco =
            repository.findById(salvo.getId());

        assertTrue(animalNoBanco.isPresent());

        assertEquals(
            "Cachorro",
            animalNoBanco.get().getEspecie()
        );
    }

    @Test
    void deveLancarExcecaoAoCadastrarEspecieInvalida() {

        // Arrange
        Animal animalInvalido = new Animal(null, "Rex","Dinossauro",100,false);

        // Act & Assert
        IllegalArgumentException e =
            assertThrows(IllegalArgumentException.class, () -> {

                service.cadastrar(animalInvalido);
            });

        assertEquals(
            "Espécie não atendida pela clínica.",
            e.getMessage()
        );
    }

    @Test
    void deveDarAltaNoAnimal() {

        // Arrange
        Animal animal = new Animal(1L,"Thor", "Cachorro",4, true);

        repository.save(animal);

        // Act
        Animal resultado = service.darAlta(1L);

        // Assert
        assertEquals(false, resultado.isInternado());
    }

    @Test
    void deveLancarExcecaoAoDarAltaAnimalInexistente() {

        // Arrange
        Long idInexistente = 9999L;

        // Act & Assert
        RuntimeException e =
            assertThrows(RuntimeException.class, () -> {

                service.darAlta(idInexistente);
            });

        assertEquals(
            "Animal não encontrado.",
            e.getMessage()
        );
    }
}