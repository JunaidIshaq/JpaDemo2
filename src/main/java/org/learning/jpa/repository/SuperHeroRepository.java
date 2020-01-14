package org.learning.jpa.repository;

import org.learning.jpa.model.SuperHero;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SuperHeroRepository {

    private EntityManager entityManager;

    public SuperHeroRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<SuperHero> save(SuperHero superHero){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(superHero);
            entityManager.getTransaction().commit();
            return Optional.of(superHero);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<SuperHero> findById(Integer superheroId){
        SuperHero superHero = entityManager.find(SuperHero.class, superheroId);
        return superHero != null ? Optional.of(superHero) : Optional.empty();
    }

    public List<SuperHero> findAll(){
        return entityManager.createQuery("from SuperHero").getResultList();
    }

    public void deleteById(Integer superHeroId) {
        // Retrieve the movie with this Id
        SuperHero superHero = entityManager.find(SuperHero.class, superHeroId);
        if(superHero != null){
            try{
                // Start a transaction because we're going to change the database.
                entityManager.getTransaction().begin();

                // Remove all references to this superhero in its movies
                superHero.getMovies().forEach(movie -> {
                    movie.getSuperHeros().remove(superHero);
                });

                // Now remove the superhero
                entityManager.remove(superHero);

                // Commit the transaction
                entityManager.getTransaction().commit();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
