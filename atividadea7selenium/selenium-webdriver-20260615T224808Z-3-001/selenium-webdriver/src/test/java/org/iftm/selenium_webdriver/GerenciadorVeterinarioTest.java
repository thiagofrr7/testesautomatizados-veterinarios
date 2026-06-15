package org.iftm.selenium_webdriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GerenciadorVeterinarioTest {

    private WebDriver driver;
    private static final String URL_BASE = "http://localhost:8080/home";

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test

    public void testarListagemDeVeterinariosExistentes() {
        // Arrange
        String tituloPagina = "Gerenciador de Veterinários";
        String nomePrimeiraLinha = "Conceição Evaristo";

        // Act
        driver.get(URL_BASE);
        WebElement linha1Coluna1 = driver.findElement(By.xpath("//tbody/tr[2]/td[1]"));
        String tituloObtido = driver.getTitle();

        // Assert
        assertTrue(driver.getCurrentUrl().contains("/home"));
        assertEquals(tituloPagina, tituloObtido);
        assertEquals(nomePrimeiraLinha, linha1Coluna1.getText());
    }

    @Test
    public void testarPesquisarVeterinariosExistentes() {
        // Arrange
        driver.get(URL_BASE);

        // Act
        WebElement btnConsultar = driver.findElement(By.xpath("//button[contains(text(),'Consultar')]"));
        btnConsultar.click();

        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Erica Queiroz Pinto");

        WebElement btnPesquisar = driver.findElement(By.cssSelector("button[type='submit']"));

        // Assert
        assertEquals("Erica Queiroz Pinto", campoNome.getAttribute("value"));
        btnPesquisar.click();
    }

    @Test
    public void testarCadatrarNovoVeterinario() {
        // Arrange
        driver.get(URL_BASE);
        WebElement btnAdicionar = driver.findElement(By.xpath("//button[contains(text(),'Adicionar')]"));
        btnAdicionar.click();

        WebElement textoNome = driver.findElement(By.id("nome"));
        WebElement textoEmail = driver.findElement(By.id("inputEmail"));
        WebElement textoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        WebElement textoSalario = driver.findElement(By.id("inputSalario"));

        // Act
        textoNome.sendKeys("Thiago");
        textoEmail.sendKeys("thiagoferreira2@gmail.com");
        textoEspecialidade.sendKeys("grandes");
        textoSalario.sendKeys("5500.00");
        
        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[contains(.,'Cadastrar')]"));
        botaoCadastrar.click();

        WebElement ultimaLinhaNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));

        // Assert
        assertTrue(driver.getCurrentUrl().contains("/home"));
        assertEquals("Thiago", ultimaLinhaNome.getText());
    }
    
    @Test
    public void testarAlterarCadastroDeVeterinario(){
        // Arrange
        driver.get(URL_BASE);
        WebElement btnAlterar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[1]"));
        btnAlterar.click();

        WebElement textoNome = driver.findElement(By.id("nome"));
        WebElement textoEmail = driver.findElement(By.id("inputEmail"));
        WebElement textoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        WebElement textoSalario = driver.findElement(By.id("inputSalario"));

        // Act
        textoNome.clear();
        textoEmail.clear();
        textoEspecialidade.clear();
        textoSalario.clear();

        textoNome.sendKeys("Maria Gomes");
        textoEmail.sendKeys("mariagomes@gmail.com");
        textoEspecialidade.sendKeys("pequenos");
        textoSalario.sendKeys("360000");

        WebElement btnAtualizar = driver.findElement(By.xpath("//button[normalize-space()='Atualizar']"));
        btnAtualizar.click();

        WebElement ultimoNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));

        // Assert
        assertEquals("Maria Gomes", ultimoNome.getText());
    }

    @Test
    public void testarDeletarCadastroDeVeterinario(){
        // Arrange
        driver.get(URL_BASE);
        WebElement ultimoNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));
        String nomeVeterinario = ultimoNome.getText();
        WebElement btnDeletar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[2]"));

        // Act
        btnDeletar.click();
        boolean aindaExiste = driver.findElements(By.xpath("//tbody/tr/td[contains(text(), '" + nomeVeterinario + "')]")).size() > 0;

        // Assert
        assertTrue(!aindaExiste);
    }
    
    @AfterEach
    public void exit(){
        if (driver != null) {
            driver.quit();
        }
    }
}