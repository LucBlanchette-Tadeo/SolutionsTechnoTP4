<script lang="ts">
  import { onMount } from 'svelte';
  import { 
    listClasseCourse,
    createClasseCourse,
    listInscriptionsByCourse, 
    getAvailableBateaux, 
    addBateauToCourse, 
    removeBateauFromCourse,
    updateClassement
  } from '../lib/api';

  let classesCourse: any[] = [];
  let inscriptions: any[] = [];
  let availableBateaux: any[] = [];
  
  let selectedCourseId: number | null = null;
  let selectedBateauId: number | null = null;
  let loading = false;
  let message = '';
  let editingClassementId: number | null = null;
  let editingClassementValue: number | null = null;
  
  // Formulaire de création de course
  let newCourseName = '';
  let newCourseType = '';

  async function loadCourses() {
    classesCourse = await listClasseCourse();
  }

  async function createNewCourse() {
    if (!newCourseName || !newCourseType) {
      message = 'Erreur: Veuillez remplir tous les champs';
      return;
    }
    
    loading = true;
    message = '';
    
    try {
      await createClasseCourse({ nomClasseCourse: newCourseName, typeClasse: newCourseType });
      newCourseName = '';
      newCourseType = '';
      await loadCourses();
      message = 'Course créée avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  async function selectCourse(courseId: number) {
    selectedCourseId = courseId;
    selectedBateauId = null;
    loading = true;
    message = '';
    
    try {
      inscriptions = await listInscriptionsByCourse(courseId);
      availableBateaux = await getAvailableBateaux(courseId);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  async function addBateau() {
    if (!selectedCourseId || !selectedBateauId) return;
    
    loading = true;
    message = '';
    
    try {
      await addBateauToCourse(selectedBateauId, selectedCourseId);
      selectedBateauId = null;
      await selectCourse(selectedCourseId);
      message = 'Bateau ajouté avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  async function removeBateau(inscriptionId: number) {
    if (!selectedCourseId) return;
    
    loading = true;
    message = '';
    
    try {
      await removeBateauFromCourse(inscriptionId);
      await selectCourse(selectedCourseId);
      message = 'Bateau retiré avec succès!';
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  function startEditClassement(inscriptionId: number, currentClassement: number | null) {
    editingClassementId = inscriptionId;
    editingClassementValue = currentClassement;
  }

  async function saveClassement(inscriptionId: number) {
    loading = true;
    message = '';
    
    try {
      await updateClassement(inscriptionId, editingClassementValue);
      await selectCourse(selectedCourseId!);
      message = 'Classement mis à jour avec succès!';
      editingClassementId = null;
      editingClassementValue = null;
      setTimeout(() => message = '', 3000);
    } catch (error) {
      message = `Erreur: ${error instanceof Error ? error.message : 'Erreur inconnue'}`;
    } finally {
      loading = false;
    }
  }

  function cancelEditClassement() {
    editingClassementId = null;
    editingClassementValue = null;
  }

  onMount(loadCourses);
</script>

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

  .create-course-section {
    background-color: white;
    padding: 25px;
    border-radius: 8px;
    border: 2px solid #667eea;
    margin-bottom: 30px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .create-course-section h2 {
    color: #667eea;
    margin-top: 0;
    margin-bottom: 15px;
  }

  .create-form {
    display: grid;
    gap: 10px;
    grid-template-columns: 1fr 1fr auto;
  }

  .create-form input {
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
  }

  .create-form button {
    padding: 12px 20px;
    background-color: #667eea;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 600;
    transition: background-color 0.3s ease;
  }

  .create-form button:hover:not(:disabled) {
    background-color: #764ba2;
  }

  .create-form button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  .course-selector {
    margin-bottom: 30px;
  }

  .course-selector h2 {
    font-size: 18px;
    margin-bottom: 15px;
    color: #555;
  }

  .course-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 15px;
  }

  .course-btn {
    padding: 16px;
    background-color: white;
    border: 2px solid #667eea;
    border-radius: 8px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    text-align: left;
    color: #000;
  }

  .course-btn:hover {
    background-color: #f0f0f0;
    border-color: #764ba2;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
  }

  .course-btn.active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-color: #667eea;
  }

  .course-name {
    font-weight: 600;
    margin-bottom: 6px;
  }

  .course-type {
    font-size: 12px;
    opacity: 0.8;
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

  .classement-cell {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .classement-input {
    width: 60px;
    padding: 6px;
    border: 2px solid #667eea;
    border-radius: 4px;
    font-size: 14px;
    font-weight: 600;
  }

  .classement-button {
    padding: 6px 10px;
    font-size: 12px;
    margin: 0;
  }

  .save-classement {
    background-color: #28a745;
  }

  .save-classement:hover:not(:disabled) {
    background-color: #218838;
  }

  .cancel-classement {
    background-color: #6c757d;
  }

  .cancel-classement:hover:not(:disabled) {
    background-color: #5a6268;
  }

  .edit-classement-btn {
    background-color: #ffc107;
    color: #000;
    padding: 6px 12px;
    font-size: 12px;
  }

  .edit-classement-btn:hover:not(:disabled) {
    background-color: #e0a800;
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

    .create-form {
      grid-template-columns: 1fr;
    }

    .course-grid {
      grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
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

<div class="container">
  <h1>🏁 Gestion des Courses</h1>
  
  {#if message}
    <div class="message" class:success={message.includes('succès')} class:error={message.includes('Erreur')}>
      {message}
    </div>
  {/if}

  <div class="create-course-section">
    <h2>➕ Créer une nouvelle course</h2>
    <div class="create-form">
      <input 
        type="text" 
        placeholder="Nom de la course" 
        bind:value={newCourseName}
        disabled={loading}
      />
      <input 
        type="text" 
        placeholder="Type de bateau (ex: Laser, Optimist)" 
        bind:value={newCourseType}
        disabled={loading}
      />
      <button on:click={createNewCourse} disabled={loading || !newCourseName || !newCourseType}>
        {loading ? 'Création...' : '✅ Créer'}
      </button>
    </div>
  </div>

  <div class="course-selector">
    <h2>Sélectionnez une course</h2>
    <div class="course-grid">
      {#each classesCourse as course (course.id)}
        <button 
          class="course-btn" 
          class:active={selectedCourseId === course.id}
          on:click={() => selectCourse(course.id)}
        >
          <div class="course-name">{course.nomClasseCourse}</div>
          <div class="course-type">{course.typeClasse}</div>
        </button>
      {/each}
    </div>
  </div>

  {#if selectedCourseId}
    <div class="content">
      <div class="add-section">
        <h3>Ajouter un bateau à la course</h3>
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
            {loading ? 'Ajout...' : '➕ Ajouter'}
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
                  <th>Classement</th>
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
                      {#if editingClassementId === insc.id}
                        <div class="classement-cell">
                          <input 
                            type="number" 
                            class="classement-input"
                            bind:value={editingClassementValue}
                            min="1"
                            placeholder="Position"
                            disabled={loading}
                          />
                          <button 
                            class="classement-button save-classement"
                            on:click={() => saveClassement(insc.id)}
                            disabled={loading || editingClassementValue === null}
                          >
                            ✓
                          </button>
                          <button 
                            class="classement-button cancel-classement"
                            on:click={cancelEditClassement}
                            disabled={loading}
                          >
                            ✕
                          </button>
                        </div>
                      {:else}
                        <div class="classement-cell">
                          <span>{insc.classement || '—'}</span>
                          <button 
                            class="edit-classement-btn"
                            on:click={() => startEditClassement(insc.id, insc.classement)}
                            disabled={loading}
                          >
                            ✏️
                          </button>
                        </div>
                      {/if}
                    </td>
                    <td>
                      <button 
                        class="remove-btn" 
                        on:click={() => removeBateau(insc.id)}
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
        {/if}
      </div>
    </div>
  {/if}
</div>
