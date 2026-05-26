package org.iftm.gerenciadorveterinarios.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AnimalServiceTest {

    @Mock
    private AnimalRepository repositoryMock;

    @InjectMocks
    private AnimalService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarAnimalComStatusInternadoTruePorPadrao() {
        // Arrange
        Animal animalNovo = new Animal(null, "Thor", "Cachorro", 3, false);
        
        when(repositoryMock.save(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Animal animalSalvo = service.cadastrar(animalNovo);

        // Assert
        assertTrue(animalSalvo.isInternado(), "O status padrão ao cadastrar deve ser TRUE (Internado).");
        verify(repositoryMock, times(1)).save(animalNovo);
    }

    @Test
    void barrarCadastroDeEspecieInvalida() {
        // Arrange
        Animal animalInvalido = new Animal(null, "Rex", "Dinossauro", 5, true);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(animalInvalido);
        });

        verify(repositoryMock, never()).save(any(Animal.class));
    }

    @Test
    void darAltaParaOAnimal() {
        // Arrange
        Animal animalInternado = new Animal(1L, "Mel", "Gato", 2, true);
        when(repositoryMock.findById(1L)).thenReturn(Optional.of(animalInternado));
        when(repositoryMock.save(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Animal animalComAlta = service.darAlta(1L);

        // Assert
        assertFalse(animalComAlta.isInternado(), "O status do animal deveria ser alterado para FALSE (Alta).");
        verify(repositoryMock, times(1)).findById(1L);
        verify(repositoryMock, times(1)).save(animalInternado);
    }
}