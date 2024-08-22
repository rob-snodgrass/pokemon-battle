package com.impact.pokemon.dao;

import com.impact.pokemon.model.Pokemon;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * !! Feel free to change everything about this !!
 * This could be a class to hold all the Pokemon objects loaded from CSV,
 * but there are many ways to do it.
 */
@Component
public class PokemonData {
    private final File file;

    PokemonData() throws IOException {
        file = new ClassPathResource("data/pokemon.csv").getFile();
    }


    public List<Pokemon> retrievePokemon() {
        List<Pokemon> pokemonList = new ArrayList<>();

        try (Scanner fileInput = new Scanner(file)) {

            //Always want to skip the first line if new Pokémon are released
            boolean firstLine = true;

            while (fileInput.hasNextLine()) {
                if(firstLine){
                    firstLine = false;
                    fileInput.nextLine();
                    continue;}
                String lineOfText = fileInput.nextLine();
                String[] lineValues = lineOfText.split(",");
                Pokemon pokemon = mapRowToPokemon(lineValues);
                pokemonList.add(pokemon);
            }

        } catch (FileNotFoundException ex) {
            //todo add something
            ex.printStackTrace();
        }
        return pokemonList;
    }

    private Pokemon mapRowToPokemon(String[] values){
        Pokemon pokemon = new Pokemon();
        //Another way I could do this is delete all the setters for the Pokemon in the model, and create the object here using the constructor. That way we could encapsulate the data more, but would be less memory efficient
        //since I would need to create a bunch of Strings and ints being stored here

        pokemon.setId(Integer.parseInt(values[0]));
        pokemon.setName(values[1]);
        pokemon.setType(values[2]);
        pokemon.setTotal(Integer.parseInt(values[3]));
        pokemon.setHitPoints(Integer.parseInt(values[4]));
        pokemon.setAttack(Integer.parseInt(values[5]));
        pokemon.setDefense(Integer.parseInt(values[6]));
        pokemon.setSpecialAttack(Integer.parseInt(values[7]));
        pokemon.setSpecialDefense(Integer.parseInt(values[8]));
        pokemon.setSpeed(Integer.parseInt(values[9]));
        pokemon.setGeneration(Integer.parseInt(values[10]));
        pokemon.setLegendary(values[11]);

        return pokemon;
    }
}
