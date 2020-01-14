package org.learning.jpa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="super_hero")
public class SuperHero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="super_hero_movies",
              joinColumns = {@JoinColumn(name="super_hero_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")})
    private Set<Movie> movies = new HashSet<>();

    public SuperHero() {
    }

    public SuperHero(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "SuperHero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies.stream().map(Movie::getTitle).collect(Collectors.toList()) + '\'' +
                '}';
    }
}
