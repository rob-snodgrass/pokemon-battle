package com.impact.pokemon.service;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
public class PokemonArenaImpl implements PokemonArena {

    private List<Pokemon> pokemonList;
    @Autowired
    private PokemonData pokemonData;

    public PokemonArenaImpl(PokemonData pokemonData){
        this.pokemonData = pokemonData;
    }

    // For testing
    public PokemonArenaImpl(){}

    @PostConstruct
    public void init(){
        this.pokemonList = pokemonData.retrievePokemon();
    }

    public Pokemon retrievePokemonByName(String name){
        for(Pokemon pokemon : pokemonList){
            if (pokemon.getName().equalsIgnoreCase(name)){
                return pokemon;
            }
        }
        // Return nothing if the pokemon is not found, must include throws NullPointerException
        return null;
        //Throw new PokemonNotFoundException
    }

    public Pokemon pokemonBattle(String pokemonAName, String pokemonBName){
 //       Pokemon pokemonA = retrievePokemonByName(pokemonAName);
 //       Pokemon pokemonB = retrievePokemonByName(pokemonBName);

        Pokemon pokemonA = new Pokemon(retrievePokemonByName(pokemonAName));
        Pokemon pokemonB = new Pokemon(retrievePokemonByName(pokemonBName));

        // Check speed for which one attacks first
        Pokemon firstAttacker = checkSpeed(pokemonA, pokemonB);
        Pokemon secondAttacker = (firstAttacker == pokemonA) ? pokemonB : pokemonA;

        // Add battle logic
        while(firstAttacker.getHitPoints()>0 && secondAttacker.getHitPoints()>0){
            double damage = calculateDamage(firstAttacker, secondAttacker);
            secondAttacker.setHitPoints(secondAttacker.getHitPoints() - damage);

            //Switch their roles after each round
            Pokemon temp = firstAttacker;
            firstAttacker = secondAttacker;
            secondAttacker = temp;

        }

        //Return the pokemon who has health
        return firstAttacker.getHitPoints() > 0 ? firstAttacker : secondAttacker;
    }


    private Pokemon checkSpeed(Pokemon pokemonA, Pokemon pokemonB){
        if (pokemonA.getSpeed() > pokemonB.getSpeed()) {
            return pokemonA;
        }
        else if (pokemonB.getSpeed() > pokemonA.getSpeed()) {
            return pokemonB;
        }
        else {
            //Took this from https://www.tutorialspoint.com/java-program-to-toss-a-coin
            Random r = new Random();
            int result = r.nextInt(2);
            if (result==1){
                return pokemonA;
            }
            else {
                return pokemonB;
            }
        }
    }

    private double calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness = getEffectivenessModifier(attacker.getType(), defender.getType());
        return (50 * (attacker.getAttack() / (double) defender.getDefense()) * effectiveness);
    }


    private double getEffectivenessModifier(String attackerType, String defenderType) {
        if (attackerType.equalsIgnoreCase("Fire") && defenderType.equalsIgnoreCase("Grass")) {
            return 2.0;
        } else if (attackerType.equalsIgnoreCase("Fire") && defenderType.equalsIgnoreCase("Water")) {
            return 0.5;
        } else if (attackerType.equalsIgnoreCase("Water") && defenderType.equalsIgnoreCase("Fire")) {
            return 2.0;
        } else if (attackerType.equalsIgnoreCase("Water") && defenderType.equalsIgnoreCase("Electric")) {
            return 0.5;
        } else if (attackerType.equalsIgnoreCase("Grass") && defenderType.equalsIgnoreCase("Electric")) {
            return 2.0;
        } else if (attackerType.equalsIgnoreCase("Grass") && defenderType.equalsIgnoreCase("Fire")) {
            return 0.5;
        } else if (attackerType.equalsIgnoreCase("Electric") && defenderType.equalsIgnoreCase("Water")) {
            return 2.0;
        } else if (attackerType.equalsIgnoreCase("Electric") && defenderType.equalsIgnoreCase("Grass")) {
            return 0.5;
        } else {
            return 1.0;
        }
    }



}


    /*
    Expecting to receive 2 Pokémon that we want to battle
    While loop to let them continuously attack each other until one runs out of hit points (check hp after each attack)

    Need to calculate the following:

    - Fire is: 2X more effective vs grass, 0.5 effective vs water, neutral against electric
    - Water is: 2X more effective vs fire, 0.5 effective vs electric, neutral against grass
    - Grass is: 2X more effective vs electric, 0.5 effective vs fire, neutral against water
    - Electric is: 2X more effective vs water, 0.5 effective vs grass, neutral against fire
    The pokemon with the highest _Speed_ will always attack first. If two pokemon have the same speed, a random one goes first
    The attack damage can be calculated as follows `50 x (attack of attacking pokemon / defense of defending pokemon) * effectiveness modifier`
        (You're welcome to use a different formula to calculate attack damage based on the pokemon.csv)
    Return the winner

            attack of attacking
      50 *  ___________________     * effectiveness
            defense of defending

     */