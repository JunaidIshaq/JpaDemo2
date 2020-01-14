import org.learning.jpa.model.Movie;
import org.learning.jpa.model.SuperHero;
import org.learning.jpa.repository.MovieRepository;
import org.learning.jpa.repository.SuperHeroRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {


    public static void main(String[] args) {
        // Create our entity manager

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("superhero");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        MovieRepository movieRepository = new MovieRepository(entityManager);
        SuperHeroRepository superHeroRepository = new SuperHeroRepository(entityManager);

        // Create Some Super Heroes

        SuperHero ironMan = new SuperHero("Iron Man");
        SuperHero thor  = new SuperHero("Thor");

        // Create Some Movies
        Movie avangers = new Movie("The Avengers");
        avangers.addSuperHero(ironMan);
        avangers.addSuperHero(thor);

        Movie infinityWar = new Movie("Avengers : Infinity War");
        infinityWar.addSuperHero(ironMan);
        infinityWar.addSuperHero(thor);

        // Save the movies
        movieRepository.save(avangers);
        movieRepository.save(infinityWar);

        // Find all Movies
        System.out.println("Movies : ");
        movieRepository.findAll().forEach(movie -> {
            System.out.println("Movie : [" + movie.getId() + "] - " + movie.getTitle());
            movie.getSuperHeros().forEach(System.out::println);
        });

        // Find all Superheroes
        System.out.println("\nSuper Heroes : ");
        superHeroRepository.findAll().forEach(superHero -> {
            System.out.println(superHero);
            superHero.getMovies().forEach(System.out::println);
        });

        // Delete a movie and verify that its superheroes are not deleted
        movieRepository.deleteById(2);
        System.out.println("\n Movies (After Delete) : ");
        movieRepository.findAll().forEach(movie -> {
            System.out.println("Movie : [" + movie.getId() + "] - " + movie.getTitle());
            movie.getSuperHeros().forEach(System.out::println);
        });

        superHeroRepository.deleteById(2);
        System.out.println("\n Super Heroes (After Delete) : ");
        superHeroRepository.findAll().forEach(superHero -> {
            System.out.println(superHero);
            superHero.getMovies().forEach(System.out::println);
        });

        // Close the entity manager and associated factory
        entityManager.close();
        entityManagerFactory.close();

    }
}
