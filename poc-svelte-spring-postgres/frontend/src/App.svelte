<script lang="ts">
  import { onMount } from 'svelte';

  // États
  let currentPage = 'home';
  let error = '';
  let success = '';
  let loading = false;

  // Données
  let bateaux: any[] = [];
  let classes: any[] = [];
  let series: any[] = [];
  let classesCourse: any[] = [];

  // Formulaires
  let bateauForm = { nom: '', numero: '', barreur: '', classeId: '' };
  let classeForm = { nom: '' };
  let serieForm = { nom: '', classeCourseId: '' };
  let classeCourseForm = { nom: '', type: 'monotype' };

  // État de modification
  let editingBateau: any = null;
  let editingClasse: any = null;
  let editingSerie: any = null;
  let editingClasseCourse: any = null;

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
    if (!serieForm.classeCourseId || serieForm.classeCourseId === '') {
      error = '❌ La classe de course est obligatoire';
      return;
    }
    
    try {
      loading = true;
      const method = editingSerie ? 'PUT' : 'POST';
      const url = editingSerie ? `${API}/series/${editingSerie.id}` : `${API}/series`;
      
      const payload = {
        nomSerie: serieForm.nom.trim(),
        classeCourse: { id: parseInt(serieForm.classeCourseId) }
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
      serieForm = { nom: '', classeCourseId: '' };
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
      classeCourseId: serie.classeCourse.id.toString()
    };
  }

  function cancelEditSerie() {
    editingSerie = null;
    serieForm = { nom: '', classeCourseId: '' };
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
    await fetchBateaux();
    await fetchClasses();
    await fetchSeries();
    await fetchClassesCourse();
  });
</script>

<div class="app">
  <nav class="navbar">
    <h1 class="logo">⛵ Bateau Manager</h1>
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
        <h2>Bienvenue au Bateau Manager 🌊</h2>
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
        <h2>🏆 Gestion des Séries</h2>
        
        <div class="form-section">
          <h3>{editingSerie ? '✏️ Modifier la série' : '➕ Ajouter une nouvelle série'}</h3>
          <form on:submit|preventDefault={addSerie}>
            <input 
              type="text"
              placeholder="Nom de la série" 
              bind:value={serieForm.nom}
              disabled={loading}
            />
            <select bind:value={serieForm.classeCourseId} disabled={loading}>
              <option value="">-- Sélectionner une classe de course --</option>
              {#each classesCourse as cc (cc.id)}
                <option value={cc.id}>{cc.nomClasseCourse}</option>
              {/each}
            </select>
            <div class="form-buttons">
              <button type="submit" disabled={loading} class="btn-primary">
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
          <h3>Séries existantes ({series.length})</h3>
          {#if series.length > 0}
            <div class="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Classe de Course</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {#each series as s (s.id)}
                    <tr>
                      <td>#{s.id}</td>
                      <td><strong>{s.nomSerie}</strong></td>
                      <td><span class="badge">{s.classeCourse?.nomClasseCourse}</span></td>
                      <td class="actions">
                        <button class="btn-edit" on:click={() => editSerie(s)} disabled={loading}>✏️ Modifier</button>
                        <button class="btn-delete" on:click={() => deleteSerie(s.id)} disabled={loading}>🗑️ Supprimer</button>
                      </td>
                    </tr>
                  {/each}
                </tbody>
              </table>
            </div>
          {:else}
            <p class="empty">Aucune série. Créez-en une ci-dessus ! 👆</p>
          {/if}
        </div>
      </div>

    <!-- COURSES PAGE -->
    {:else if currentPage === 'courses'}
      <div class="page">
        <h2>🏁 Gestion des Classes de Course</h2>
        
        <div class="form-section">
          <h3>{editingClasseCourse ? '✏️ Modifier' : '➕ Ajouter une nouvelle classe de course'}</h3>
          <form on:submit|preventDefault={addClasseCourse}>
            <input 
              type="text"
              placeholder="Nom de la classe de course" 
              bind:value={classeCourseForm.nom}
              disabled={loading}
            />
            <select bind:value={classeCourseForm.type} disabled={loading}>
              <option value="monotype">Monotype</option>
              <option value="handicap">Handicap</option>
            </select>
            <div class="form-buttons">
              <button type="submit" disabled={loading} class="btn-primary">
                {loading ? '⏳ Traitement...' : (editingClasseCourse ? '💾 Mettre à jour' : '➕ Créer')}
              </button>
              {#if editingClasseCourse}
                <button type="button" on:click={cancelEditClasseCourse} class="btn-secondary">
                  ❌ Annuler
                </button>
              {/if}
            </div>
          </form>
        </div>

        <div class="table-section">
          <h3>Classes de course ({classesCourse.length})</h3>
          {#if classesCourse.length > 0}
            <div class="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Type</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {#each classesCourse as cc (cc.id)}
                    <tr>
                      <td>#{cc.id}</td>
                      <td><strong>{cc.nomClasseCourse}</strong></td>
                      <td><span class="badge-type">{cc.typeClasse}</span></td>
                      <td class="actions">
                        <button class="btn-edit" on:click={() => editClasseCourse(cc)} disabled={loading}>✏️ Modifier</button>
                        <button class="btn-delete" on:click={() => deleteClasseCourse(cc.id)} disabled={loading}>🗑️ Supprimer</button>
                      </td>
                    </tr>
                  {/each}
                </tbody>
              </table>
            </div>
          {:else}
            <p class="empty">Aucune classe de course. Créez-en une ci-dessus ! 👆</p>
          {/if}
        </div>
      </div>
    {/if}
  </main>
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
  }
</style>