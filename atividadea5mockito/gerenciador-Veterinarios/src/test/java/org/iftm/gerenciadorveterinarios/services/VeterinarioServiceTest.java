package org.iftm.gerenciadorveterinarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VeterinarioServiceTest {

    @Mock
    private VeterinarioRepository repositorioMock;

    @InjectMocks
    private VeterinarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBuscarVeterinariosComParteDoNome() {
        // Arrange
        List<Veterinario> listaMockada = Arrays.asList(
            new Veterinario(1, "Dr. Roberto Silva", "", "", null),
            new Veterinario(2, "Dra. Maria Silva", "", "", null)
        );
        when(repositorioMock.findByNomeContains("Silva")).thenReturn(listaMockada);

        // Act
        List<Veterinario> resultado = service.buscaVeterinariosComParteNome("Silva");

        // Assert
        assertEquals(2, resultado.size(), "A lista deveria conter 2 veterinários.");
        verify(repositorioMock, times(1)).findByNomeContains("Silva");
    }

    @Test
    void deveLancarExcecaoAoApagarQuandoIdNaoExistir() {
        // Arrange
        Integer idInexistente = 99; 
        
        Veterinario vetInexistente = new Veterinario(idInexistente, "Inexistente", "", "", null);
        
        when(repositorioMock.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            service.apagar(vetInexistente);
        });

        // Assert
        verify(repositorioMock, never()).delete(any(Veterinario.class));
    }

}