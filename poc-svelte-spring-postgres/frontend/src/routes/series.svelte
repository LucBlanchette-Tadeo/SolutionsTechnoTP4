<script lang="ts">
  import { onMount } from 'svelte';
  import { 
    listSeries, 
    createSerie, 
    listClasseCourse,
    getSerieClasseCourseBySerieId,
    createSerieClasseCourse,
    deleteSerieClasseCourse
  } from '$lib/api';

  let series: any[] = [];
  let classesCourse: any[] = [];
  let message = '';
  let loading = false;
  
  // Formulaire création série
  let newSerieName = '';
  let newSerieNombreCourses = '0';
  let newSerieCoursesACompter = '0';
  
  let selectedSerieId: number | null = null;
  let seriesCourses: any[] = [];
  let availableClassesCourse: any[] = [];
  let selectedClasseCourseId: number | null = null;

  async function loadData() {
    try {
      series = await listSeries();
      classesCourse = await listClasseCourse();
    } catch (e) {
      message = `Erreur: ${e instanceof Error ? e.message : String(e)}`;
      setTimeout(() => message = '', 3000);
    }
  }

  async function createNewSerie() {
    if (!newSerieName) {
      message = 'Erreur: Veuillez entrer un nom de série';
      return;
    }
    loading = true;
    message = '';
    
    try {
      await createSerie({ 
        nomSerie: newSerieName, 
        nombreCourses: parseInt(newSerieNombreCourses || '0'), 
        nombreCoursesACompter: parseInt(newSerieCoursesACompter || '0') 
      });
      newSerieName = '';
      newSerieNombreCourses = '0';
      newSerieCoursesACompter = '0';
      message = 'Série créée avec succès!';
      setTimeout(() => message = '', 3000);
      await loadData();
    } catch (e) {
      message = `Erreur: ${e instanceof Error ? e.message : String(e)}`;
    } finally {
      loading = false;
    }
  }

  async function selectSerie(serieId: number) {
    selectedSerieId = serieId;
    selectedClasseCourseId = null;
    try {
      seriesCourses = await getSerieClasseCourseBySerieId(serieId);
      updateAvailableClasses();
    } catch (e) {
      message = `Erreur: ${e instanceof Error ? e.message : String(e)}`;
      setTimeout(() => message = '', 3000);
    }
  }

  function updateAvailableClasses() {
    const linkedIds = seriesCourses.map(sc => sc.classeCourse.id);
    availableClassesCourse = classesCourse.filter(c => !linkedIds.includes(c.id));
  }

  async function addCourseToSerie() {
    if (!selectedSerieId || !selectedClasseCourseId) {
      message = 'Erreur: Veuillez sélectionner une course';
      return;
    }
    loading = true;
    message = '';
    
    try {
      await createSerieClasseCourse({
        serie: { id: selectedSerieId },
        classeCourse: { id: selectedClasseCourseId },
        ordreCourse: seriesCourses.length + 1
      });
      selectedClasseCourseId = null;
      message = 'Course ajoutée à la série!';
      setTimeout(() => message = '', 3000);
      await selectSerie(selectedSerieId);
    } catch (e) {
      message = `Erreur: ${e instanceof Error ? e.message : String(e)}`;
    } finally {
      loading = false;
    }
  }

  async function removeCourseFromSerie(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette course de la série?')) return;
    loading = true;
    message = '';
    
    try {
      await deleteSerieClasseCourse(id);
      message = 'Course supprimée de la série!';
      setTimeout(() => message = '', 3000);
      await selectSerie(selectedSerieId!);
    } catch (e) {
      message = `Erreur: ${e instanceof Error ? e.message : String(e)}`;
    } finally {
      loading = false;
    }
  }

  onMount(loadData);
</script>

<h1>📊 Gestion des Séries</h1>

