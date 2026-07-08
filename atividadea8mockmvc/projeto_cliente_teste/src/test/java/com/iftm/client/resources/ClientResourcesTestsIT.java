package com.iftm.client.resources;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientResourcesTestsIT {

    @Autowired
    private MockMvc mockMvc;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 3L;
        nonExistingId = 33L;
    }

    @Test
    @DisplayName("findAll deve retornar uma página com todos os clientes existentes")
    public void findAllShouldReturnPage() throws Exception {
        // Arrange
        int quantidadeClientes = 12;

        // Act
        ResultActions result = mockMvc.perform(get("/clients/")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content").isArray());
        result.andExpect(jsonPath("$.totalElements").exists());
        result.andExpect(jsonPath("$.totalElements").value(quantidadeClientes));
        result.andExpect(jsonPath("$.content[?(@.id == '%s')]", 3L).exists());
        result.andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists());
        result.andExpect(jsonPath("$.content[?(@.name == '%s')]", "Clarice Lispector").exists());
        result.andExpect(jsonPath("$.content[?(@.name == '%s')]", "Toni Morrison").exists());
        result.andExpect(jsonPath("$.content[*].id", containsInAnyOrder(4, 10, 3, 1, 6, 5, 12, 7, 2, 11, 8, 9)));
    }

    @Test
    @DisplayName("findById deve retornar o cliente quando o id existe")
    public void findByIdShouldReturnClientWhenIdExists() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/clients/id/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value("Clarice Lispector"));
        result.andExpect(jsonPath("$.cpf").value("10919444522"));
        result.andExpect(jsonPath("$.income").value(3800.0));
        result.andExpect(jsonPath("$.birthDate").exists());
        result.andExpect(jsonPath("$.children").value(2));
    }

    @Test
    @DisplayName("findById deve retornar Not Found quando o id não existe")
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/clients/id/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.timestamp").exists());
        result.andExpect(jsonPath("$.status").value(404));
        result.andExpect(jsonPath("$.error").value("Resource not found"));
        result.andExpect(jsonPath("$.message").value("Entity not found"));
        result.andExpect(jsonPath("$.path").value("/clients/id/33"));
    }
}