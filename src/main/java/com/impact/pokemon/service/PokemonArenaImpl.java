package com.impact.pokemon.service;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PokemonArenaImpl implements PokemonArena {

    private List<Pokemon> pokemonList;
    @Autowired
    private PokemonData pokemonData;

    public PokemonArenaImpl(PokemonData pokemonData){
        this.pokemonData = pokemonData;
    }

    @PostConstruct
    public void init(){
        this.pokemonList = pokemonData.retrievePokemon();
    }

    //take the pokemon ID's and do battle by those

    public Pokemon pokemonBattle(int pokemonAId, int pokemonBId){
        Pokemon pokemonA = new Pokemon();





        return pokemonA;
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
     */