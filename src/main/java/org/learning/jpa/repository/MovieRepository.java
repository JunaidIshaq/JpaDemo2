package org.learning.jpa.repository;

import org.learning.jpa.model.Movie;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MovieRepository {

    private EntityManager entityManager;

    public MovieRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Movie> save(Movie movie){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(movie);
            entityManager.getTransaction().commit();
            return Optional.of(movie);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Movie> findById(Integer movieId){
        Movie movie = entityManager.find(Movie.class, movieId);
        return movie != null ? Optional.of(movie) : Optional.empty();
    }

    public List<Movie> findAll(){
        return entityManager.createQuery("from Movie").getResultList();
    }

    public void deleteById(Integer movieId){
        // Retrieve the movie with this Id
        Movie movie = entityManager.find(Movie.class, movieId);
        if(movie != null){
            try{
                // Start a transation because we're going to change that database.
                entityManager.getTransaction().begin();
                // Remove all references to this movie by superheroes
                movie.getSuperHeros().forEach(superHero -> {
                    superHero.getMovies().remove(movie);
                });
                // Now remove the movie;
                entityManager.remove(movie);

                // Commit the Transaction
                entityManager.getTransaction().commit();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
