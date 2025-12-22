const input = document.getElementById("user-input");
const checkBtn = document.getElementById("check-btn");
const clearBtn = document.getElementById("clear-btn");
const output = document.getElementById("results-div");

const isValid = (num) => {
  
  const empty = /^$/;

  if(empty.test(num)){
    alert("Please provide a phone number");
    return;
  }

  const regex = /^(?:1\s?)?(?:\(\d{3}\)|\d{3})(?:[ -]?)\d{3}(?:[ -]?)\d{4}$/;
  if(regex.test(num)){
    output.innerText = "Valid US number: " + num;
    return true;
  }
  output.innerText = "Invalid US number: " + num;
  return false;
}

checkBtn.addEventListener("click", () => {
  isValid(input.value.trim());
});

clearBtn.addEventListener("click", () => {
  input.value = "";
  output.innerText = "";
});