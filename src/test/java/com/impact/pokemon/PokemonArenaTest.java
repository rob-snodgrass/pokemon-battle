package com.impact.pokemon;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import com.impact.pokemon.service.PokemonArenaImpl;
import org.junit.jupiter.api.BeforeAll;
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
    private PokemonArenaImpl arena;
    @Autowired
    private PokemonData pokemonData;

    List <Pokemon> mockPokemonList;

    PokemonArenaTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    @BeforeAll
    static void init(){
        PokemonArenaImpl arena = new PokemonArenaImpl();
    }
    @BeforeEach
    void setUp() {
        mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon(6, "Charizard", "Fire", 534, 78, 84, 78, 109, 85, 100, 1, "false"));
        mockPokemonList.add(new Pokemon(3, "Venusaur", "Grass", 525, 80, 82, 83, 100, 100, 80, 1, "false"));
        mockPokemonList.add(new Pokemon(95, "Onix", "Water", 385, 35, 45, 160, 30, 45, 70, 1, "false"));
        mockPokemonList.add(new Pokemon(101, "Electrode", "Electric", 480,60,50,70,80,80,140,1,"false"));

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
        assertEquals("Charizard", winner.getName());
    }


    @Test
    public void testBattleWinner4() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(1).getName(), mockPokemonList.get(2).getName());
        assertEquals("Venusaur", winner.getName());
    }


    @Test
    public void testBattleWinner5() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(1).getName(), mockPokemonList.get(3).getName());
        assertEquals("Venusaur", winner.getName());
    }


    @Test
    public void testBattleWinner6() {
        Pokemon winner = arena.pokemonBattle(mockPokemonList.get(2).getName(), mockPokemonList.get(3).getName());
        assertEquals("Electrode", winner.getName());
    }

    @Test
    public void testRetrievePokemonByName_LowerCase() {
        Pokemon result = arena.retrievePokemonByName("bulbasaur");
        assertNotNull(result, "Expected Bulbasaur to be found with lowercase input.");
        assertEquals("Bulbasaur", result.getName(), "Expected name to be Bulbasaur.");
    }

    @Test
    public void testRetrievePokemonByName_UpperCase() {
        Pokemon result = arena.retrievePokemonByName("BULBASAUR");
        assertNotNull(result, "Expected Bulbasaur to be found with uppercase input.");
        assertEquals("Bulbasaur", result.getName(), "Expected name to be Bulbasaur.");
    }

    @Test
    public void testRetrievePokemonByName_MixedCase() {
        Pokemon result = arena.retrievePokemonByName("bUlBaSaUr");
        assertNotNull(result, "Expected Bulbasaur to be found with mixed case input.");
        assertEquals("Bulbasaur", result.getName(), "Expected name to be Bulbasaur.");
    }

}
