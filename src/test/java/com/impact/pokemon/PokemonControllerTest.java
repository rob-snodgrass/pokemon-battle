package com.impact.pokemon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

    private final TestRestTemplate rest;

    PokemonControllerTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    // Admittedly I was not sure how I wanted to test this controller, so I used the template provided
    @Test
    void testAttackPicksWinnerWithHitPoints() {
        String pokemonA = "Bulbasaur";
        String pokemonB = "Charmander";

        Map<String, Object> response = rest.getForObject("/attack?pokemonA=" + pokemonA + "&pokemonB=" + pokemonB, Map.class);
        assertEquals(2, response.size());
        assertEquals("Charmander", response.get("winner"));
        assertEquals(39, response.get("hitPoints"));
    }

    @Test
    public void testAttackEndpointWithInvalidPokemon() {
        String pokemonA = "Warrior";
        String pokemonB = "Mage";


        Map<String, Object> response = rest.getForObject("/attack?pokemonA=" + pokemonA + "&pokemonB=" + pokemonB, Map.class);
        assertNotNull(response, "Expected the response to be not null");
        assertEquals("Invalid Pokemon Selection", response.get("winner"), "Expected an invalid Pokémon selection message");
        assertEquals("Undefined", response.get("hitPoints"), "Expected hit points to be undefined");
    }
}