{#if message}
  <div class="alert alert-{message.includes('Erreur') ? 'error' : 'success'}">
    {message}
  </div>
{/if}

<div class="container">
  <!-- PANNEAU GAUCHE: Créer et lister les séries -->
  <div class="left-section">
    <div class="card">
      <h2>➕ Créer une nouvelle série</h2>
      <form on:submit|preventDefault={createNewSerie}>
        <div class="form-group">
          <label>Nom de la série</label>
          <input 
            type="text" 
            placeholder="Ex: Championnat d'été 2024" 
            bind:value={newSerieName}
            disabled={loading}
            required 
          />
        </div>

        <div class="form-row">
          <div class="form-group">
            <label>Nombre de courses</label>
            <input 
              type="number" 
              min="0" 
              bind:value={newSerieNombreCourses}
              disabled={loading}
            />
          </div>
          <div class="form-group">
            <label>Nombre à compter</label>
            <input 
              type="number" 
              min="0" 
              bind:value={newSerieCoursesACompter}
              disabled={loading}
            />
          </div>
        </div>

        <button type="submit" class="btn btn-primary" disabled={loading || !newSerieName}>
          {loading ? '⏳ Création...' : '✅ Créer la série'}
        </button>
      </form>
    </div>

    <div class="card">
      <h2>📋 Séries existantes ({series.length})</h2>
      {#if series.length > 0}
        <div class="series-list">
          {#each series as s (s.id)}
            <div 
              class="serie-item {selectedSerieId === s.id ? 'active' : ''}"
              on:click={() => selectSerie(s.id)}
            >
              <div class="serie-header">{s.nomSerie}</div>
            </div>
          {/each}
        </div>
      {:else}
        <p class="empty-state">Aucune série créée</p>
      {/if}
    </div>
  </div>

  <!-- PANNEAU DROIT: Gérer les courses de la série -->
  <div class="right-section">
    {#if selectedSerieId !== null}
      <div class="card">
        <h2>🏁 Courses de la série</h2>

        <div class="add-course-section">
          <h3>➕ Ajouter une course</h3>
          <div class="form-row">
            <select 
              bind:value={selectedClasseCourseId}
              disabled={loading || availableClassesCourse.length === 0}
            >
              <option value={null}>-- Sélectionner une course --</option>
              {#each availableClassesCourse as c (c.id)}
                <option value={c.id}>{c.nomClasseCourse}</option>
              {/each}
            </select>
            <button 
              on:click={addCourseToSerie}
              class="btn btn-secondary"
              disabled={!selectedClasseCourseId || loading}
            >
              ➕ Ajouter
            </button>
          </div>
        </div>

        {#if seriesCourses.length > 0}
          <div class="courses-table">
            <table>
              <thead>
                <tr>
                  <th>Ordre</th>
                  <th>Course</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {#each seriesCourses as sc, idx (sc.id)}
                  <tr>
                    <td class="center">{idx + 1}</td>
                    <td><strong>{sc.classeCourse?.nomClasseCourse}</strong></td>
                    <td class="actions">
                      <button 
                        class="btn btn-small btn-danger"
                        on:click={() => removeCourseFromSerie(sc.id)}
                        disabled={loading}
                      >
                        🗑️ Retirer
                      </button>
                    </td>
                  </tr>
                {/each}
              </tbody>
            </table>
          </div>
        {:else}
          <p class="empty-state">Aucune course ajoutée à cette série</p>
        {/if}
      </div>
    {:else}
      <div class="card empty-state-large">
        <p>👈 Sélectionnez une série pour gérer ses courses</p>
      </div>
    {/if}
  </div>
</div>

<style>
  :global(body) {
    background-color: #f5f5f5;
  }

  h1 {
    text-align: center;
    color: #333;
    margin-bottom: 2rem;
    font-size: 2.5rem;
  }

  h2 {
    color: #2c3e50;
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }

  h3 {
    color: #34495e;
    font-size: 1.1rem;
    margin: 0 0 0.5rem 0;
  }

  .alert {
    padding: 1rem;
    border-radius: 6px;
    margin-bottom: 1rem;
    max-width: 1400px;
    margin-left: auto;
    margin-right: auto;
    animation: slideIn 0.3s ease-out;
  }

  .alert-error {
    background-color: #fee;
    border-left: 4px solid #e74c3c;
    color: #c0392b;
  }

  .alert-success {
    background-color: #efe;
    border-left: 4px solid #27ae60;
    color: #229954;
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

  .container {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 2rem;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 1rem;
  }

  .left-section, .right-section {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
  }

  .card {
    background: white;
    border-radius: 8px;
    padding: 1.5rem;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease;
  }

  .card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }

  .form-group {
    margin-bottom: 1rem;
  }

  .form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 1rem;
    align-items: end;
  }

  label {
    display: block;
    font-weight: 600;
    margin-bottom: 0.5rem;
    color: #2c3e50;
    font-size: 0.95rem;
  }

  input, select {
    width: 100%;
    padding: 0.75rem;
    border: 2px solid #e0e0e0;
    border-radius: 6px;
    font-size: 1rem;
    transition: all 0.3s ease;
    font-family: inherit;
  }

  input:focus, select:focus {
    outline: none;
    border-color: #3498db;
    box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
  }

  input:disabled, select:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
    opacity: 0.6;
  }

  .btn {
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 6px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    text-align: center;
    display: inline-block;
  }

  .btn-primary {
    background-color: #3498db;
    color: white;
    width: 100%;
  }

  .btn-primary:hover:not(:disabled) {
    background-color: #2980b9;
    transform: translateY(-2px);
  }

  .btn-primary:disabled {
    background-color: #bdc3c7;
    cursor: not-allowed;
  }

  .btn-secondary {
    background-color: #95a5a6;
    color: white;
    padding: 0.7rem 1.2rem;
  }

  .btn-secondary:hover:not(:disabled) {
    background-color: #7f8c8d;
    transform: translateY(-2px);
  }

  .btn-secondary:disabled {
    background-color: #bdc3c7;
    cursor: not-allowed;
  }

  .btn-small {
    padding: 0.5rem 0.75rem;
    font-size: 0.85rem;
  }

  .btn-danger {
    background-color: #e74c3c;
    color: white;
  }

  .btn-danger:hover:not(:disabled) {
    background-color: #c0392b;
  }

  .btn-danger:disabled {
    opacity: 0.6;
  }

  .series-list {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }

  .serie-item {
    padding: 1rem;
    background-color: #f9f9f9;
    border: 2px solid transparent;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .serie-item:hover {
    background-color: #f0f0f0;
    border-color: #bdc3c7;
  }

  .serie-item.active {
    background-color: #d6eaf8;
    border-color: #3498db;
    box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
  }

  .serie-header {
    font-weight: 600;
    color: #2c3e50;
  }

  .add-course-section {
    background-color: #f9f9f9;
    padding: 1rem;
    border-radius: 6px;
    margin-bottom: 1.5rem;
  }

  .add-course-section h3 {
    margin-top: 0;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 1rem;
  }

  thead {
    background-color: #ecf0f1;
  }

  th {
    padding: 1rem;
    text-align: left;
    font-weight: 700;
    color: #2c3e50;
    border-bottom: 2px solid #bdc3c7;
  }

  td {
    padding: 0.75rem 1rem;
    border-bottom: 1px solid #ecf0f1;
  }

  tbody tr:hover {
    background-color: #f9f9f9;
  }

  .center {
    text-align: center;
  }

  .actions {
    display: flex;
    gap: 0.5rem;
  }

  .empty-state {
    text-align: center;
    color: #7f8c8d;
    padding: 2rem;
    font-size: 1.1rem;
  }

  .empty-state-large {
    min-height: 300px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  @media (max-width: 1024px) {
    .container {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 768px) {
    h1 {
      font-size: 1.8rem;
    }

    .form-row {
      grid-template-columns: 1fr;
      align-items: stretch;
    }

    .actions {
      flex-direction: column;
    }

    .actions .btn {
      width: 100%;
    }
  }
</style>
