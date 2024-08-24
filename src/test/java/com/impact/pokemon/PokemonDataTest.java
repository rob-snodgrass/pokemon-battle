package com.impact.pokemon;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonDataTest {

    private final TestRestTemplate rest;
    @Autowired
    private PokemonData pokemonData;
    List <Pokemon> pokemonList;

    PokemonDataTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    @BeforeEach
    public void setup() throws IOException{
        pokemonList = pokemonData.retrievePokemon();
    }
    @Test
    void testCsvReadCorrectly() {
        assertNotNull(pokemonList);
        assertFalse(pokemonList.isEmpty());
        assertEquals(45,pokemonList.get(0).getHitPoints(),"Bulbasaur has 45 hit points");

    }
}
