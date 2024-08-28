package com.impact.pokemon.service;

import com.impact.pokemon.dao.PokemonData;
import com.impact.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PokemonArenaImpl implements PokemonArena {

    private List<Pokemon> pokemonList;
    private Map<String,Pokemon> pokemonMap;
    @Autowired
    private PokemonData pokemonData;

    public PokemonArenaImpl(PokemonData pokemonData){
        this.pokemonData = pokemonData;
    }

    // For testing
    public PokemonArenaImpl(){}

    @PostConstruct
    public void init(){
        this.pokemonMap = pokemonData.loadPokemon();
    }


    /**
     * Returns the list of all Pokemon for the front end to populate dropdown selectors from memory to avoid touching the file reader multiple times
     * @return List<Pokemon> read from the CSV
     * I wanted to have the list be sorted by ID so it would be a little more fun to group older gen pokemon together, but I couldn't find a way to do it easily
     * I found a stackoverflow saying I could use "Collections.sort(pokemonList, Comparator.comparingInt(ItemEvent::getID));" But I couldn't get it working
     */
    public List<Pokemon> retrievePokemonList(){
        pokemonList = new ArrayList<>(pokemonMap.values());
        return pokemonList;
    }


    /**
        I wanted to incorporate some kind of method here to return the map of winner to keep the controller stateless
     */
    public Map<String, Object> determineWinner(String pokemonA, String pokemonB){

        Pokemon winner = pokemonBattle(pokemonA,pokemonB);
        try {
            long hitPoints = Math.round(winner.getHitPoints());

            return Map.of(
                    "winner", winner.getName(),
                    "hitPoints", hitPoints);
        }catch (NullPointerException ex){
            System.out.println("Invalid Pokemon attempted to battle");
            // This would be where I would log it with whatever logging suite I was assigned
        }
        return Map.of(
                "winner", "Invalid Pokemon Selection",
                "hitPoints", "Undefined");
    }


    /**
     * Retrieves the pokemon object in memory from the name passed in
     * @param name String name of the pokemon
     * @return Pokemon Object matching the name. It cannot be null because the user can only select names that are in the CSV
     */
    public Pokemon retrievePokemonByName(String name) throws NullPointerException {
        // We need to make sure the cases match the map's cases
        String formattedName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Pokemon pokemon = pokemonMap.get(formattedName);
        if (pokemon == null) {
            throw new NullPointerException("Pokémon not found: " + name);
        }
        return pokemon;
    }

    /**
     * The battle logic between the two pokemon fighting taking in all battle circumstances
     * @param pokemonAName String name for the first pokemon
     * @param pokemonBName String name for the second pokemon
     * @return The pokemon Object for who has won
     */
    public Pokemon pokemonBattle(String pokemonAName, String pokemonBName){
        try {
            // Could surround with try/catch in case pokemon is not found, but we're verifying via frontend
            Pokemon pokemonA = new Pokemon(retrievePokemonByName(pokemonAName));
            Pokemon pokemonB = new Pokemon(retrievePokemonByName(pokemonBName));

            // Check speed for which one attacks first
            Pokemon firstAttacker = checkSpeed(pokemonA, pokemonB);
            Pokemon secondAttacker = (firstAttacker == pokemonA) ? pokemonB : pokemonA;

            // Add battle logic
            while (firstAttacker.getHitPoints() > 0 && secondAttacker.getHitPoints() > 0) {
                double damage = calculateDamage(firstAttacker, secondAttacker);
                secondAttacker.setHitPoints(secondAttacker.getHitPoints() - damage);

                //Switch their roles after each round
                Pokemon temp = firstAttacker;
                firstAttacker = secondAttacker;
                secondAttacker = temp;

            }


        // Return the pokemon who has health
        return firstAttacker.getHitPoints() > 0 ? firstAttacker : secondAttacker;
        } catch (NullPointerException ex){
            System.out.println("Invalid pokemon provided");
            // This is the other spot I would log whatever information I could, maybe a custom pokemonNotFound exception?
        }
        return null;
    }


    /**
     * Determines which pokemon will attack first by their speed, if they are the same it is a coin-flip
     * @param pokemonA Pokemon object of pokemon A
     * @param pokemonB Pokemon object of pokemon B
     * @return The pokemon who will be the first attacker in battle
     */
    private Pokemon checkSpeed(Pokemon pokemonA, Pokemon pokemonB){
        if (pokemonA.getSpeed() > pokemonB.getSpeed()) {
            return pokemonA;
        }
        else if (pokemonB.getSpeed() > pokemonA.getSpeed()) {
            return pokemonB;
        }
        else {
            // Took this from https://www.tutorialspoint.com/java-program-to-toss-a-coin
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

    /**
     * Calculates the damage for each round based on the formula '50 x (attack of attacking pokemon / defense of defending pokemon) * effectiveness modifier'
     * @param attacker The pokemon object conducting the attack
     * @param defender The pokemon object being attacked, requires their defense and type to determine effectiveness
     * @return A double of the amount of damage the attack will do from the attacker to defender
     */
    private double calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness = getEffectivenessModifier(attacker.getType(), defender.getType());
        return (50 * (attacker.getAttack() / (double) defender.getDefense()) * effectiveness);
    }

    /**
     * Calculates the effectiveness modifier based on what type of pokemon the attacker and defender are
     * @param attackerType String name of the type of pokemon the attacker is
     * @param defenderType String name of the type of pokemon the defender is
     * @return A double value of how effective the attacker will be against the defender
     * I tried to brainstorm another way of doing this and decided simpler was better
     */
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