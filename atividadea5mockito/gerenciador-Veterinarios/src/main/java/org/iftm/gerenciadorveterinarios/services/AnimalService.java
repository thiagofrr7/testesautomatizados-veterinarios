package org.iftm.gerenciadorveterinarios.services;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
    
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public Animal cadastrar(Animal animal) {
        if ("Dinossauro".equalsIgnoreCase(animal.getEspecie())) {
            throw new IllegalArgumentException("Espécie não atendida pela clínica.");
        }

        animal.setInternado(true);
        
        return repository.save(animal);
    }
    
}

