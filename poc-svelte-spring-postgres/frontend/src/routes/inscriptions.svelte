<script lang="ts">
  import { onMount } from 'svelte';
  import { 
    listSeries, 
    listInscriptionsBySerie, 
    getAvailableBateaux, 
    addBateauToSerie, 
    removeBateauFromSerie 
  } from '$lib/api';

  let series: any[] = [];
  let inscriptions: any[] = [];
  let availableBateaux: any[] = [];
  
  let selectedSerieId: number | null = null;
  let selectedBateauId: number | null = null;
  let loading = false;
  let message = '';

  async function loadSeries() {
    series = await listSeries();
  }

  async function selectSerie(serieId: number) {
    selectedSerieId = serieId;
    selectedBateauId = null;
    loading = true;
    message = '';
    
    try {
      inscriptions = await listInscriptionsBySerie(serieId);
      availableBateaux = await getAvailableBateaux(serieId);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  async function addBateau() {
    if (!selectedSerieId || !selectedBateauId) return;
    
    loading = true;
    message = '';
    
    try {
      await addBateauToSerie(selectedBateauId, selectedSerieId);
      selectedBateauId = null;
      await selectSerie(selectedSerieId);
      message = 'Bateau ajouté avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  async function removeBateau(inscriptionId: number) {
    if (!selectedSerieId) return;
    
    loading = true;
    message = '';
    
    try {
      await removeBateauFromSerie(inscriptionId);
      await selectSerie(selectedSerieId);
      message = 'Bateau retiré avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  onMount(loadSeries);
</script>

<div class="container">
  <h1>Gestion des Inscriptions aux Séries</h1>
  
  {#if message}
    <div class="message" class:success={message.includes('succès')} class:error={message.includes('Erreur')}>
      {message}
    </div>
  {/if}

  <div class="series-selector">
    <h2>Sélectionnez une série</h2>
    <div class="series-grid">
      {#each series as s (s.id)}
        <button 
          class="serie-btn" 
          class:active={selectedSerieId === s.id}
          on:click={() => selectSerie(s.id)}
        >
          {s.nomSerie}
        </button>
      {/each}
    </div>
  </div>

  {#if selectedSerieId}
    <div class="content">
      <div class="add-section">
        <h3>Ajouter un bateau</h3>
        <div class="add-form">
          <select bind:value={selectedBateauId}>
            <option value={null}>Sélectionner un bateau</option>
            {#each availableBateaux as b (b.id)}
              <option value={b.id}>
                {b.nomBateau} ({b.numeroVoile}) - {b.nomBarreur}
              </option>
            {/each}
          </select>
          <button on:click={addBateau} disabled={!selectedBateauId || loading}>
            {loading ? 'Ajout...' : 'Ajouter'}
          </button>
        </div>
      </div>

      <div class="inscriptions-section">
        <h3>Bateaux inscrits ({inscriptions.length})</h3>
        {#if inscriptions.length === 0}
          <p class="no-data">Aucun bateau inscrit pour le moment</p>
        {:else}
          <div class="table-responsive">
            <table>
              <thead>
                <tr>
                  <th>Nom du bateau</th>
                  <th>Numéro de voile</th>
                  <th>Barreur</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {#each inscriptions as insc (insc.id)}
                  <tr>
                    <td>{insc.bateau.nomBateau}</td>
                    <td>{insc.bateau.numeroVoile}</td>
                    <td>{insc.bateau.nomBarreur}</td>
                    <td>
                      <button 
                        class="remove-btn" 
                        on:click={() => removeBateau(insc.id)}
                        disabled={loading}
                      >
                        Retirer
                      </button>
                    </td>
                  </tr>
                {/each}
              </tbody>
            </table>
          </div>
        {/if}
      </div>
    </div>
  {/if}
</div>

<h2>Inscrire à une série</h2>
<select bind:value={serieId}>
  <option value="">Choisir série</option>
  {#each series as s}
    <option value={s.id}>{s.nomSerie}</option>
  {/each}
</select>
<select bind:value={bateauId}>
  <option value="">Choisir bateau</option>
  {#each bateaux as b}
    <option value={b.id}>{b.nomBateau}</option>
  {/each}
</select>
<button on:click={() => addSerie(serieId, bateauId)}>Inscrire à la série</button>

<table>
  <tr><th>Course</th><th>Bateau</th><th>Résultat</th></tr>
  {#each inscriptions as i}
    <tr>
      <td>{i.course?.nomCourse}</td>
      <td>{i.bateau?.nomBateau}</td>
      <td>{i.resultat}</td>
    </tr>
  {/each}
</table>

<style>
  .container {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }

  h1 {
    color: #333;
    margin-bottom: 30px;
    text-align: center;
  }

  .message {
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 4px;
    font-weight: 500;
  }

  .message.success {
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
  }

  .message.error {
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
  }

  .series-selector {
    margin-bottom: 30px;
  }

  .series-selector h2 {
    font-size: 18px;
    margin-bottom: 15px;
    color: #555;
  }

  .series-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    gap: 10px;
  }

  .serie-btn {
    padding: 12px 16px;
    background-color: #f0f0f0;
    border: 2px solid #ddd;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
  }

  .serie-btn:hover {
    background-color: #e0e0e0;
    border-color: #999;
  }

  .serie-btn.active {
    background-color: #007bff;
    color: white;
    border-color: #0056b3;
  }

  .content {
    background-color: #f9f9f9;
    padding: 25px;
    border-radius: 8px;
    border: 1px solid #e0e0e0;
  }

  .add-section {
    margin-bottom: 30px;
    padding-bottom: 25px;
    border-bottom: 2px solid #ddd;
  }

  .add-section h3 {
    font-size: 16px;
    margin-bottom: 15px;
    color: #333;
  }

  .add-form {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
  }

  select {
    flex: 1;
    min-width: 250px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
  }

  button {
    padding: 10px 20px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: background-color 0.3s ease;
  }

  button:hover:not(:disabled) {
    background-color: #218838;
  }

  button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  .remove-btn {
    background-color: #dc3545;
    padding: 6px 12px;
    font-size: 12px;
  }

  .remove-btn:hover:not(:disabled) {
    background-color: #c82333;
  }

  .inscriptions-section {
    margin-top: 20px;
  }

  .inscriptions-section h3 {
    font-size: 16px;
    margin-bottom: 15px;
    color: #333;
  }

  .no-data {
    text-align: center;
    color: #999;
    padding: 20px;
    font-style: italic;
  }

  .table-responsive {
    overflow-x: auto;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    background-color: white;
  }

  th {
    background-color: #f5f5f5;
    padding: 12px;
    text-align: left;
    font-weight: 600;
    color: #333;
    border-bottom: 2px solid #ddd;
  }

  td {
    padding: 12px;
    border-bottom: 1px solid #eee;
  }

  tr:hover {
    background-color: #fafafa;
  }

  @media (max-width: 768px) {
    .container {
      padding: 15px;
    }

    .series-grid {
      grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
    }

    .add-form {
      flex-direction: column;
    }

    select {
      min-width: unset;
    }

    table {
      font-size: 13px;
    }

    th, td {
      padding: 8px;
    }
  }
</style>
