document.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.getElementById("search-input");
  const searchButton = document.getElementById("search-button");

  const creatureName = document.getElementById("creature-name");
  const creatureId = document.getElementById("creature-id");
  const weight = document.getElementById("weight");
  const height = document.getElementById("height");
  const types = document.getElementById("types");

  const ability = document.getElementById("ability");
  const abilityDescription = document.getElementById("ability-description");
  const hp = document.getElementById("hp");
  const attack = document.getElementById("attack");
  const defense = document.getElementById("defense");
  const specialAttack = document.getElementById("special-attack");
  const specialDefense = document.getElementById("special-defense");
  const speed = document.getElementById("speed");
const creatureImage = document.getElementById("creature-image");

function clearUI() {
    creatureName.textContent = "";
    creatureId.textContent = "";
    weight.textContent = "";
    height.textContent = "";
    types.innerHTML = "";
    hp.textContent = "";
    attack.textContent = "";
    defense.textContent = "";
    specialAttack.textContent = "";
    specialDefense.textContent = "";
    speed.textContent = "";
    ability.textContent = "";
    abilityDescription.textContent = "";
    creatureImage.style.display = "none";
}

function displayCreature(data) {
    const typeColors = {
        normal: "#A8A77A",
        fire: "#EE8130",
        water: "#6390F0",
        electric: "#F7D02C",
        grass: "#7AC74C",
        ice: "#96D9D6",
        fighting: "#C22E28",
        poison: "#A33EA1",
        ground: "#E2BF65",
        flying: "#A98FF3",
        psychic: "#F95587",
        bug: "#A6B91A",
        rock: "#B6A136",
        ghost: "#735797",
        dragon: "#6F35FC",
        dark: "#705746",
        steel: "#B7B7CE",
        fairy: "#D685AD"
    };

    creatureName.textContent = data.name;
    creatureId.textContent = `#${data.id}`;
    weight.textContent = data.weight;
    height.textContent = data.height;

    types.innerHTML = "";
    data.types.forEach(t => {
        const span = document.createElement("span");
        span.textContent = t;
        span.style.backgroundColor = typeColors[t.toLowerCase()] || "#777"; 
        types.appendChild(span);
    });

    hp.textContent = `HP: ${data.stats.hp}`;
    attack.textContent = `Attack: ${data.stats.attack}`;
    defense.textContent = `Defense: ${data.stats.defense}`;
    specialAttack.textContent = `Special Attack: ${data.stats.specialAttack}`;
    specialDefense.textContent = `Special Defense: ${data.stats.specialDefense}`;
    speed.textContent = `Speed: ${data.stats.speed}`;

    ability.textContent = data.special?.name ? `Ability: ${data.special.name}` : "";
    abilityDescription.textContent = data.special?.description || "";

    if (data.image) {
        creatureImage.src = data.image;
        creatureImage.style.display = "block";
    } else {
        creatureImage.style.display = "none";
    }
}




  async function fetchAbilityDescription(url) {
    try {
      const res = await fetch(url);
      if (!res.ok) throw new Error();
      const data = await res.json();
      const englishEntry = data.effect_entries.find(
        entry => entry.language.name === "en"
      );
      return englishEntry ? englishEntry.effect : "";
    } catch {
      return "";
    }
  }

  searchButton.addEventListener("click", async () => {
    const rawQuery = searchInput.value.trim().toLowerCase();
    if (!rawQuery) return;

    try {
      const res = await fetch(`https://pokeapi.co/api/v2/pokemon/${rawQuery}`);
      if (!res.ok) throw new Error();

      const apiData = await res.json();

      const mainAbility = apiData.abilities?.[0];
      let abilityData = null;

      if (mainAbility) {
        const description = await fetchAbilityDescription(mainAbility.ability.url);
        abilityData = {
          name: mainAbility.ability.name.toUpperCase(),
          description
        };
      }

      const pokemonData = {
    name: apiData.name.toUpperCase(),
    id: apiData.id,
    weight: `Weight: ${apiData.weight}`,
    height: `Height: ${apiData.height}`,
    image: apiData.sprites?.front_default || "",
    types: apiData.types.map(t => t.type.name.toUpperCase()),
    special: abilityData,
    stats: {
        hp: apiData.stats[0]?.base_stat ?? "",
        attack: apiData.stats[1]?.base_stat ?? "",
        defense: apiData.stats[2]?.base_stat ?? "",
        specialAttack: apiData.stats[3]?.base_stat ?? "",
        specialDefense: apiData.stats[4]?.base_stat ?? "",
        speed: apiData.stats[5]?.base_stat ?? ""
    }
};


      displayCreature(pokemonData);
    } catch {
      alert("Pokémon not found");
      clearUI();
    }
  });
});
