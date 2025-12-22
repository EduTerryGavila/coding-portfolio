const textInput = document.getElementById('text-input');
const checkButton = document.getElementById('check-btn');
const result = document.getElementById('result');
let isError = false;

function cleanWord(str) {
  const regex = /[^a-z0-9 ]/gi;
  return str.replace(regex, '');
}

function isPalindrome(str){
  
  const clean = cleanWord(str);
  const lowerStr = clean.toLowerCase();
  const array = lowerStr.split("");

  for (let i = array.length - 1; i >= 0; i--) {
    if (array[i] === " ") {
     array.splice(i, 1);
   }
  }

  for (let i = 0; i < array.length/2; i++){
    if (array[i] !== array[array.length - 1 - i]){
      result.innerText = str + " is not a palindrome!";
      return false;
    }
  }

  result.innerText = str + " is a palindrome!";
  return true;
}

checkButton.onclick = () => {
  if (textInput.value.trim() === '') {
    alert('Please input a value.');
    result.innerText = '';
    return;
  }
  isPalindrome(textInput.value);
};
