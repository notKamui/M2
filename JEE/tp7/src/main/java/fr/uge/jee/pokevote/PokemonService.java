package fr.uge.jee.pokevote;

import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class PokemonService {

    private final PokemonRepository repository;

    @PersistenceContext
    private final EntityManager em;

    public PokemonService(PokemonRepository repository, EntityManager em) {
        this.repository = repository;
        this.em = em;
    }

    @Retryable(value = RuntimeException.class, maxAttempts = 5)
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void insertOrIncrementPokemon(String name) {
        var pokemon = repository.findByName(name).orElseGet(() -> {
            var it = new Pokemon(name);
            repository.save(it);
            return it;
        });
        em.lock(pokemon, javax.persistence.LockModeType.PESSIMISTIC_WRITE);
        pokemon.setScore(pokemon.getScore() + 1);
        repository.save(pokemon);
    }

    public long totalCountVote() {
        return repository.totalCountVote();
    }
}
