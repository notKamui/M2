package fr.uge.jee.springmvc.pokematch.web.pokemon;

import fr.uge.jee.springmvc.pokematch.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PokemonLeaderboard {

    private final int leaderboardMaxSize;

    private final LinkedHashMap<Integer, Set<Pokemon>> scoreToPokemons;
    private final HashMap<Pokemon, Integer> pokemonToScore;

    public PokemonLeaderboard(int leaderboardMaxSize) {
        this.leaderboardMaxSize = leaderboardMaxSize;
        this.scoreToPokemons = new LinkedHashMap<>();
        this.pokemonToScore = new HashMap<>();
    }

    public static PokemonLeaderboard create(int leaderboardMaxSize) {
        return new PokemonLeaderboard(leaderboardMaxSize);
    }

    // O(1 + 1 + 1 + 1) === O(1)
    public void scorePokemon(Pokemon pokemon) {
        synchronized (scoreToPokemons) {
            // increment the score of the Pokémon and get it
            var score = pokemonToScore.merge(pokemon, 1, Integer::sum); // O(1)

            // get the current score section where the Pokémon is
            var section = scoreToPokemons.get(score - 1); // O(1)
            if (section != null) {
                // remove the Pokémon from the current score section
                section.remove(pokemon); // O(1)
            }

            // add the Pokémon to the new score section
            scoreToPokemons.computeIfAbsent(score, k -> new HashSet<>()).add(pokemon); // O(1)
        }
    }

    // O(n) (et des chouillas mais faut pas le dire)
    public List<Pair<Pokemon, Integer>> getLeaders() {
        synchronized (scoreToPokemons) {
            var leaders = scoreToPokemons.values().stream()
                .flatMap(Collection::stream)
                .map(pokemon -> new Pair<>(pokemon, pokemonToScore.get(pokemon)))
                .skip(Math.max(pokemonToScore.size() - leaderboardMaxSize, 0))
                .collect(Collectors.toList());
            Collections.reverse(leaders);
            return leaders;
        }
    }
}
