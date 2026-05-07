<script lang="ts">
  import { onMount } from 'svelte';
  import Login from './lib/Login.svelte';
  import Courses from './routes/courses.svelte';
  import RaceResults from './lib/RaceResults.svelte';

  // États
  let isAuthenticated = false;
  let currentUsername = '';
  let currentPage = 'home';
  let error = '';
  let success = '';
  let loading = false;

  // Données
  let bateaux: any[] = [];
  let classes: any[] = [];
  let series: any[] = [];
  let courses: any[] = [];
  let classesCourse: any[] = [];

  // Formulaires
  let bateauForm = { nom: '', numero: '', barreur: '', classeId: '' };
  let classeForm = { nom: '' };
  let serieForm = { nom: '', nombreCourses: 0, nombreCoursesACompter: 0 };
  let classeCourseForm = { nom: '', type: 'monotype' };

  // État de modification
  let editingBateau: any = null;
  let editingClasse: any = null;
  let editingSerie: any = null;
  let editingClasseCourse: any = null;

  // État pour gérer les courses dans les séries
  let selectedSerieForCourses: any = null;
  let seriesCourses: any[] = [];
  let availableClassesCourseForSerie: any[] = [];
  let selectedClasseCourseIdToAdd = '';
  let showLeaderboardModal = false;
  let selectedCourseForLeaderboard: any = null;
  let leaderboardData: any[] = [];

  // API
  const API = 'http://localhost:8080/api';

  // ============= FETCH FUNCTIONS =============
  async function fetchBateaux() {
    try {
      const res = await fetch(`${API}/bateaux`);
      if (!res.ok) throw new Error('Erreur API');
      bateaux = await res.json();
    } catch (e: any) {
      error = 'Erreur: ' + e.message;
    }
  }

  async function fetchClasses() {
    try {
      const res = await fetch(`${API}/classe-bateau`);
      if (!res.ok) throw new Error('Erreur API');
      classes = await res.json();
    } catch (e: any) {
      error = 'Erreur: ' + e.message;
    }
  }

  async function fetchSeries() {
    try {
      const res = await fetch(`${API}/series`);
      if (!res.ok) throw new Error('Erreur API');
      series = await res.json();
    } catch (e: any) {
      error = 'Erreur: ' + e.message;
    }
  }

  async function fetchClassesCourse() {
    try {
      const res = await fetch(`${API}/classe-course`);
      if (!res.ok) throw new Error('Erreur API');
      classesCourse = await res.json();
    } catch (e: any) {
      error = 'Erreur: ' + e.message;
    }
  }

  async function fetchCourses() {
    try {
      const res = await fetch(`${API}/courses`);
      if (!res.ok) throw new Error('Erreur API');
      courses = await res.json();
    } catch (e: any) {
      error = 'Erreur: ' + e.message;
    }
  }

  // ============= BATEAU FUNCTIONS =============
  async function addBateau() {
    error = '';
    success = '';
    
    // Validation
    if (!bateauForm.nom || !bateauForm.nom.trim()) {
      error = '❌ Le nom du bateau est obligatoire';
      return;
    }
    if (!bateauForm.numero || bateauForm.numero === '') {
      error = '❌ Le numéro de voile est obligatoire';
      return;
    }
    if (!bateauForm.barreur || !bateauForm.barreur.trim()) {
      error = '❌ Le nom du barreur est obligatoire';
      return;
    }
    if (!bateauForm.classeId || bateauForm.classeId === '') {
      error = '❌ La classe de bateau est obligatoire';
      return;
    }
    
    try {
      loading = true;
      const method = editingBateau ? 'PUT' : 'POST';
      const url = editingBateau ? `${API}/bateaux/${editingBateau.id}` : `${API}/bateaux`;
      
      const payload = {
        nomBateau: bateauForm.nom.trim(),
        numeroVoile: parseInt(bateauForm.numero),
        nomBarreur: bateauForm.barreur.trim(),
        classeBateau: { id: parseInt(bateauForm.classeId) }
      };
      
      console.log('📤 Envoi du bateau:', payload);
      
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      
      console.log('📥 Réponse status:', res.status);
      
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Erreur ${res.status}: ${errorText}`);
      }
      
      const result = await res.json();
      console.log('✅ Bateau sauvegardé:', result);
      
      success = editingBateau ? '✅ Bateau modifié avec succès!' : '✅ Bateau créé avec succès!';
      bateauForm = { nom: '', numero: '', barreur: '', classeId: '' };
      editingBateau = null;
      await fetchBateaux();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      console.error('❌ Erreur:', e);
      error = '❌ ' + (e.message || 'Erreur inconnue');
    } finally {
      loading = false;
    }
  }

  async function deleteBateau(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce bateau ?')) return;
    error = '';
    try {
      loading = true;
      const res = await fetch(`${API}/bateaux/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Erreur suppression');
      success = '✅ Bateau supprimé avec succès!';
      await fetchBateaux();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + e.message;
    } finally {
      loading = false;
    }
  }

  function editBateau(bateau: any) {
    editingBateau = bateau;
    bateauForm = {
      nom: bateau.nomBateau,
      numero: bateau.numeroVoile.toString(),
      barreur: bateau.nomBarreur,
      classeId: bateau.classeBateau.id.toString()
    };
  }

  function cancelEditBateau() {
    editingBateau = null;
    bateauForm = { nom: '', numero: '', barreur: '', classeId: '' };
  }

  // ============= CLASSE FUNCTIONS =============
  async function addClass() {
    error = '';
    success = '';
    
    if (!classeForm.nom || !classeForm.nom.trim()) {
      error = '❌ Le nom est obligatoire';
      return;
    }
    
    try {
      loading = true;
      const method = editingClasse ? 'PUT' : 'POST';
      const url = editingClasse ? `${API}/classe-bateau/${editingClasse.id}` : `${API}/classe-bateau`;
      
      const payload = { nomClasse: classeForm.nom.trim() };
      console.log('📤 Envoi classe bateau:', payload);
      
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      
      console.log('📥 Réponse status:', res.status);
      
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Erreur ${res.status}: ${errorText}`);
      }
      
      success = editingClasse ? '✅ Classe modifiée avec succès!' : '✅ Classe créée avec succès!';
      classeForm = { nom: '' };
      editingClasse = null;
      await fetchClasses();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      console.error('❌ Erreur:', e);
      error = '❌ ' + (e.message || 'Erreur inconnue');
    } finally {
      loading = false;
    }
  }

  async function deleteClasse(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette classe ?')) return;
    error = '';
    try {
      loading = true;
      const res = await fetch(`${API}/classe-bateau/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Erreur suppression');
      success = '✅ Classe supprimée avec succès!';
      await fetchClasses();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + e.message;
    } finally {
      loading = false;
    }
  }

  function editClasse(classe: any) {
    editingClasse = classe;
    classeForm = { nom: classe.nomClasse };
  }

  function cancelEditClasse() {
    editingClasse = null;
    classeForm = { nom: '' };
  }

  // ============= SERIE FUNCTIONS =============
  async function addSerie() {
    error = '';
    success = '';
    
    if (!serieForm.nom || !serieForm.nom.trim()) {
      error = '❌ Le nom de la série est obligatoire';
      return;
    }
    
    try {
      loading = true;
      const method = editingSerie ? 'PUT' : 'POST';
      const url = editingSerie ? `${API}/series/${editingSerie.id}` : `${API}/series`;
      
      const payload = {
        nomSerie: serieForm.nom.trim(),
        nombreCourses: serieForm.nombreCourses || 0,
        nombreCoursesACompter: serieForm.nombreCoursesACompter || 0
      };
      
      console.log('📤 Envoi série:', payload);
      
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      
      console.log('📥 Réponse status:', res.status);
      
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Erreur ${res.status}: ${errorText}`);
      }
      
      const result = await res.json();
      console.log('✅ Série sauvegardée:', result);
      
      success = editingSerie ? '✅ Série modifiée avec succès!' : '✅ Série créée avec succès!';
      serieForm = { nom: '', nombreCourses: 0, nombreCoursesACompter: 0 };
      editingSerie = null;
      await fetchSeries();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      console.error('❌ Erreur:', e);
      error = '❌ ' + (e.message || 'Erreur inconnue');
    } finally {
      loading = false;
    }
  }

  async function deleteSerie(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette série ?')) return;
    error = '';
    try {
      loading = true;
      const res = await fetch(`${API}/series/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Erreur suppression');
      success = '✅ Série supprimée avec succès!';
      await fetchSeries();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + e.message;
    } finally {
      loading = false;
    }
  }

  function editSerie(serie: any) {
    editingSerie = serie;
    serieForm = {
      nom: serie.nomSerie,
      nombreCourses: serie.nombreCourses || 0,
      nombreCoursesACompter: serie.nombreCoursesACompter || 0
    };
  }

  function cancelEditSerie() {
    editingSerie = null;
    serieForm = { nom: '', nombreCourses: 0, nombreCoursesACompter: 0 };
  }

  // ============= SERIE COURSES FUNCTIONS =============
  async function selectSerieForCourses(serie: any) {
    selectedSerieForCourses = serie;
    try {
      const res = await fetch(`${API}/serie-classe-course/serie/${serie.id}`);
      if (!res.ok) throw new Error('Erreur API');
      seriesCourses = await res.json();
      updateAvailableClassesCourse();
    } catch (e: any) {
      error = '❌ ' + (e.message || 'Erreur inconnue');
      setTimeout(() => { error = ''; }, 3000);
    }
  }

  function updateAvailableClassesCourse() {
    const linkedIds = seriesCourses.map((sc: any) => sc.classeCourse.id);
    availableClassesCourseForSerie = classesCourse.filter(c => !linkedIds.includes(c.id));
  }

  async function addCourseToSerie() {
    if (!selectedSerieForCourses || !selectedClasseCourseIdToAdd) {
      error = '❌ Veuillez sélectionner une course';
      return;
    }
    try {
      loading = true;
      const payload = {
        serie: { id: selectedSerieForCourses.id },
        classeCourse: { id: parseInt(selectedClasseCourseIdToAdd) },
        ordreCourse: seriesCourses.length + 1
      };

      const res = await fetch(`${API}/serie-classe-course`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Erreur ${res.status}: ${errorText}`);
      }

      success = '✅ Course ajoutée à la série!';
      selectedClasseCourseIdToAdd = '';
      await selectSerieForCourses(selectedSerieForCourses);
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + (e.message || 'Erreur inconnue');
      setTimeout(() => { error = ''; }, 3000);
    } finally {
      loading = false;
    }
  }

  async function removeCourseFromSerie(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette course de la série ?')) return;
    try {
      loading = true;
      const res = await fetch(`${API}/serie-classe-course/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Erreur suppression');
      success = '✅ Course supprimée de la série!';
      await selectSerieForCourses(selectedSerieForCourses);
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + e.message;
      setTimeout(() => { error = ''; }, 3000);
    } finally {
      loading = false;
    }
  }

  async function showCourseLeaderboard(course: any) {
    try {
      selectedCourseForLeaderboard = course;
      // Utiliser l'endpoint des inscriptions pour la course
      const res = await fetch(`${API}/inscriptions/course/${course.classeCourse.id}`);
      if (!res.ok) throw new Error('Erreur API');
      leaderboardData = await res.json();
      showLeaderboardModal = true;
    } catch (e: any) {
      error = '❌ ' + (e.message || 'Erreur inconnue');
      setTimeout(() => { error = ''; }, 3000);
    }
  }

  // ============= CLASSE COURSE FUNCTIONS =============
  async function addClasseCourse() {
    error = '';
    success = '';
    
    if (!classeCourseForm.nom || !classeCourseForm.nom.trim()) {
      error = '❌ Le nom est obligatoire';
      return;
    }
    
    try {
      loading = true;
      const method = editingClasseCourse ? 'PUT' : 'POST';
      const url = editingClasseCourse ? `${API}/classe-course/${editingClasseCourse.id}` : `${API}/classe-course`;
      
      const payload = {
        nomClasseCourse: classeCourseForm.nom.trim(),
        typeClasse: classeCourseForm.type
      };
      
      console.log('📤 Envoi classe course:', payload);
      
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      
      console.log('📥 Réponse status:', res.status);
      
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Erreur ${res.status}: ${errorText}`);
      }
      
      success = editingClasseCourse ? '✅ Classe de course modifiée!' : '✅ Classe de course créée!';
      classeCourseForm = { nom: '', type: 'monotype' };
      editingClasseCourse = null;
      await fetchClassesCourse();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      console.error('❌ Erreur:', e);
      error = '❌ ' + (e.message || 'Erreur inconnue');
    } finally {
      loading = false;
    }
  }

  async function deleteClasseCourse(id: number) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette classe de course ?')) return;
    error = '';
    try {
      loading = true;
      const res = await fetch(`${API}/classe-course/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Erreur suppression');
      success = '✅ Classe de course supprimée!';
      await fetchClassesCourse();
      setTimeout(() => { success = ''; }, 3000);
    } catch (e: any) {
      error = '❌ ' + e.message;
    } finally {
      loading = false;
    }
  }

  function editClasseCourse(classeCourse: any) {
    editingClasseCourse = classeCourse;
    classeCourseForm = {
      nom: classeCourse.nomClasseCourse,
      type: classeCourse.typeClasse
    };
  }

  function cancelEditClasseCourse() {
    editingClasseCourse = null;
    classeCourseForm = { nom: '', type: 'monotype' };
  }

  // ============= NAVIGATION =============
  function goToPage(page: string) {
    currentPage = page;
    error = '';
    success = '';
    // Reset all editing states
    editingBateau = null;
    editingClasse = null;
    editingSerie = null;
    editingClasseCourse = null;
  }

  onMount(async () => {
    // Vérifier si l'utilisateur est déjà authentifié
    const authenticated = localStorage.getItem('authenticated');
    const username = localStorage.getItem('username');
    
    if (authenticated === 'true' && username) {
      isAuthenticated = true;
      currentUsername = username;
      await fetchBateaux();
      await fetchClasses();
      await fetchSeries();
      await fetchClassesCourse();
    }
  });

  function handleLoginSuccess(username: string) {
    isAuthenticated = true;
    currentUsername = username;
    currentPage = 'home';
    fetchBateaux();
    fetchClasses();
    fetchSeries();
    fetchClassesCourse();
  }

  function handleLogout() {
    isAuthenticated = false;
    currentUsername = '';
    localStorage.removeItem('authenticated');
    localStorage.removeItem('username');
    currentPage = 'home';
  }
</script>

<div class="app">
  {#if !isAuthenticated}
    <Login onLoginSuccess={handleLoginSuccess} />
  {:else}
    <nav class="navbar">
      <h1 class="logo"> YRR </h1>
      <div class="nav-buttons">
        <button 
          class:active={currentPage === 'home'} 
          on:click={() => goToPage('home')}
        >
          🏠 Accueil
        </button>
        <button 
          class:active={currentPage === 'bateaux'} 
          on:click={() => goToPage('bateaux')}
        >
          🚤 Bateaux
        </button>
        <button 
          class:active={currentPage === 'classes'} 
          on:click={() => goToPage('classes')}
        >
          📋 Classes
        </button>
        <button 
          class:active={currentPage === 'series'} 
          on:click={() => goToPage('series')}
        >
          🏆 Séries
        </button>
        <button 
          class:active={currentPage === 'courses'} 
          on:click={() => goToPage('courses')}
        >
          🏁 Courses
        </button>
        <button 
          class:active={currentPage === 'resultats'} 
          on:click={() => goToPage('resultats')}
        >
          🎯 Résultats
        </button>
        <div class="user-section">
          <span class="username">👤 {currentUsername}</span>
          <button class="logout-btn" on:click={handleLogout}>
            🚪 Déconnexion
          </button>
        </div>
      </div>
    </nav>

  <main class="content">
    {#if error}
      <div class="alert alert-error">{error}</div>
    {/if}
    
    {#if success}
      <div class="alert alert-success">{success}</div>
    {/if}

    <!-- HOME PAGE -->
    {#if currentPage === 'home'}
      <div class="hero">
        <h2>Bienvenue 🌊</h2>
        <p>Gérez facilement votre flotte de bateaux de voile</p>
        <div class="hero-buttons">
          <button class="btn-primary" on:click={() => goToPage('bateaux')}>
            ➜ Voir les Bateaux
          </button>
          <button class="btn-secondary" on:click={() => goToPage('series')}>
            ➜ Voir les Séries
          </button>
        </div>
      </div>

    <!-- BATEAUX PAGE -->
    {:else if currentPage === 'bateaux'}
      <div class="page">
        <h2>🚤 Gestion des Bateaux</h2>
        
        <div class="form-section">
          <h3>{editingBateau ? '✏️ Modifier le bateau' : '➕ Ajouter un nouveau bateau'}</h3>
          <form on:submit|preventDefault={addBateau}>
            <input 
              type="text"
              placeholder="Nom du bateau" 
              bind:value={bateauForm.nom}
              disabled={loading}
            />
            <input 
              type="number"
              placeholder="Numéro de voile" 
              bind:value={bateauForm.numero}
              disabled={loading}
            />
            <input 
              type="text"
              placeholder="Nom du barreur" 
              bind:value={bateauForm.barreur}
              disabled={loading}
            />
            <select bind:value={bateauForm.classeId} disabled={loading}>
              <option value="">-- Sélectionner une classe --</option>
              {#each classes as c (c.id)}
                <option value={c.id}>{c.nomClasse}</option>
              {/each}
            </select>
            <div class="form-buttons">
              <button type="submit" disabled={loading} class="btn-primary">
                {loading ? '⏳ Traitement...' : (editingBateau ? '💾 Mettre à jour' : '➕ Créer')}
              </button>
              {#if editingBateau}
                <button type="button" on:click={cancelEditBateau} class="btn-secondary">
                  ❌ Annuler
                </button>
              {/if}
            </div>
          </form>
        </div>

        <div class="table-section">
          <h3>Liste des bateaux ({bateaux.length})</h3>
          {#if bateaux.length > 0}
            <div class="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Voile</th>
                    <th>Barreur</th>
                    <th>Classe</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {#each bateaux as b (b.id)}
                    <tr>
                      <td>#{b.id}</td>
                      <td><strong>{b.nomBateau || '-'}</strong></td>
                      <td>{b.numeroVoile}</td>
                      <td>{b.nomBarreur || '-'}</td>
                      <td><span class="badge">{b.classeBateau?.nomClasse}</span></td>
                      <td class="actions">
                        <button class="btn-edit" on:click={() => editBateau(b)} disabled={loading}>✏️ Modifier</button>
                        <button class="btn-delete" on:click={() => deleteBateau(b.id)} disabled={loading}>🗑️ Supprimer</button>
                      </td>
                    </tr>
                  {/each}
                </tbody>
              </table>
            </div>
          {:else}
            <p class="empty">Aucun bateau. Ajoutez-en un ci-dessus ! 👆</p>
          {/if}
        </div>
      </div>

    <!-- CLASSES PAGE -->
    {:else if currentPage === 'classes'}
      <div class="page">
        <h2>📋 Gestion des Classes de Bateau</h2>
        
        <div class="form-section">
          <h3>{editingClasse ? '✏️ Modifier la classe' : '➕ Ajouter une nouvelle classe'}</h3>
          <form on:submit|preventDefault={addClass}>
            <input 
              type="text"
              placeholder="Nom de la classe (ex: Laser, J70)" 
              bind:value={classeForm.nom}
              disabled={loading}
            />
            <div class="form-buttons">
              <button type="submit" disabled={loading} class="btn-primary">
                {loading ? '⏳ Traitement...' : (editingClasse ? '💾 Mettre à jour' : '➕ Créer')}
              </button>
              {#if editingClasse}
                <button type="button" on:click={cancelEditClasse} class="btn-secondary">
                  ❌ Annuler
                </button>
              {/if}
            </div>
          </form>
        </div>

        <div class="table-section">
          <h3>Classes disponibles ({classes.length})</h3>
          {#if classes.length > 0}
            <div class="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {#each classes as c (c.id)}
                    <tr>
                      <td>#{c.id}</td>
                      <td><strong>{c.nomClasse}</strong></td>
                      <td class="actions">
                        <button class="btn-edit" on:click={() => editClasse(c)} disabled={loading}>✏️ Modifier</button>
                        <button class="btn-delete" on:click={() => deleteClasse(c.id)} disabled={loading}>🗑️ Supprimer</button>
                      </td>
                    </tr>
                  {/each}
                </tbody>
              </table>
            </div>
          {:else}
            <p class="empty">Aucune classe. Créez-en une ci-dessus ! 👆</p>
          {/if}
        </div>
      </div>

    <!-- SERIES PAGE -->
    {:else if currentPage === 'series'}
      <div class="page">
        <h2>📊 Gestion des Séries et Courses</h2>
        
        <!-- ÉTAPE 1: Instructions -->
        <div class="series-container">
          <!-- PANNEAU GAUCHE: Créer et lister les séries -->
          <div class="left-panel">
            <div class="form-section">
              <h3>{editingSerie ? '✏️ Modifier la série' : '➕ Créer une nouvelle série'}</h3>
              <form on:submit|preventDefault={addSerie}>
                <input 
                  type="text"
                  placeholder="Nom de la série (ex: Monotypes 2024)" 
                  bind:value={serieForm.nom}
                  disabled={loading}
                />
                <div class="form-buttons">
                  <button type="submit" disabled={loading || !serieForm.nom} class="btn-primary">
                    {loading ? '⏳ Traitement...' : (editingSerie ? '💾 Mettre à jour' : '➕ Créer')}
                  </button>
                  {#if editingSerie}
                    <button type="button" on:click={cancelEditSerie} class="btn-secondary">
                      ❌ Annuler
                    </button>
                  {/if}
                </div>
              </form>
            </div>

            <div class="table-section">
              <h3>📋 Séries existantes ({series.length})</h3>
              <p class="section-hint">💡 Cliquez sur une série pour la sélectionner</p>
              {#if series.length > 0}
                <div class="series-list">
                  {#each series as s (s.id)}
                    <div 
                      class="serie-item {serieForm.nom && series.find(x => x.id === editingSerie?.id)?.id === s.id ? 'active' : ''}"
                      on:click={() => { selectSerieForCourses(s); }}
                    >
                      <div class="serie-header">
                        <strong>{s.nomSerie}</strong>
                      </div>
                      <div class="serie-info">
                        <small>Courses: {s.nombreCourses} | À compter: {s.nombreCoursesACompter}</small>
                      </div>
                      <div class="serie-actions">
                        <button class="btn-edit" on:click|stopPropagation={() => editSerie(s)} disabled={loading}>✏️</button>
                        <button class="btn-delete" on:click|stopPropagation={() => deleteSerie(s.id)} disabled={loading}>🗑️</button>
                      </div>
                    </div>
                  {/each}
                </div>
              {:else}
                <p class="empty">Aucune série. Créez-en une ci-dessus ! 👆</p>
              {/if}
            </div>
          </div>

          <!-- PANNEAU DROIT: Gérer les courses de la série -->
          <div class="right-panel">
            {#if selectedSerieForCourses !== null}
              <div class="table-section">
                <div class="serie-selected-header">
                  <h2>🏁 Courses - {selectedSerieForCourses.nomSerie}</h2>
                  <span class="badge">{selectedSerieForCourses.nombreCourses} courses | {selectedSerieForCourses.nombreCoursesACompter} à compter</span>
                </div>

                <div class="add-course-section">
                  <h3>➕ Ajouter une course à cette série</h3>
                  <div class="add-course-row">
                    <select bind:value={selectedClasseCourseIdToAdd} disabled={loading}>
                      <option value="">-- Choisir une course --</option>
                      {#each availableClassesCourseForSerie as c (c.id)}
                        <option value={c.id}>{c.nomClasseCourse}</option>
                      {/each}
                    </select>
                    <button 
                      on:click={addCourseToSerie}
                      class="btn-primary"
                      disabled={!selectedClasseCourseIdToAdd || loading}
                    >
                      ➕ Ajouter
                    </button>
                  </div>
                </div>

                {#if seriesCourses.length > 0}
                  <div class="table-wrapper">
                    <h3>📊 Courses de cette série ({seriesCourses.length})</h3>
                    <p class="section-hint">💡 Cliquez sur "Classement" pour voir les bateaux inscrits</p>
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
                            <td class="center"><strong>#{idx + 1}</strong></td>
                            <td><strong>{sc.classeCourse?.nomClasseCourse}</strong></td>
                            <td class="actions">
                              <button 
                                class="btn-info"
                                on:click={() => showCourseLeaderboard(sc)}
                                disabled={loading}
                                title="Voir le classement des bateaux"
                              >
                                📊 Classement
                              </button>
                              <button 
                                class="btn-delete"
                                on:click={() => removeCourseFromSerie(sc.id)}
                                disabled={loading}
                                title="Retirer cette course de la série"
                              >
                                🗑️ Supprimer
                              </button>
                            </td>
                          </tr>
                        {/each}
                      </tbody>
                    </table>
                  </div>
                {:else}
                  <div class="empty-state">
                    <p>📭 Aucune course ajoutée à cette série</p>
                    <p class="hint">Utilisez le formulaire ci-dessus pour en ajouter une</p>
                  </div>
                {/if}
              </div>
            {:else}
              <div class="empty-state-large">
                <p><strong>Sélectionnez une série pour gérer ses courses</strong></p>
                <p class="hint">Créez une nouvelle série ou choisissez une série existante dans la liste de gauche</p>
              </div>
            {/if}
          </div>
        </div>
      </div>

      <!-- MODAL LEADERBOARD -->
      {#if showLeaderboardModal && selectedCourseForLeaderboard}
        <div class="modal-overlay" on:click={() => showLeaderboardModal = false}>
          <div class="modal" on:click={e => e.stopPropagation()}>
            <div class="modal-header">
              <h2>🏆 Leaderboard - {selectedCourseForLeaderboard.classeCourse?.nomClasseCourse}</h2>
              <button class="close-btn" on:click={() => showLeaderboardModal = false}>✕</button>
            </div>

            <div class="modal-content">
              {#if leaderboardData.length > 0}
                <div class="leaderboard-info">
                  <p>📊 <strong>{leaderboardData.length} bateau(x) inscrit(s)</strong></p>
                </div>
                <div class="leaderboard-table">
                  <table>
                    <thead>
                      <tr>
                        <th class="rank">Classement</th>
                        <th>Bateau</th>
                        <th>Barreur</th>
                        <th class="center">Voile</th>
                        <th class="center">Classe</th>
                        <th class="center">État</th>
                      </tr>
                    </thead>
                    <tbody>
                      {#each leaderboardData as entry, idx (entry.id)}
                        <tr class="rank-{idx === 0 ? 'gold' : idx === 1 ? 'silver' : idx === 2 ? 'bronze' : 'neutral'}">
                          <td class="rank">
                            <span class="position">
                              {#if idx === 0}🥇{:else if idx === 1}🥈{:else if idx === 2}🥉{:else}#{idx + 1}{/if}
                            </span>
                          </td>
                          <td><strong>{entry.bateau?.nomBateau || 'N/A'}</strong></td>
                          <td>{entry.bateau?.nomBarreur || 'N/A'}</td>
                          <td class="center">{entry.bateau?.numeroVoile || '-'}</td>
                          <td class="center">{entry.bateau?.classeBateau?.nomClasse || '-'}</td>
                          <td class="center">
                            <span class="status-badge">{entry.classement || '✓'}</span>
                          </td>
                        </tr>
                      {/each}
                    </tbody>
                  </table>
                </div>
                <div class="add-bateau-section">
                  <h3>➕ Ajouter un bateau</h3>
                  <p class="hint">Pour inscrire d'autres bateaux disponibles</p>
                </div>
              {:else}
                <div class="empty-leaderboard">
                  <p>📭 Aucun bateau inscrit à cette course</p>
                  <p class="hint">Inscrivez des bateaux pour commencer</p>
                </div>
              {/if}
            </div>
          </div>
        </div>
      {/if}

    <!-- COURSES PAGE -->
    {:else if currentPage === 'courses'}
      <Courses />

    <!-- RESULTATS PAGE -->
    {:else if currentPage === 'resultats'}
      <RaceResults />
    {/if}
  </main>
  {/if}
</div>

<style>
  :global(body) {
    margin: 0;
    padding: 0;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
    background-color: #f5f5f5;
  }

  .app {
    min-height: 100vh;
  }

  .navbar {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 20px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 20px;
  }

  .logo {
    margin: 0;
    font-size: 1.8em;
    font-weight: 700;
  }

  .nav-buttons {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
  }

  .nav-buttons button {
    background: rgba(255, 255, 255, 0.2);
    color: white;
    border: 2px solid transparent;
    padding: 10px 18px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
    transition: all 0.3s;
    font-size: 0.95em;
  }

  .nav-buttons button:hover {
    background: rgba(255, 255, 255, 0.3);
    transform: translateY(-2px);
  }

  .nav-buttons button.active {
    background: rgba(255, 255, 255, 0.4);
    border-color: white;
    box-shadow: 0 0 10px rgba(255, 255, 255, 0.3);
  }

  .user-section {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-left: auto;
  }

  .username {
    color: white;
    font-weight: 600;
    padding: 8px 12px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 6px;
  }

  .logout-btn {
    background: rgba(255, 59, 48, 0.8);
    color: white;
    border: 2px solid transparent;
    padding: 10px 18px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
    transition: all 0.3s;
    font-size: 0.95em;
  }

  .logout-btn:hover {
    background: rgba(255, 59, 48, 1);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(255, 59, 48, 0.4);
  }

  .content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 30px 20px;
  }

  .alert {
    padding: 16px 20px;
    border-radius: 8px;
    margin-bottom: 20px;
    font-weight: 500;
    animation: slideIn 0.3s ease;
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
    border: 2px solid #fcc;
    color: #c00;
  }

  .alert-success {
    background-color: #efe;
    border: 2px solid #cfc;
    color: #060;
  }

  .hero {
    text-align: center;
    padding: 80px 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .hero h2 {
    color: #667eea;
    font-size: 2.8em;
    margin-bottom: 15px;
  }

  .hero p {
    color: #666;
    font-size: 1.3em;
    margin-bottom: 40px;
  }

  .hero-buttons {
    display: flex;
    gap: 20px;
    justify-content: center;
    flex-wrap: wrap;
  }

  .page {
    animation: fadeIn 0.3s ease;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }

  .page h2 {
    color: #333;
    margin-bottom: 30px;
  }

  .form-section {
    background: white;
    padding: 30px;
    border-radius: 12px;
    margin-bottom: 40px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .form-section h3 {
    margin-top: 0;
    color: #333;
  }

  form {
    display: grid;
    gap: 15px;
  }

  input, select {
    padding: 14px 16px;
    border: 2px solid #ddd;
    border-radius: 8px;
    font-size: 1em;
    transition: all 0.3s;
  }

  input:focus, select:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  }

  input:disabled, select:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
  }

  .form-buttons {
    display: flex;
    gap: 15px;
  }

  .btn-primary {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border: none;
    padding: 14px 28px;
    border-radius: 8px;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s;
    flex: 1;
  }

  .btn-primary:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 8px 16px rgba(102, 126, 234, 0.4);
  }

  .btn-primary:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }

  .btn-secondary {
    background: white;
    color: #667eea;
    border: 2px solid #667eea;
    padding: 14px 28px;
    border-radius: 8px;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s;
    flex: 1;
  }

  .btn-secondary:hover {
    background: #667eea;
    color: white;
  }

  .table-wrapper {
    overflow-x: auto;
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
    padding: 16px;
    font-weight: 600;
    color: #333;
    border-bottom: 3px solid #ddd;
  }

  td {
    padding: 14px 16px;
    border-bottom: 1px solid #eee;
  }

  tbody tr:hover {
    background-color: #f9f9f9;
  }

  .actions {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
  }

  .btn-edit, .btn-delete {
    padding: 8px 12px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.85em;
    font-weight: 600;
    transition: all 0.3s;
    white-space: nowrap;
  }

  .btn-edit {
    background-color: #e3f2fd;
    color: #1976d2;
  }

  .btn-edit:hover:not(:disabled) {
    background-color: #1976d2;
    color: white;
  }

  .btn-delete {
    background-color: #ffebee;
    color: #c62828;
  }

  .btn-delete:hover:not(:disabled) {
    background-color: #c62828;
    color: white;
  }

  .btn-edit:disabled, .btn-delete:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }

  .table-section {
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .table-section h3 {
    margin-top: 0;
    color: #333;
  }

  .badge {
    display: inline-block;
    padding: 6px 12px;
    background-color: #e6f0ff;
    color: #0066cc;
    border-radius: 6px;
    font-size: 0.9em;
    font-weight: 600;
  }

  .badge-type {
    display: inline-block;
    padding: 6px 12px;
    background-color: #fff0e6;
    color: #ff8800;
    border-radius: 6px;
    font-size: 0.9em;
    font-weight: 600;
  }

  .empty {
    text-align: center;
    color: #999;
    padding: 50px 20px;
    font-style: italic;
  }

  @media (max-width: 768px) {
    .navbar {
      flex-direction: column;
      align-items: flex-start;
    }

    .nav-buttons {
      width: 100%;
      justify-content: space-between;
    }

    .hero h2 {
      font-size: 1.8em;
    }

    .form-buttons {
      flex-direction: column;
    }

    .actions {
      flex-direction: column;
    }

    th, td {
      padding: 10px 8px;
      font-size: 0.9em;
    }

    .series-container {
      grid-template-columns: 1fr;
    }
  }

  /* SERIES PAGE STYLES */
  .series-container {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 30px;
    margin-top: 20px;
  }

  .left-panel, .right-panel {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  .series-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .serie-item {
    padding: 15px;
    background-color: #f9f9f9;
    border: 2px solid transparent;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .serie-item:hover {
    background-color: #f0f0f0;
    border-color: #bdc3c7;
  }

  .serie-item.active {
    background-color: #d6eaf8;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  }

  .serie-header {
    margin-bottom: 5px;
    color: #2c3e50;
    font-weight: 600;
  }

  .serie-info {
    color: #7f8c8d;
    font-size: 0.9em;
  }

  .serie-actions {
    display: flex;
    gap: 8px;
  }

  .add-course-section {
    background-color: #f9f9f9;
    padding: 15px;
    border-radius: 8px;
    margin-bottom: 20px;
  }

  .add-course-section h3 {
    margin-top: 0;
    font-size: 1em;
    color: #333;
  }

  .add-course-row {
    display: flex;
    gap: 10px;
    align-items: center;
  }

  .add-course-row select {
    flex: 1;
    padding: 10px;
    border: 2px solid #ddd;
    border-radius: 6px;
    font-size: 0.95em;
  }

  .add-course-row .btn-primary {
    padding: 10px 16px;
    white-space: nowrap;
  }

  .center {
    text-align: center;
  }

  .empty-state-large {
    background: white;
    padding: 60px 20px;
    border-radius: 12px;
    text-align: center;
    color: #7f8c8d;
    font-size: 1.2em;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  /* MODAL LEADERBOARD */
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
    padding: 20px;
    border-bottom: 2px solid #ecf0f1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-radius: 12px 12px 0 0;
  }

  .modal-header h2 {
    margin: 0;
    flex: 1;
    color: white;
  }

  .close-btn {
    background-color: rgba(255, 255, 255, 0.3);
    color: white;
    border: none;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    cursor: pointer;
    font-size: 1.3rem;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
  }

  .close-btn:hover {
    background-color: rgba(255, 255, 255, 0.5);
  }

  .modal-content {
    padding: 20px;
  }

  .leaderboard-table {
    margin-top: 15px;
  }

  .leaderboard-table table {
    margin: 0;
  }

  .leaderboard-table tbody tr:nth-child(odd) {
    background-color: #fafafa;
  }

  .position {
    font-weight: bold;
    color: #667eea;
    font-size: 1.1rem;
  }

  .badge {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 12px;
    font-size: 0.8em;
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

  .btn-info {
    background-color: #2980b9;
    color: white;
    padding: 8px 12px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.85em;
    font-weight: 600;
    transition: all 0.3s;
    white-space: nowrap;
  }

  .btn-info:hover:not(:disabled) {
    background-color: #21618c;
  }

  /* GUIDE SECTION - Étapes explicites */
  .guide-section {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 15px;
    margin-bottom: 30px;
    padding: 20px;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    border-radius: 12px;
  }

  .guide-step {
    display: flex;
    gap: 12px;
    padding: 15px;
    background-color: white;
    border-radius: 8px;
    border-left: 4px solid #bdc3c7;
    transition: all 0.3s ease;
  }

  .guide-step.active {
    border-left-color: #667eea;
    box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
  }

  .guide-step:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .step-number {
    font-size: 1.5em;
    min-width: 40px;
  }

  .step-content {
    flex: 1;
  }

  .step-content strong {
    display: block;
    color: #2c3e50;
    margin-bottom: 5px;
    font-weight: 600;
  }

  .step-content p {
    margin: 0;
    color: #7f8c8d;
    font-size: 0.9em;
  }

  /* Section hint - Indications contextuelles */
  .section-hint {
    margin: 10px 0;
    padding: 10px;
    background-color: #fffacd;
    color: #8b7500;
    border-radius: 6px;
    font-size: 0.9em;
    font-style: italic;
    border-left: 4px solid #ffdc00;
  }

  /* Badges informatifs */
  .badge {
    display: inline-block;
    padding: 6px 12px;
    border-radius: 20px;
    font-size: 0.8em;
    font-weight: 600;
    white-space: nowrap;
    background-color: #e3f2fd;
    color: #1976d2;
  }

  .badge-info {
    background-color: #c8e6c9;
    color: #2e7d32;
  }

  /* En-tête de série sélectionnée */
  .serie-selected-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 15px;
    margin-bottom: 20px;
  }

  .serie-selected-header h2 {
    margin: 0;
    color: #2c3e50;
  }

  .serie-selected-header .badge {
    align-self: center;
  }

  /* États vides améliorés */
  .empty-state {
    background: #f9f9f9;
    padding: 30px 20px;
    border-radius: 12px;
    text-align: center;
    border: 2px dashed #bdc3c7;
  }

  .empty-state p {
    margin: 10px 0;
    color: #7f8c8d;
  }

  .empty-state p.hint {
    font-size: 0.9em;
    color: #95a5a6;
    font-style: italic;
  }

  .empty-state-large {
    background: white;
    padding: 60px 20px;
    border-radius: 12px;
    text-align: center;
    color: #7f8c8d;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  /* Leaderboard amélioré */
  .leaderboard-info {
    background-color: #e8f4f8;
    padding: 12px;
    border-radius: 6px;
    margin-bottom: 15px;
    text-align: center;
    color: #2c3e50;
    font-weight: 600;
  }

  .empty-leaderboard {
    background: #f9f9f9;
    padding: 30px 20px;
    border-radius: 12px;
    text-align: center;
  }

  .empty-leaderboard p {
    margin: 10px 0;
    color: #7f8c8d;
  }

  .empty-leaderboard p.hint {
    font-size: 0.9em;
    color: #95a5a6;
    font-style: italic;
  }

  /* Classe des rangées de leaderboard pour les médailles */
  tr.rank-gold {
    background-color: #fff9e6;
  }

  tr.rank-silver {
    background-color: #f5f5f5;
  }

  tr.rank-bronze {
    background-color: #ffe6cc;
  }

  tr.rank-neutral {
    background-color: #fafafa;
  }

  .rank {
    font-weight: 600;
    text-align: center;
    min-width: 60px;
  }

  .status-badge {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.85em;
    font-weight: 600;
    background-color: #d5f4e6;
    color: #27ae60;
  }

</style>