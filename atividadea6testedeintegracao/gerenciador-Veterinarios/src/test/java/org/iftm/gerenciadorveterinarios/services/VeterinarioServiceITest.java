package org.iftm.gerenciadorveterinarios.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest 
@Transactional 
public class VeterinarioServiceITest {

    @Autowired
    private VeterinarioService service;
    @Autowired
    private VeterinarioRepository repository;
    

    @Test
    void deveBuscarVeterinarioPorIdComSucessoComLimiteCaracteres() {
        // Arrange
        Integer idExistente = 1; 
        // Act - Chamada real que vai bater lá na tabela do H2
        Optional<Veterinario> resultado = service.buscaVeterinariosPeloId(idExistente);
        // Assert
        assertTrue(resultado.isPresent());
        // verifica a regra de negocio de retornar apenas os 10 primeiros caracteres.
        assertEquals("Conceição ", resultado.get().getNome());
        // Pergunta: "Cadê o verify()?"
    }

    @Test
    void deveSalvarVeterinarioNoBancoDeDados() {
        // Arrange - Objeto novo, sem ID (nulo)
        Veterinario novoVet = new Veterinario(null, "Dra. Marcia", "marcia@gmail.com", "Grandes Animais", BigDecimal.valueOf(5500.0));
        // Act
        Veterinario salvo = service.salvar(novoVet);
        // Assert
        assertNotNull(salvo.getId(), "O banco H2 deveria ter gerado um ID automático!");
        assertEquals("Dra. Marcia", salvo.getNome());
        // Prova Real: Usando o repository para checar se ele está gravado de verdade
        Optional<Veterinario> vetNoBanco = repository.findById(salvo.getId());
        assertTrue(vetNoBanco.isPresent());
        assertEquals("marcia@gmail.com", vetNoBanco.get().getEmail());
    }

    @Test
    void deveLancarExcecaoAoApagarIdNaoExistente() {
        // Arrange
        Integer idInexistente = 9999; // ID que não mapeamos no import.sql
        int quantidadeOriginal = 2;
        // Act & Assert
        RuntimeException e = assertThrows(RuntimeException.class, () -> {
        service.apagar(idInexistente);});
        int quantidadeAtual = service.buscaTodosVeterinarios().size();
        assertEquals("Veterinário com ID 9999 não foi encontrado no banco de dados. Operação de exclusão cancelada.", e.getMessage());
        // Pergunta: "Como garantir que o banco não apagou nada errado?"
        // Podemos checar se a contagem de registros continua igual!
        assertEquals(quantidadeOriginal, quantidadeAtual); 
    }

}