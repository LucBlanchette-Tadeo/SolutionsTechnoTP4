<script lang="ts">
  import { onMount } from 'svelte';
  import { listClasseBateau, createClasseBateau } from '$lib/api';

  let classes: any[] = [];
  let nom = '';
  let typeHandicap = '';
  let valeur = '';

  async function load() {
    classes = await listClasseBateau();
  }

  async function add() {
    await createClasseBateau({ nomClasse: nom, typeHandicap: typeHandicap, valeurHandicap: parseFloat(valeur || '0') });
    nom = ''; typeHandicap = ''; valeur = '';
    await load();
  }

  onMount(load);
</script>

<h1>Classes de bateaux</h1>
<form on:submit|preventDefault={add}>
  <input placeholder="Nom classe" bind:value={nom} />
  <input placeholder="Type handicap" bind:value={typeHandicap} />
  <input placeholder="Valeur handicap" bind:value={valeur} />
  <button type="submit">Créer</button>
</form>

<table>
  <tr><th>Nom</th><th>Type</th><th>Valeur</th></tr>
  {#each classes as c}
    <tr>
      <td>{c.nomClasse}</td>
      <td>{c.typeHandicap}</td>
      <td>{c.valeurHandicap}</td>
    </tr>
  {/each}
</table>
