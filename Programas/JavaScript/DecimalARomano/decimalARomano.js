const number = document.getElementById("number");
const convertBtn = document.getElementById("convert-btn");
const result = document.getElementById("output");

const decimalToRoman = (num) => {
  const romanMap = [
    { value: 1000, numeral: "M" },
    { value: 900, numeral: "CM" },
    { value: 500, numeral: "D" },
    { value: 400, numeral: "CD" },
    { value: 100, numeral: "C" },
    { value: 90, numeral: "XC" },
    { value: 50, numeral: "L" },
    { value: 40, numeral: "XL" },
    { value: 10, numeral: "X" },
    { value: 9, numeral: "IX" },
    { value: 5, numeral: "V" },
    { value: 4, numeral: "IV" },
    { value: 1, numeral: "I" }
  ];

  let res = "";

  for (const { value, numeral } of romanMap) {
    while (num >= value) {
      res += numeral;
      num -= value;
    }
  }

  return res;
};


const checkUserInput = () => {
  const inputInt = parseInt(number.value);

  if (!number.value || isNaN(inputInt)) {
    result.innerText = "Please enter a valid number";
    return;
  }

  if (inputInt < 1) {
    result.innerText = "Please enter a number greater than or equal to 1";
    return;
  }

  if (inputInt >= 4000) {
    result.innerText = "Please enter a number less than or equal to 3999";
    return;
  }

  result.textContent = decimalToRoman(inputInt);
  result.scrollLeft = 0;
  number.value = "";
};

convertBtn.addEventListener("click", checkUserInput);

number.addEventListener("keydown", (e) => {
  if (e.key === "Enter") {
    checkUserInput();
  }
});