<script lang="ts">
  import { onMount } from 'svelte';
  import { 
    listSeries, 
    createSerie, 
    listClasseCourse,
    getSerieClasseCourseBySerieId,
    createSerieClasseCourse,
    deleteSerieClasseCourse,
    getLeaderboardByCourse,
    listBateaux
  } from '$lib/api';

  let series: any[] = [];
  let classesCourse: any[] = [];
  let bateaux: any[] = [];
  let nom = '';
  let classeCourseId = '';
  let nombre = '0';
  let aCompter = '0';
  let error = '';
  let success = '';
  
  let selectedSerieId: number | null = null;
  let seriesCourses: any[] = [];
  let availableClassesCourse: any[] = [];
  let selectedClasseCourseId = '';
  
  let showLeaderboard = false;
  let selectedCourse: any = null;
  let leaderboard: any[] = [];
  let editingLeaderboardId: number | null = null;
  let editingPosition: number | null = null;
  let editingPoints: number | null = null;
  let editingStatut: string = '';

  async function load() {
    try {
      series = await listSeries();
      classesCourse = await listClasseCourse();
      bateaux = await listBateaux();
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  async function add() {
    if (!nom || !classeCourseId) {
      error = 'Veuillez remplir tous les champs';
      return;
    }
    try {
      await createSerie({ 
        nomSerie: nom, 
        classeCourse: { id: parseInt(classeCourseId) }, 
        nombreCourses: parseInt(nombre || '0'), 
        nombreCoursesACompter: parseInt(aCompter || '0') 
      });
      nom = '';
      classeCourseId = '';
      nombre = '0';
      aCompter = '0';
      success = 'Série créée avec succès!';
      setTimeout(() => success = '', 3000);
      await load();
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  async function selectSerie(serieId: number) {
    selectedSerieId = serieId;
    try {
      seriesCourses = await getSerieClasseCourseBySerieId(serieId);
      updateAvailableClasses();
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  function updateAvailableClasses() {
    const linkedIds = seriesCourses.map(sc => sc.classeCourse.id);
    availableClassesCourse = classesCourse.filter(c => !linkedIds.includes(c.id));
  }

  async function addCourseToSerie() {
    if (!selectedSerieId || !selectedClasseCourseId) {
      error = 'Veuillez sélectionner une série et une course';
      return;
    }
    try {
      await createSerieClasseCourse({
        serie: { id: selectedSerieId },
        classeCourse: { id: parseInt(selectedClasseCourseId) },
        ordreCourse: seriesCourses.length + 1
      });
      selectedClasseCourseId = '';
      success = 'Course ajoutée à la série!';
      setTimeout(() => success = '', 3000);
      await selectSerie(selectedSerieId);
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  async function removeCourseFromSerie(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette course de la série?')) return;
    try {
      await deleteSerieClasseCourse(id);
      success = 'Course supprimée de la série!';
      setTimeout(() => success = '', 3000);
      await selectSerie(selectedSerieId!);
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  async function showCourseLeaderboard(course: any) {
    try {
      selectedCourse = course;
      leaderboard = await getLeaderboardByCourse(course.classeCourse.id);
      showLeaderboard = true;
    } catch (e) {
      error = e.message || String(e);
      setTimeout(() => error = '', 3000);
    }
  }

  function closeLeaderboard() {
    showLeaderboard = false;
    selectedCourse = null;
    leaderboard = [];
    editingLeaderboardId = null;
  }

  function getBateauName(bateauId: number) {
    const b = bateaux.find(b => b.id === bateauId);
    return b ? `${b.nomBateau} (${b.numeroVoile})` : 'Inconnu';
  }

  onMount(load);
</script>

<h1>📊 Gestion des Séries</h1>

{#if error}
  <div class="alert alert-error">{error}</div>
{/if}
{#if success}
  <div class="alert alert-success">{success}</div>
{/if}

<div class="main-container">
  <!-- PANNEAU GAUCHE: Créer et lister les séries -->
  <div class="left-section">
    <div class="card">
      <h2>➕ Créer une nouvelle série</h2>
      <form on:submit|preventDefault={add}>
        <div class="form-group">
          <label>Nom de la série *</label>
          <input type="text" placeholder="Ex: Série d'été 2024" bind:value={nom} required />
        </div>
        
        <div class="form-group">
          <label>Classe de course *</label>
          <select bind:value={classeCourseId} required>
            <option value="">-- Sélectionner --</option>
            {#each classesCourse as c}
              <option value={c.id}>{c.nomClasseCourse}</option>
            {/each}
          </select>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label>Nombre de courses</label>
            <input type="number" min="0" bind:value={nombre} />
          </div>
          <div class="form-group">
            <label>Nombre à compter</label>
            <input type="number" min="0" bind:value={aCompter} />
          </div>
        </div>

        <button type="submit" class="btn btn-primary">Créer la série</button>
      </form>
    </div>

    <div class="card">
      <h2>📋 Séries existantes</h2>
      {#if series.length > 0}
        <div class="series-list">
          {#each series as s}
            <div 
              class="serie-item {selectedSerieId === s.id ? 'active' : ''}"
              on:click={() => selectSerie(s.id)}
            >
              <div class="serie-header">
                <strong>{s.nomSerie}</strong>
              </div>
              <div class="serie-info">
                <small>Classe: {s.classeCourse?.nomClasseCourse}</small>
              </div>
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
          <h3>Ajouter une course</h3>
          <div class="form-row">
            <select bind:value={selectedClasseCourseId} class="flex-1">
              <option value="">-- Sélectionner une course --</option>
              {#each availableClassesCourse as c}
                <option value={c.id}>{c.nomClasseCourse}</option>
              {/each}
            </select>
            <button 
              on:click={addCourseToSerie}
              class="btn btn-secondary"
              disabled={!selectedClasseCourseId}
            >
              Ajouter
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
                {#each seriesCourses as sc, idx}
                  <tr>
                    <td class="center">{idx + 1}</td>
                    <td>{sc.classeCourse?.nomClasseCourse}</td>
                    <td class="actions">
                      <button 
                        class="btn btn-small btn-info"
                        on:click={() => showCourseLeaderboard(sc)}
                      >
                        📊 Leaderboard
                      </button>
                      <button 
                        class="btn btn-small btn-danger"
                        on:click={() => removeCourseFromSerie(sc.id)}
                      >
                        🗑️
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

<!-- MODAL LEADERBOARD -->
{#if showLeaderboard && selectedCourse}
  <div class="modal-overlay" on:click={closeLeaderboard}>
    <div class="modal" on:click={e => e.stopPropagation()}>
      <div class="modal-header">
        <h2>🏆 Leaderboard - {selectedCourse.classeCourse?.nomClasseCourse}</h2>
        <button class="close-btn" on:click={closeLeaderboard}>✕</button>
      </div>

      <div class="modal-content">
        {#if leaderboard.length > 0}
          <div class="leaderboard-table">
            <table>
              <thead>
                <tr>
                  <th>Pos</th>
                  <th>Bateau</th>
                  <th>Barreur</th>
                  <th>Voile</th>
                  <th>Points</th>
                  <th>Statut</th>
                </tr>
              </thead>
              <tbody>
                {#each leaderboard as entry}
                  <tr class={entry.statut ? 'status-' + entry.statut : ''}>
                    <td class="position">{entry.position || '-'}</td>
                    <td>{entry.bateau?.nomBateau || 'N/A'}</td>
                    <td>{entry.bateau?.nomBarreur || 'N/A'}</td>
                    <td class="center">{entry.bateau?.numeroVoile || '-'}</td>
                    <td class="center"><strong>{entry.points}</strong></td>
                    <td>
                      <span class="badge {entry.statut ? 'badge-' + entry.statut : 'badge-ok'}">
                        {entry.statut || 'OK'}
                      </span>
                    </td>
                  </tr>
                {/each}
              </tbody>
            </table>
          </div>
        {:else}
          <p class="empty-state">📭 Aucun résultat pour cette course</p>
        {/if}
      </div>
    </div>
  </div>
{/if}

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
    margin: 1rem 0 0.5rem 0;
  }

  .alert {
    padding: 1rem;
    border-radius: 6px;
    margin-bottom: 1rem;
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

  .main-container {
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
    box-shadow: 0 4px 12px rgba(52, 152, 219, 0.4);
  }

  .btn-secondary {
    background-color: #95a5a6;
    color: white;
    padding: 0.7rem 1.2rem;
  }

  .btn-secondary:hover:not(:disabled) {
    background-color: #7f8c8d;
  }

  .btn-small {
    padding: 0.5rem 0.75rem;
    font-size: 0.85rem;
  }

  .btn-info {
    background-color: #2980b9;
    color: white;
  }

  .btn-info:hover {
    background-color: #21618c;
  }

  .btn-danger {
    background-color: #e74c3c;
    color: white;
  }

  .btn-danger:hover {
    background-color: #c0392b;
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
    margin-bottom: 0.5rem;
    color: #2c3e50;
  }

  .serie-info {
    color: #7f8c8d;
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

  .flex-1 {
    flex: 1;
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

  tbody tr {
    transition: background-color 0.2s ease;
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
    flex-wrap: wrap;
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

  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    animation: fadeIn 0.3s ease-out;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }

  .modal {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
    max-width: 700px;
    width: 90%;
    max-height: 85vh;
    overflow-y: auto;
    animation: slideUp 0.3s ease-out;
  }

  @keyframes slideUp {
    from {
      transform: translateY(30px);
      opacity: 0;
    }
    to {
      transform: translateY(0);
      opacity: 1;
    }
  }

  .modal-header {
    padding: 2rem;
    border-bottom: 2px solid #ecf0f1;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .modal-header h2 {
    margin: 0;
    flex: 1;
  }

  .close-btn {
    background-color: #ecf0f1;
    border: none;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
    font-size: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
  }

  .close-btn:hover {
    background-color: #e74c3c;
    color: white;
  }

  .modal-content {
    padding: 2rem;
  }

  .leaderboard-table table {
    margin: 0;
  }

  .leaderboard-table tbody tr:nth-child(odd) {
    background-color: #fafafa;
  }

  .position {
    font-weight: bold;
    color: #3498db;
    font-size: 1.1rem;
  }

  .badge {
    display: inline-block;
    padding: 0.4rem 0.8rem;
    border-radius: 20px;
    font-size: 0.85rem;
    font-weight: 600;
    white-space: nowrap;
  }

  .badge-ok {
    background-color: #d5f4e6;
    color: #27ae60;
  }

  .badge-DNS {
    background-color: #fadbd8;
    color: #c0392b;
  }

  .badge-ARF {
    background-color: #fdebd0;
    color: #e67e22;
  }

  .badge-OCS {
    background-color: #fadbd8;
    color: #c0392b;
  }

  tr.status-DNS,
  tr.status-ARF,
  tr.status-OCS {
    background-color: #fef5e7;
  }

  @media (max-width: 1024px) {
    .main-container {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 768px) {
    h1 {
      font-size: 1.8rem;
    }

    .form-row {
      grid-template-columns: 1fr;
    }

    .actions {
      flex-direction: column;
    }

    .actions .btn {
      width: 100%;
    }

    .modal {
      width: 95%;
      max-height: 90vh;
    }

    .modal-header {
      flex-direction: column;
      align-items: flex-start;
      gap: 1rem;
    }

    .close-btn {
      align-self: flex-end;
    }
  }
</style>
