package fr.uge.jee.pokevote;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PokemonRepository extends CrudRepository<Pokemon, UUID> {

    Optional<Pokemon> findByName(String name);

    @Query("SELECT SUM(p.score) FROM Pokemon p")
    long totalCountVote();
}
