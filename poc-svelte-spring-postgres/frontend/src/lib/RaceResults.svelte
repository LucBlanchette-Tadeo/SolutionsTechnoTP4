<script lang="ts">
  import { onMount } from 'svelte';
  import * as api from '$lib/api';

  let classeCourseId: number | null = null;
  let bateauId: number | null = null;
  let tempsBrutSecondes: number = 3600; // 1h par défaut
  let typeHandicap: string = 'PY';
  let races: any[] = [];
  let courses: any[] = [];
  let bateaux: any[] = [];
  let message: string = '';
  let error: string = '';
  let loading: boolean = false;

  onMount(async () => {
    await loadCourses();
    await loadBateaux();
  });

  async function loadCourses() {
    try {
      courses = await api.listClasseCourse();
    } catch (e) {
      console.error('Erreur chargement courses:', e);
    }
  }

  async function loadBateaux() {
    try {
      bateaux = await api.listBateaux();
    } catch (e) {
      console.error('Erreur chargement bateaux:', e);
    }
  }

  async function loadRaceResults() {
    if (!classeCourseId) return;
    try {
      races = await api.getRaceResults(classeCourseId);
    } catch (e) {
      console.error('Erreur chargement résultats:', e);
    }
  }

  async function submitResult() {
    if (!classeCourseId || !bateauId) {
      error = 'Veuillez sélectionner une course et un bateau';
      return;
    }

    loading = true;
    error = '';
    message = '';

    try {
      const result = await api.submitRaceResult(classeCourseId, bateauId, tempsBrutSecondes, typeHandicap);
      message = `Résultat enregistré avec succès! Temps corrigé: ${result.tempsCorrigenFormatted}`;
      await loadRaceResults();
      // Réinitialiser le formulaire
      bateauId = null;
      tempsBrutSecondes = 3600;
      typeHandicap = 'PY';
    } catch (e: any) {
      error = e.message || 'Erreur lors de l\'enregistrement';
    } finally {
      loading = false;
    }
  }

  async function deleteResult(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce résultat?')) return;
    try {
      await api.deleteRaceResult(id);
      message = 'Résultat supprimé';
      await loadRaceResults();
    } catch (e: any) {
      error = e.message || 'Erreur lors de la suppression';
    }
  }

  function formatTime(seconds: number): string {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = Math.floor(seconds % 60);
    return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
  }

  $: if (classeCourseId) loadRaceResults();
</script>

