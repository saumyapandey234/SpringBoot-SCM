console.log("Loaded");

let currentTheme = getTheme();
// initial
applyTheme(currentTheme);

// Set up the listener once
const change = document.querySelector('#theme_change_button');
change.addEventListener("click", () => {
  const oldTheme = currentTheme;
  currentTheme = currentTheme === "dark" ? "light" : "dark";
  setTheme(currentTheme);
  // Remove old theme, add new theme
  document.querySelector('html').classList.remove(oldTheme);
  document.querySelector('html').classList.add(currentTheme);
  // Update button text
  updateButtonText();
});

function applyTheme(theme) {
  document.querySelector('html').classList.add(theme);
  updateButtonText();
}

function updateButtonText() {
  const change = document.querySelector('#theme_change_button');
  change.querySelector('span').textContent = currentTheme === 'light' ? 'Dark' : 'Light';
}

// set theme to local storage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// get the theme from local storage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}


// function to request server to create order 

const paymentStart=()=>{
  console.log("Payment started");
  let amount = document.getElementById('paymentField').value;
  console.log(amount);
  if(amount=='' || amount==null){
    alert("Amount is required");
    return;
  }
  //Create the data object to send
  const data = {
    amount: amount,
    info: 'order_request'
  };

  // we will use ajax or simple js to send request to server to create order
  // we will use jquery for ajax call
  // Send POST request using Fetch
  fetch("/payment/create_order", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify(data),
})
.then((response) => {
  if (!response.ok) {
    throw new Error("Network response was not ok");
  }
  return response.json(); // <-- parse JSON
})
.then((responseData) => {
  console.log("Order Response:", responseData);

  // create Razorpay options using real order_id
  let options = {
    "key": "rzp_test_RPpCog0UHTqwiq", // your Razorpay key
    "amount": responseData.amount,
    "currency": "INR",
    "name": "SCM-2.0",
    "description": "Donation",
    "image": "https://www.pexels.com/photo/close-up-of-a-red-siamese-fighting-fish-325044/",
    "order_id": responseData.id, // <--- dynamic order_id from backend
    "handler": function (response) {
      alert("Payment Successful!");
      console.log(response);
    },
    "prefill": {
      "name": "",
      "email": "",
      "contact": ""
    },
    "notes": {
      "address": "Razorpay Corporate Office"
    },
    "theme": {
      "color": "#3399cc"
    }
  };

  const razorpay = new Razorpay(options);
  razorpay.open();
})
.catch((error) => {
  console.error("Error:", error);
  alert("Something went wrong!!");
});
}
