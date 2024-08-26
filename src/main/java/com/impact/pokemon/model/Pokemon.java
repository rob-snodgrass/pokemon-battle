package com.impact.pokemon.model;

public class Pokemon {

    //I'm choosing int over long for memory purposes
    private int id;
    private String name;
    private String type;
    private int total;
    private double hitPoints;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int generation;
    private String legendary;

    public Pokemon(){}

    public Pokemon(int id, String name, String type, int total, double hitPoints,
                   int attack, int defense, int specialAttack, int specialDefense,
                   int speed, int generation, String legendary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.total = total;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.generation = generation;
        this.legendary = legendary;
    }

    // I was encountering a problem with the pokemonBattle method using the same in-memory pokemon from my list, so I need the ability to create a cloned object to maintain encapsulation
    public Pokemon(Pokemon pokemon) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
        this.type = pokemon.getType();
        this.total = pokemon.getTotal();
        this.hitPoints = pokemon.getHitPoints();
        this.attack = pokemon.getAttack();
        this.defense = pokemon.getDefense();
        this.specialAttack = pokemon.getSpecialAttack();
        this.specialDefense = pokemon.getSpecialDefense();
        this.speed = pokemon.getSpeed();
        this.generation = pokemon.getGeneration();
        this.legendary = pokemon.getLegendary();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getLegendary() {
        return legendary;
    }

    public void setLegendary(String legendary) {
        this.legendary = legendary;
    }
}
