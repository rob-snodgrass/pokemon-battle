package com.impact.pokemon.controller;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import com.impact.pokemon.service.PokemonArenaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    @Autowired
    private PokemonArenaImpl arena;

    //this will become /pokemon to retrieve all for the front end to test
    @GetMapping("pokemon")
    public List<Pokemon> retrieveAllPokemon(){
        return data.retrievePokemon();
    }


    /**
     * Accepts two pokemon names to determine who would win in a fight
     * @param pokemonA String name of Pokemon A
     * @param pokemonB String name of Pokemon B
     * @return Map<String,Object> of which Pokemon won as well as their remaining HP
     * @throws IOException
     */
    @GetMapping("attack")
    public Map<String, Object> attack(@RequestParam String pokemonA,@RequestParam String pokemonB) throws IOException {
        logger.info("Requested pokemonA: {}, pokemonB: {}", pokemonA, pokemonB);

        Pokemon winner = arena.pokemonBattle(pokemonA,pokemonB);


        return Map.of(
                "winner", winner.getName(),
                "hitPoints", winner.getHitPoints());

//        return Map.of(
//                "winner", arena.pokemonBattle(pokemonA,pokemonB).getName(),
//                "hitPoints", arena.pokemonBattle(pokemonA,pokemonB).getHitPoints());
    }
}
