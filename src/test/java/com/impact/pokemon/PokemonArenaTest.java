package com.impact.pokemon;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import com.impact.pokemon.service.PokemonArenaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonArenaTest {

    private final TestRestTemplate rest;
    @Autowired
    private PokemonArenaImpl pokemonArena;
    @Autowired
    private PokemonData pokemonData;

    PokemonArenaImpl arena;

    List <Pokemon> mockPokemonList;

    PokemonArenaTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    @BeforeEach
    void setUp() {
        mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon(6, "Charizard", "Fire", 534, 78, 84, 78, 109, 85, 100, 1, "false"));
        mockPokemonList.add(new Pokemon(3, "Venusaur", "Grass", 525, 80, 82, 83, 100, 100, 80, 1, "false"));
        mockPokemonList.add(new Pokemon(95, "Onix", "Water", 385, 35, 45, 160, 30, 45, 70, 1, "false"));
        mockPokemonList.add(new Pokemon(145, "Zapdos", "Electric", 580, 90, 90, 85, 125, 90, 100, 1, "true"));
        PokemonArenaImpl arena = new PokemonArenaImpl();
    }

    // Using hard coded pokemon I can predetermine who will win each round and expect a result to test the battle logic
    

    @Test
    public void testBattleWinner1() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(0).getName(), mockPokemonList.get(1).getName());
        assertEquals("Charizard", winner.getName());
    }

    @Test
    public void testBattleWinner2() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(0).getName(), mockPokemonList.get(2).getName());
        assertEquals("Onix", winner.getName());
    }

    @Test
    public void testBattleWinner3() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(0).getName(), mockPokemonList.get(3).getName());
        assertEquals("Zapdos", winner.getName());
    }


    @Test
    public void testBattleWinner4() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(1).getName(), mockPokemonList.get(2).getName());
        assertEquals("Onix", winner.getName());
    }


    @Test
    public void testBattleWinner5() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(1).getName(), mockPokemonList.get(3).getName());
        assertEquals("Zapdos", winner.getName());
    }

    @Test
    public void testBattleWinner6() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(2).getName(), mockPokemonList.get(3).getName());
        assertEquals("Onix", winner.getName());
    }



}