<div class="handicap-container">
  <h1>🏁 Résultats de Course avec Handicaps</h1>

  <div class="form-section">
    <h2>Enregistrer un résultat</h2>

    {#if error}
      <div class="alert alert-error">{error}</div>
    {/if}

    {#if message}
      <div class="alert alert-success">{message}</div>
    {/if}

    <div class="form-group">
      <label for="course">Course</label>
      <select id="course" bind:value={classeCourseId}>
        <option value={null}>-- Sélectionner une course --</option>
        {#each courses as course (course.id)}
          <option value={course.id}>{course.nomClasseCourse}</option>
        {/each}
      </select>
    </div>

    <div class="form-group">
      <label for="bateau">Bateau</label>
      <select id="bateau" bind:value={bateauId}>
        <option value={null}>-- Sélectionner un bateau --</option>
        {#each bateaux as bateau (bateau.id)}
          <option value={bateau.id}>{bateau.nomBateau} (#{bateau.numeroVoile})</option>
        {/each}
      </select>
    </div>

    <div class="form-group">
      <label for="temps">Temps brut (en secondes)</label>
      <div class="input-row">
        <input
          id="temps"
          type="number"
          bind:value={tempsBrutSecondes}
          min="1"
          step="1"
        />
        <span class="time-display">{formatTime(tempsBrutSecondes)}</span>
      </div>
    </div>

    <div class="form-group">
      <label for="handicap">Type de handicap</label>
      <div class="handicap-options">
        <label>
          <input type="radio" bind:group={typeHandicap} value="PY" />
          <strong>PY</strong> (Portsmouth Yardstick)
          <div class="formula">Temps Corrigé = (Temps Brut × 1000) / PY</div>
        </label>
        <label>
          <input type="radio" bind:group={typeHandicap} value="TMF" />
          <strong>TMF</strong> (Time Multiplying Factor)
          <div class="formula">Temps Corrigé = Temps Brut × TMF</div>
        </label>
      </div>
    </div>

    <button on:click={submitResult} disabled={loading} class="btn-primary">
      {loading ? '⏳ Enregistrement...' : '✓ Enregistrer le résultat'}
    </button>
  </div>

  {#if classeCourseId && races.length > 0}
    <div class="results-section">
      <h2>Résultats classés</h2>
      <table class="results-table">
        <thead>
          <tr>
            <th>Position</th>
            <th>Bateau</th>
            <th>Temps brut</th>
            <th>Handicap</th>
            <th>Temps corrigé</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {#each races as race, idx (race.raceResultId)}
            <tr class="result-row">
              <td class="position">{race.positionCorrigee}</td>
              <td class="bateau-name">{race.bateauName}</td>
              <td class="time">{formatTime(race.tempsBrut)}</td>
              <td class="handicap">{race.typeHandicap}</td>
              <td class="time highlighted">{formatTime(race.tempsCorrige)}</td>
              <td class="actions">
                <button
                  on:click={() => deleteResult(race.raceResultId)}
                  class="btn-delete"
                  title="Supprimer"
                >
                  🗑️
                </button>
              </td>
            </tr>
          {/each}
        </tbody>
      </table>
    </div>
  {/if}
</div>

<style>
  .handicap-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  }

  h1 {
    color: #2c3e50;
    border-bottom: 3px solid #3498db;
    padding-bottom: 10px;
    margin-bottom: 30px;
  }

  h2 {
    color: #34495e;
    margin-top: 30px;
    margin-bottom: 20px;
  }

  .form-section {
    background: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 30px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .form-group {
    margin-bottom: 20px;
  }

  label {
    display: block;
    font-weight: 600;
    margin-bottom: 8px;
    color: #2c3e50;
  }

  select,
  input[type='number'] {
    width: 100%;
    padding: 10px;
    border: 1px solid #bdc3c7;
    border-radius: 4px;
    font-size: 14px;
    font-family: inherit;
  }

  select:focus,
  input[type='number']:focus {
    outline: none;
    border-color: #3498db;
    box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
  }

  .input-row {
    display: flex;
    gap: 15px;
    align-items: center;
  }

  .input-row input {
    flex: 1;
  }

  .time-display {
    background: #ecf0f1;
    padding: 10px 15px;
    border-radius: 4px;
    font-weight: 600;
    min-width: 80px;
    text-align: center;
  }

  .handicap-options {
    display: flex;
    gap: 30px;
    margin-top: 10px;
  }

  .handicap-options label {
    display: flex;
    flex-direction: column;
    gap: 5px;
    margin-bottom: 0;
    font-weight: normal;
    cursor: pointer;
  }

  .handicap-options input[type='radio'] {
    width: auto;
    margin-right: 5px;
  }

  .formula {
    font-size: 12px;
    color: #7f8c8d;
    font-style: italic;
    margin-top: 5px;
  }

  .btn-primary,
  .btn-delete {
    padding: 12px 24px;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .btn-primary {
    background: #3498db;
    color: white;
    width: 100%;
  }

  .btn-primary:hover:not(:disabled) {
    background: #2980b9;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(52, 152, 219, 0.3);
  }

  .btn-primary:disabled {
    background: #95a5a6;
    cursor: not-allowed;
  }

  .btn-delete {
    background: #e74c3c;
    color: white;
    padding: 8px 12px;
    font-size: 14px;
  }

  .btn-delete:hover {
    background: #c0392b;
  }

  .alert {
    padding: 12px 16px;
    border-radius: 4px;
    margin-bottom: 20px;
    font-weight: 500;
  }

  .alert-error {
    background: #fadbd8;
    color: #c0392b;
    border-left: 4px solid #e74c3c;
  }

  .alert-success {
    background: #d5f4e6;
    color: #27ae60;
    border-left: 4px solid #2ecc71;
  }

  .results-section {
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .results-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 15px;
  }

  .results-table thead {
    background: #34495e;
    color: white;
  }

  .results-table th {
    padding: 15px;
    text-align: left;
    font-weight: 600;
  }

  .results-table td {
    padding: 12px 15px;
    border-bottom: 1px solid #ecf0f1;
  }

  .result-row:hover {
    background: #f8f9fa;
  }

  .position {
    font-weight: 700;
    color: #3498db;
    font-size: 18px;
    text-align: center;
  }

  .bateau-name {
    font-weight: 600;
    color: #2c3e50;
  }

  .time {
    font-family: 'Courier New', monospace;
    color: #7f8c8d;
  }

  .time.highlighted {
    background: #f39c12;
    color: white;
    font-weight: 600;
    border-radius: 4px;
  }

  .handicap {
    text-align: center;
    font-weight: 600;
    background: #ecf0f1;
    border-radius: 4px;
    padding: 5px 10px !important;
  }

  .actions {
    text-align: center;
  }

  @media (max-width: 768px) {
    .handicap-options {
      flex-direction: column;
    }

    .results-table {
      font-size: 14px;
    }

    .results-table th,
    .results-table td {
      padding: 10px;
    }
  }
</style>
