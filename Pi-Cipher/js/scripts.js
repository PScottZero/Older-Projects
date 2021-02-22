$(document).ready(function() {
    let plaintext = document.getElementById("plaintext");
    plaintext.addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            encrypt(plaintext.value);
        }
    });

    let ciphertext = document.getElementById("ciphertext");
    ciphertext.addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            decrypt(ciphertext.value);
        }
    });
});

function encrypt(plaintext) {
    let cipher = new PiCipher();
    cipher.encrypt(plaintext);
}

function decrypt(ciphertext) {
    let cipher = new PiCipher();
    cipher.decrypt(ciphertext);
}

function menu() {
    document.getElementById("menu_button").classList.toggle("change");

    let slide = document.getElementById("slide_menu");
    if (slide.style.left === "0em") {
        slide.style.left = "-21em";
    } else slide.style.left = "0em";
}