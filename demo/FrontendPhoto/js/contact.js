document
  .getElementById("contactForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const message = document.getElementById("message").value;

    // Div di errore
    const nameError = document.getElementById("nameError");
    const emailError = document.getElementById("emailError");
    const messageError = document.getElementById("messageError");

    let isValid = true;

    nameError.textContent = "";
    emailError.textContent = "";
    messageError.textContent = "";

    // Rimuovi la classe d-block dai div di errore
    nameError.classList.remove("d-block");
    emailError.classList.remove("d-block");
    messageError.classList.remove("d-block");

    // Valida name
    if (name.trim() === "") {
      nameError.textContent = "Il campo nome non può essere vuoto";
      nameError.classList.add("d-block");
      isValid = false;
    } else if (name.length < 3 || name.length > 100) {
      nameError.textContent =
        "Il campo nome non può essere minore di 3 e maggiore di 100 caratteri";
      nameError.classList.add("d-block");
      isValid = false;
    }
    // Valida mail
    if (!email.includes("@") || !email.includes(".")) {
      emailError.textContent = "Inserisci un'email valida. Es: ***@***.it";
      emailError.classList.add("d-block");
      isValid = false;
    } else if (!(email.endsWith(".com") || email.endsWith(".it"))) {
      emailError.textContent = "L'email deve terminare con .com o .it.";
      emailError.classList.add("d-block");
      isValid = false;
    }

    //Valida message
    if (message.trim() === "") {
      messageError.textContent = "Il campo 'Messaggio' è obbligatorio.";
      messageError.classList.add("d-block");
      isValid = false;
    }
    if (isValid) {
      axios
        .post("http://localhost:8080/api/v1/contacts", {
          name: name,
          email: email,
          message: message,
        })
        .then(function (response) {
          console.log("Messaggio inviato:", response);
          alert("Messaggio inviato con successo!");
        })
        .catch(function (error) {
          console.error("Errore nell'invio del messaggio:", error);
          alert("Errore nell'invio del messaggio.");
        });
    }
  });
