async function fetchData(){
    try {
        //nnti semua yg pokemon mo diganti jd api asli ta

        // Fetch random Pokémon data from the API
        const pokemonId = Math.floor(Math.random() * 898) + 1;  // There are 898 Pokémon as of now //se jujur nd tau mo bemana nnti ini tp utk sementara begini saja aokawoakow
        const response  = await fetch(`https://pokeapi.co/api/v2/pokemon/${pokemonId}`);

        if (!response.ok) {
            throw new Error('Could not fetch data');
        }

        
        const data = await response.json();
        const pokemonSprite = data.sprites.front_default; //pokemon sprite mo diganti jd gambar event
        const pokemonName = data.name; //pokemon name mo diganti jd event_name

        // Add a new card with the fetched data
        addCard(pokemonName, pokemonSprite);
    } catch (error) {
        console.error(error);
    }
}

function addCard(name, sprite) {
    const cardContainer = document.getElementById("card-container");

    // Create a new card element
    const card = document.createElement("div");
    card.className = "col-sm-4 mb-3";

    // Create the card structure
    card.innerHTML = `
        <div class="card">
            <img src="${sprite}" class="card-img-top" alt="${name}" style="width: 25%; display: block; margin: 10px auto;">
            <div class="card-body">
                <h5 class="card-title text-center">${name}</h5>
            </div>
        </div>
    `;

    // Append the card to the card container
    cardContainer.appendChild(card);
}
