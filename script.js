
async function fetchData(){
    try {

        const response  = await fetch("https://pokeapi.co/api/v2/pokemon/raichu")

        if(!response.ok){
            throw new Error('Could not fetch data');
        }
        const data = await response.json();
        const pokemonSprite = data.sprites.front_default;
        
        const imgElement = document.getElementById("gambarEvent");
        const pokemonName = data.name;

        

        imgElement.src = pokemonSprite;
        imgElement.style.display = "block";
        document.getElementById('judulEvent').innerText = pokemonName;

    } catch (error) {
        console.error(error);
    }
}