const app = Vue.createApp({
    setup() {
        const winnerName = Vue.ref('');
        const winnerHitPoints = Vue.ref('');
        const selectedPokemon1 = Vue.ref('');
        const selectedPokemon2 = Vue.ref('');
        const pokedex = Vue.ref([]);
        const battleCompleted = Vue.ref(false);
        const inputPokemon1 = Vue.ref(''); 
        const inputPokemon2 = Vue.ref(''); 
        const errorMessage = Vue.ref('');

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

        // Sanitize the data in the frontend even thought the backend has some protections
        const validatePokemon = (pokemonName) => {
            return pokedex.value.includes(pokemonName);
        };

        const startBattle = async () => {
            // When using the inputBox we will prioritize it over the dropdown and then validate it
            const pokemonA = inputPokemon1.value.trim() !== '' ? inputPokemon1.value.trim() : selectedPokemon1.value;
            const pokemonB = inputPokemon2.value.trim() !== '' ? inputPokemon2.value.trim() : selectedPokemon2.value;

            console.log(`Pokemon A: ${pokemonA}`);
            console.log(`Pokemon B: ${pokemonB}`);

            // console.log(inputPokemon1.value);
            // console.log(inputPokemon2.value);
            // console.log(selectedPokemon1.value);
            // console.log(selectedPokemon2.value);

            // if (!validatePokemon(pokemonA)) {
            //     errorMessage.value = `Pokémon "${pokemonA}" does not exist in the pokedex.`;
            //     return;
            // }
            // if (!validatePokemon(pokemonB)) {
            //     errorMessage.value = `Pokémon "${pokemonB}" does not exist in the pokedex.`;
            //     return;
            // }

            // Reset any prior errors, hide battle text
            errorMessage.value = ''; 
            battleCompleted.value = false; 

            try {
                // Build the query with selected Pokémon names 
                //const query = `pokemonA=${selectedPokemon1.value}&pokemonB=${selectedPokemon2.value}`;
                const query = `pokemonA=${pokemonA}&pokemonB=${pokemonB}`;

                // Send a GET request to the /attack API with the two pokemon names and wait for the response
                const response = await fetch(`/attack?${query}`);
                const data = await response.json();

                // I commented out my initial validation of pokemon names to let the backend handle it
                if(data.hitPoints == "Undefined"){
                    errorMessage.value = `One or both Pokémon do not exist in the pokedex.`;
                    return;
                }
                
                // Display the name of the winner, sent as a map from the backend
                winnerName.value = data.winner;
                winnerHitPoints.value = data.hitPoints;
                battleCompleted.value = true;
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
            winnerHitPoints,
            selectedPokemon1,
            selectedPokemon2,
            inputPokemon1,
            inputPokemon2,
            pokedex,
            startBattle,
            battleCompleted,
            errorMessage,
        };
    }
});

app.mount('#app');
