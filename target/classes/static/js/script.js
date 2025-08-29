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
