package org.iftm.gerenciadorveterinarios.repositories;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Animal;

public class AnimalRepository {
    
    Animal save(Animal animal);
    Optional<Animal> findById(Long id);
    
}
