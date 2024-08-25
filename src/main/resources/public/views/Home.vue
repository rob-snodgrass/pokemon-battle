<template>
    <div class="home">
      <h1>Welcome to the Pokémon Arena!</h1>
      <p>Select two Pokémon from the list to start a battle.</p>
      <router-link to="/arena">
        <button>Go to Arena</button>
      </router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Home',
  };
  </script>
  
  <style scoped>
  .home {
    text-align: center;
    padding: 20px;
  }
  
  .home h1 {
    font-size: 2rem;
    margin-bottom: 20px;
  }
  
  .home button {
    padding: 10px 20px;
    font-size: 1rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .home button:hover {
    background-color: #0056b3;
  }
  </style>
  