package org.learning.jpa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<SuperHero> superHeros = new HashSet<>();

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<SuperHero> getSuperHeros() {
        return superHeros;
    }

    public void setSuperHeros(Set<SuperHero> superHeros) {
        this.superHeros = superHeros;
    }

    public void addSuperHero(SuperHero superHero){
        this.getSuperHeros().add(superHero);
        superHero.getMovies().add(this);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
