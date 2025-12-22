let price = 1.87;
let cid = [
  ['PENNY', 1.01],
  ['NICKEL', 2.05],
  ['DIME', 3.1],
  ['QUARTER', 4.25],
  ['ONE', 90],
  ['FIVE', 55],
  ['TEN', 20],
  ['TWENTY', 60],
  ['ONE HUNDRED', 100]
];

const input = document.getElementById("cash");
const div = document.getElementById("change-due");
const button = document.getElementById("purchase-btn");
const priceInput = document.getElementById("price");

priceInput.value = price;
priceInput.addEventListener("change", () => {
  price = parseFloat(priceInput.value) || 0;
});

const DENOMS = [
  ['ONE HUNDRED', 10000],
  ['TWENTY', 2000],
  ['TEN', 1000],
  ['FIVE', 500],
  ['ONE', 100],
  ['QUARTER', 25],
  ['DIME', 10],
  ['NICKEL', 5],
  ['PENNY', 1]
];

const toCents = (v) => Math.round(Number(v) * 100);

const fromCentsFormatted = (cents) => {
  const n = cents / 100;
  if (Number.isInteger(n)) return String(n);
  return (Math.round(cents) / 100).toString();
};
const formatMoney = (cents) => "$" + fromCentsFormatted(cents);

function calculateChange(priceNum, cashNum, cidArr) {
  const priceC = toCents(priceNum);
  const cashC = toCents(cashNum);
  const changeNeeded = cashC - priceC;

  if (changeNeeded < 0) return { status: "NOT_ENOUGH", change: [] };
  if (changeNeeded === 0) return { status: "EXACT", change: [] };

  const cidMap = {};
  let totalCid = 0;
  for (const [name, amt] of cidArr) {
    const cents = toCents(amt);
    cidMap[name] = cents;
    totalCid += cents;
  }

  if (totalCid === changeNeeded) {
    const closed = DENOMS.map(([name]) => [name, cidMap[name] || 0]).filter(([,amt]) => amt > 0);
    return { status: "CLOSED", change: closed };
  }

  let remaining = changeNeeded;
  const changeGiven = [];

  for (const [name, value] of DENOMS) {
    if (remaining <= 0) break;
    const available = cidMap[name] || 0;
    if (available <= 0) continue;

    const maxNeededUnits = Math.floor(remaining / value);
    const availableUnits = Math.floor(available / value);
    const unitsToGive = Math.min(maxNeededUnits, availableUnits);

    if (unitsToGive > 0) {
      const giveCents = unitsToGive * value;
      changeGiven.push([name, giveCents]);
      remaining -= giveCents;
    }
  }

  if (remaining > 0) {
    return { status: "INSUFFICIENT_FUNDS", change: [] };
  }

  return { status: "OPEN", change: changeGiven };
}

function renderResult(res) {
  if (res.status === "NOT_ENOUGH") return;
  if (res.status === "EXACT") {
    div.textContent = "No change due - customer paid with exact cash";
    return;
  }
  if (res.status === "INSUFFICIENT_FUNDS") {
    div.textContent = "Status: INSUFFICIENT_FUNDS";
    return;
  }
  if (res.status === "CLOSED") {
    if (!res.change.length) {
      div.textContent = "Status: CLOSED";
      return;
    }
    const parts = res.change.map(([name, cents]) => `${name}: ${formatMoney(cents)}`);
    div.textContent = "Status: CLOSED " + parts.join(" ");
    return;
  }
  if (res.status === "OPEN") {
    if (!res.change.length) {
      div.textContent = "Status: OPEN";
      return;
    }
    const parts = res.change.map(([name, cents]) => `${name}: ${formatMoney(cents)}`);
    div.textContent = "Status: OPEN " + parts.join(" ");
    return;
  }
}

button.addEventListener("click", () => {
  const cashVal = parseFloat(input.value);

  if (isNaN(cashVal)) {
    alert("Customer does not have enough money to purchase the item");
    return;
  }

  const cashC = toCents(cashVal);
  const priceC = toCents(price);

  if (cashC < priceC) {
    alert("Customer does not have enough money to purchase the item");
    return;
  }

  if (cashC === priceC) {
    div.textContent = "No change due - customer paid with exact cash";
    return;
  }

  const result = calculateChange(price, cashVal, cid);
  if (result.status === "NOT_ENOUGH") {
    alert("Customer does not have enough money to purchase the item");
    return;
  }
  renderResult(result);
});

document.getElementById("example-a").addEventListener("click", () => {
  price = 19.5;
  priceInput.value = price;
  input.value = 20;
  cid = [
    ["PENNY", 1.01], ["NICKEL", 2.05], ["DIME", 3.1], ["QUARTER", 4.25],
    ["ONE", 90], ["FIVE", 55], ["TEN", 20], ["TWENTY", 60], ["ONE HUNDRED", 100]
  ];
  div.textContent = "Ejemplo cargado — pulsa Comprar";
});

document.getElementById("example-b").addEventListener("click", () => {
  price = 3.26;
  priceInput.value = price;
  input.value = 100;
  cid = [
    ["PENNY", 1.01], ["NICKEL", 2.05], ["DIME", 3.1], ["QUARTER", 4.25],
    ["ONE", 90], ["FIVE", 55], ["TEN", 20], ["TWENTY", 60], ["ONE HUNDRED", 100]
  ];
  div.textContent = "Ejemplo cargado — pulsa Comprar";
});
