package org.iftm.gerenciadorveterinarios.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
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

    public Animal darAlta(Long id) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado."));
        
        animal.setInternado(false); 
        return repository.save(animal);
    }

    @Transactional
    public Optional<Animal> buscaAnimaisPeloId(Long id){
        Optional<Animal> anm = repository.findById(id);
        if (anm.get().getNome().length()>10){
            anm.get().setNome(anm.get().getNome().substring(0, 10));
        }
        return anm;
    }
    
}

