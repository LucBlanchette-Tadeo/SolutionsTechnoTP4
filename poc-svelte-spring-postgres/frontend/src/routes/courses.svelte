<script lang="ts">
  import { onMount } from 'svelte';
  import { 
    listClasseCourse,
    createClasseCourse,
    listClasseBateau
  } from '../lib/api';

  let courses: any[] = [];
  let classesBateau: any[] = [];
  
  let loading = false;
  let message = '';
  
  // Formulaire de création de course
  let newCourseName = '';
  let selectedType = 'monotype';
  let selectedClasseBateauId: number | null = null;

  async function loadData() {
    try {
      courses = await listClasseCourse();
      classesBateau = await listClasseBateau();
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    }
  }

  async function createNewCourse() {
    if (!newCourseName || !selectedClasseBateauId) {
      message = 'Erreur: Veuillez remplir tous les champs';
      return;
    }
    
    loading = true;
    message = '';
    
    try {
      await createClasseCourse({
        nomClasseCourse: newCourseName,
        typeClasse: selectedType
      });
      
      newCourseName = '';
      selectedClasseBateauId = null;
      
      await loadData();
      message = 'Course créée avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  onMount(loadData);
</script>

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

  .alert {
    padding: 1rem;
    border-radius: 6px;
    margin-bottom: 1rem;
    max-width: 1200px;
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
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 1rem;
  }

  .card {
    background: white;
    border-radius: 8px;
    padding: 1.5rem;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease;
    margin-bottom: 1.5rem;
  }

  .card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }

  .form-group {
    margin-bottom: 1rem;
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

  .courses-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 1rem;
  }

  .course-card {
    background: white;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    padding: 1.25rem;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .course-card:hover {
    border-color: #3498db;
    box-shadow: 0 2px 8px rgba(52, 152, 219, 0.2);
    transform: translateY(-2px);
  }

  .course-name {
    font-weight: 600;
    font-size: 1.1rem;
    color: #2c3e50;
    margin-bottom: 0.5rem;
  }

  .course-class {
    font-size: 0.85rem;
    color: #7f8c8d;
    background-color: #f0f0f0;
    padding: 0.25rem 0.5rem;
    border-radius: 4px;
    display: inline-block;
  }

  .empty-state {
    text-align: center;
    color: #7f8c8d;
    padding: 2rem;
    font-size: 1.1rem;
  }

  @media (max-width: 768px) {
    h1 {
      font-size: 1.8rem;
    }

    .courses-grid {
      grid-template-columns: 1fr;
    }
  }
</style>

<div class="container">
  <h1>🏁 Gestion des Courses</h1>

  {#if message}
    <div class="alert alert-{message.includes('Erreur') ? 'error' : 'success'}">
      {message}
    </div>
  {/if}

  <!-- Création d'une nouvelle course -->
  <div class="card">
    <h2>➕ Créer une nouvelle course</h2>
    <form on:submit|preventDefault={createNewCourse}>
      <div class="form-group">
        <label>Nom de la course</label>
        <input 
          type="text" 
          placeholder="Ex: Course Lacs - Classe IOM" 
          bind:value={newCourseName}
          disabled={loading}
          required 
        />
      </div>

      <div class="form-group">
        <label>Type de classe</label>
        <select bind:value={selectedType} disabled={loading} required>
          <option value="monotype">Monotype</option>
          <option value="polyvalent">Polyvalent</option>
          <option value="mixte">Mixte</option>
        </select>
      </div>

      <div class="form-group">
        <label>Classe de bateau autorisée</label>
        <select bind:value={selectedClasseBateauId} disabled={loading} required>
          <option value={null}>-- Sélectionner une classe de bateau --</option>
          {#each classesBateau as cb (cb.id)}
            <option value={cb.id}>
              {cb.nomClasse}
            </option>
          {/each}
        </select>
      </div>

      <button type="submit" class="btn btn-primary" disabled={loading || !newCourseName || !selectedClasseBateauId}>
        {loading ? '⏳ Création...' : '✅ Créer la course'}
      </button>
    </form>
  </div>

  <!-- Liste des courses existantes -->
  <div class="card">
    <h2>� Courses existantes ({courses.length})</h2>
    {#if courses.length > 0}
      <div class="courses-grid">
        {#each courses as course (course.id)}
          <div class="course-card">
            <div class="course-name">{course.nomClasseCourse}</div>
            <div class="course-class">{course.typeClasse}</div>
          </div>
        {/each}
      </div>
    {:else}
      <p class="empty-state">Aucune course créée</p>
    {/if}
  </div>
</div>
