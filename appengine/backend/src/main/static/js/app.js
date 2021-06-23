console.log('App started');

document.addEventListener("DOMContentLoaded", async function(event) {
    console.log('Document loaded');

    try {
        const fruitResp = await fetch('/fruit');
        const fruits = await fruitResp.json();
        console.log(`Loaded ${fruits.length} fruits`);

        const fruitSelect = document.getElementById("fruit-select");
        fruits.forEach(fruit => {
            const item = document.createElement("sl-menu-item");
            item.value = fruit.name;
            item.textContent = `${fruit.name} (${fruit.calories} cal)`;
            fruitSelect.appendChild(item);
        });
    } catch(e) {
        console.log("Impossible to retrieve fruits");
        console.error(e);
    }
});