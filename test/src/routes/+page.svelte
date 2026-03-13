<script>
  let message = "";

  function envoyer() {
    console.log("Message envoyé :", message);
    alert("Message envoyé : " + message);
  }
</script>

<main>
  <h1>Formulaire simple</h1>

  <input
    type="text"
    placeholder="Écris un message..."
    bind:value={message}
  />

  <button on:click={envoyer}>
    Envoyer
  </button>
</main>
