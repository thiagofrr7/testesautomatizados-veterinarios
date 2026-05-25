package org.iftm.gerenciadorveterinarios.entities;

public class Animal {
    
    private Long id;
    private String nome;
    private String especie;
    private int idade;
    private boolean internado;

    public Animal(){}

    public Animal(Long id, String nome, String especie, int idade, boolean internado){
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.internado = internado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isInternado() {
        return internado;
    }

    public void setInternado(boolean internado) {
        this.internado = internado;
    }
    
}
