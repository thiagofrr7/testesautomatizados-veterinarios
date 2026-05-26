package org.iftm.gerenciadorveterinarios.repositories;

import java.util.Optional;
import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
    Animal save(Animal animal);
    Optional<Animal> findById(Long id);
    
}