const app = Vue.createApp({
    setup() {
        const winnerName = Vue.ref('');
        const selectedPokemon1 = Vue.ref('');
        const selectedPokemon2 = Vue.ref('');
        const pokedex = Vue.ref([]);

        // Fetching Pokemon list from /test for the dropdowns, need to change this endpoint
        const fetchPokedex = async () => {
            try {
                const response = await fetch("/test");
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

        // Need to actually create the ability to send the two pokemon values to the backend, but it won't run without this for the click method
        const startBattle = () => {
            console.log("Battle has begun");
        }

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
