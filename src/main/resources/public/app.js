const app = Vue.createApp({
    setup() {
        const winnerName = Vue.ref('');
        const selectedPokemon1 = Vue.ref('');
        const selectedPokemon2 = Vue.ref('');
        const pokedex = Vue.ref([]);

        // Sourced from https://lukashermann.dev/writing/how-to-use-async-await-with-vuejs-components/
        const fetchPokedex = async () => {
            try {
                const response = await fetch("/pokemon");
                const data = await response.json();
                pokedex.value = data.map(pokemon => pokemon.name);
                
                if (pokedex.value.length > 0) {
                    selectedPokemon1.value = pokedex.value[0];
                    selectedPokemon2.value = pokedex.value[0];
                }
            } catch (error) {
                console.error('Error fetching pokedex:', error);
            }
        };

        const startBattle = async () => {
            try {
                // Build the query with selected Pokémon names 
                const query = `pokemonA=${selectedPokemon1.value}&pokemonB=${selectedPokemon2.value}`;

                // Send a GET request to the /attack API with the two pokemon names and wait for the response
                const response = await fetch(`/attack?${query}`);
                const data = await response.json();
                
                // Display the name of the winner, sent as a map from the backend
                winnerName.value = data.winner;
                console.log(`Winner: ${data.winner}, with ${data.hitPoints} hit points.`);
            } catch (error) {
                console.error('Error during battle:', error);
            }
        };

        // Learned I can use this to call a component once it's been mounted  
        // https://vuejs.org/api/composition-api-lifecycle
        
        Vue.onMounted(fetchPokedex);

        return {
            winnerName,
            selectedPokemon1,
            selectedPokemon2,
            pokedex,
            startBattle,
        };
    }
});

app.mount('#app');
