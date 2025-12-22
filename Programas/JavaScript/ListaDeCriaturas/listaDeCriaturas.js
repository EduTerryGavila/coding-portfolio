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
  }

  function displayCreature(data) {
    creatureName.textContent = data.name;
    creatureId.textContent = `#${data.id}`;
    weight.textContent = data.weight;
    height.textContent = data.height;

    types.innerHTML = "";
    data.types.forEach(t => {
      const div = document.createElement("div");
      div.textContent = t;
      types.appendChild(div);
    });

    hp.textContent = data.stats.hp;
    attack.textContent = data.stats.attack;
    defense.textContent = data.stats.defense;
    specialAttack.textContent = data.stats.specialAttack;
    specialDefense.textContent = data.stats.specialDefense;
    speed.textContent = data.stats.speed;

    if (data.special) {
  ability.textContent = data.special.name;
  abilityDescription.textContent = data.special.description;
} else {
  ability.textContent = "";
  abilityDescription.textContent = "";
}
  }

  searchButton.addEventListener("click", () => {
    const rawQuery = searchInput.value.trim();
    if (!rawQuery) return;

    const key = rawQuery.toLowerCase();
    const url = `https://rpg-creature-api.freecodecamp.rocks/api/creature/${key}`;

    const fetchPromise = fetch(url);

    fetchPromise
      .then(res => {
        if (!res.ok) {
          throw new Error("Creature not found");
        }
        return res.json();
      })
      .then(apiData => {
        const apiCreature = {
        name: apiData.name ? apiData.name.toUpperCase() : (apiData.id ? String(apiData.id) : ""),
          id: apiData.id,
          weight: `Weight: ${apiData.weight}`,
          height: `Height: ${apiData.height}`,
          types: (apiData.types || []).map(t => {
  if (t.name) return t.name.toUpperCase();             
  if (t.type?.name) return t.type.name.toUpperCase(); 
  return String(t).toUpperCase();                
}),

special: apiData.special
      ? {
          name: apiData.special.name || "",
          description: apiData.special.description || ""
        }
      : null,
          stats: {
            hp: apiData.stats?.[0]?.base_stat ?? "",
            attack: apiData.stats?.[1]?.base_stat ?? "",
            defense: apiData.stats?.[2]?.base_stat ?? "",
            specialAttack: apiData.stats?.[3]?.base_stat ?? "",
            specialDefense: apiData.stats?.[4]?.base_stat ?? "",
            speed: apiData.stats?.[5]?.base_stat ?? ""
          }
        };
        displayCreature(apiCreature);
      })
      .catch(() => {
        alert("Creature not found");
        clearUI();
      });
  });
});
