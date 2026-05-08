<script lang="ts">
  import { onMount } from 'svelte';
  import { listBateaux, createBateau, listClasseBateau } from '$lib/api';

  let bateaux: any[] = [];
  let classes: any[] = [];
  let error: string = '';
  let success: string = '';
  let loading = false;

  let nom = '';
  let numero = '';
  let barreur = '';
  let classeId = '';

  async function load() {
    try {
      bateaux = await listBateaux();
      classes = await listClasseBateau();
    } catch (e) {
      error = 'Erreur lors du chargement des données';
      console.error(e);
    }
  }

  async function add() {
    error = '';
    success = '';
    
    if (!nom.trim() || !numero.trim() || !barreur.trim() || !classeId) {
      error = 'Tous les champs sont obligatoires';
      return;
    }

    try {
      loading = true;
      await createBateau({ 
        nomBateau: nom, 
        numeroVoile: parseInt(numero), 
        nomBarreur: barreur, 
        classeBateau: { id: parseInt(classeId) } 
      });
      success = 'Bateau créé avec succès !';
      nom = '';
      numero = '';
      barreur = '';
      classeId = '';
      await load();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e) {
      error = `Erreur lors de la création : ${e.message}`;
      console.error(e);
    } finally {
      loading = false;
    }
  }

  onMount(load);
</script>

<div class="container">
  <h1>🚤 Gestion des Bateaux</h1>
  
  {#if error}
    <div class="alert alert-error">{error}</div>
  {/if}
  
  {#if success}
    <div class="alert alert-success">{success}</div>
  {/if}

  <div class="form-section">
    <h2>Ajouter un nouveau bateau</h2>
    <form on:submit|preventDefault={add}>
      <div class="form-group">
        <label for="nom">Nom du bateau</label>
        <input 
          id="nom"
          type="text"
          placeholder="ex: Mon Bateau" 
          bind:value={nom} 
          disabled={loading}
        />
      </div>

      <div class="form-group">
        <label for="numero">Numéro de voile</label>
        <input 
          id="numero"
          type="number"
          placeholder="ex: 123" 
          bind:value={numero}
          disabled={loading}
        />
      </div>

      <div class="form-group">
        <label for="barreur">Nom du barreur</label>
        <input 
          id="barreur"
          type="text"
          placeholder="ex: Jean Dupont" 
          bind:value={barreur}
          disabled={loading}
        />
      </div>

      <div class="form-group">
        <label for="classe">Classe du bateau</label>
        <select 
          id="classe"
          bind:value={classeId}
          disabled={loading}
        >
          <option value="">-- Choisir une classe --</option>
          {#each classes as c (c.id)}
            <option value={c.id}>{c.nomClasse}</option>
          {/each}
        </select>
      </div>

      <button 
        type="submit" 
        disabled={loading}
        class="btn btn-primary"
      >
        {loading ? 'Création en cours...' : 'Créer le bateau'}
      </button>
    </form>
  </div>

  <div class="table-section">
    <h2>Liste des bateaux ({bateaux.length})</h2>
    {#if bateaux.length > 0}
      <table>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Numéro de voile</th>
            <th>Barreur</th>
            <th>Classe</th>
          </tr>
        </thead>
        <tbody>
          {#each bateaux as b (b.id)}
            <tr>
              <td>{b.nomBateau || '-'}</td>
              <td>{b.numeroVoile}</td>
              <td>{b.nomBarreur || '-'}</td>
              <td>
                <span class="classe-badge">{b.classeBateau?.nomClasse}</span>
              </td>
            </tr>
          {/each}
        </tbody>
      </table>
    {:else}
      <p class="empty-state">Aucun bateau pour le moment. Ajoutez-en un ci-dessus !</p>
    {/if}
  </div>
</div>

<style>
  .container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 20px;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  }

  h1 {
    color: #0066cc;
    margin-bottom: 30px;
    text-align: center;
    font-size: 2.5em;
  }

  h2 {
    color: #333;
    font-size: 1.5em;
    margin-bottom: 20px;
  }

  .alert {
    padding: 15px 20px;
    border-radius: 8px;
    margin-bottom: 20px;
    animation: slideIn 0.3s ease-in;
  }

  @keyframes slideIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .alert-error {
    background-color: #fee;
    border: 1px solid #fcc;
    color: #c00;
  }

  .alert-success {
    background-color: #efe;
    border: 1px solid #cfc;
    color: #060;
  }

  .form-section {
    background: #f9f9f9;
    padding: 30px;
    border-radius: 12px;
    margin-bottom: 40px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .form-group {
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
  }

  label {
    font-weight: 600;
    margin-bottom: 8px;
    color: #333;
  }

  input, select {
    padding: 12px 15px;
    border: 2px solid #ddd;
    border-radius: 6px;
    font-size: 1em;
    transition: border-color 0.3s, box-shadow 0.3s;
  }

  input:focus, select:focus {
    outline: none;
    border-color: #0066cc;
    box-shadow: 0 0 0 3px rgba(0, 102, 204, 0.1);
  }

  input:disabled, select:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
    opacity: 0.6;
  }

  .btn {
    padding: 12px 30px;
    border: none;
    border-radius: 6px;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s;
  }

  .btn-primary {
    background-color: #0066cc;
    color: white;
  }

  .btn-primary:hover:not(:disabled) {
    background-color: #0052a3;
    box-shadow: 0 4px 12px rgba(0, 102, 204, 0.3);
  }

  .btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }

  .table-section {
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }

  thead {
    background-color: #f0f0f0;
  }

  th {
    text-align: left;
    padding: 15px;
    font-weight: 600;
    color: #333;
    border-bottom: 2px solid #ddd;
  }

  td {
    padding: 15px;
    border-bottom: 1px solid #eee;
  }

  tbody tr:hover {
    background-color: #f9f9f9;
  }

  .classe-badge {
    display: inline-block;
    padding: 6px 12px;
    background-color: #e6f0ff;
    color: #0066cc;
    border-radius: 20px;
    font-size: 0.9em;
    font-weight: 600;
  }

  .empty-state {
    text-align: center;
    padding: 40px 20px;
    color: #999;
    font-size: 1.1em;
  }
</style>
</table>
