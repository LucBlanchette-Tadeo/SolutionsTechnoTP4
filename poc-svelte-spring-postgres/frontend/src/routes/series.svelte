<script lang="ts">
  import { onMount } from 'svelte';
  import { listSeries, createSerie, listClasseCourse } from '$lib/api';

  let series: any[] = [];
  let classesCourse: any[] = [];
  let nom = '';
  let classeCourseId = '';
  let nombre = '0';
  let aCompter = '0';
  let error = '';

  async function load() {
    try {
      series = await listSeries();
      classesCourse = await listClasseCourse();
    } catch (e) {
      error = e.message || String(e);
    }
  }

  async function add() {
    try {
      await createSerie({ nomSerie: nom, classeCourse: { id: parseInt(classeCourseId||'0') }, nombreCourses: parseInt(nombre||'0'), nombreCoursesACompter: parseInt(aCompter||'0') });
      nom=''; classeCourseId=''; nombre='0'; aCompter='0';
      await load();
    } catch (e) {
      error = e.message || String(e);
    }
  }

  onMount(load);
</script>

<h1>Séries</h1>
{#if error}
  <div class="error">{error}</div>
{/if}
<form on:submit|preventDefault={add}>
  <input placeholder="Nom série" bind:value={nom} />
  <select bind:value={classeCourseId}>
    <option value="">Choisir classe de course</option>
    {#each classesCourse as c}
      <option value={c.id}>{c.nomClasseCourse}</option>
    {/each}
  </select>
  <input placeholder="Nombre courses" bind:value={nombre} />
  <input placeholder="Nombre à compter" bind:value={aCompter} />
  <button type="submit">Créer</button>
</form>

<table>
  <tr><th>Nom</th><th>Classe</th><th>Nb courses</th><th>Nb à compter</th></tr>
  {#each series as s}
    <tr>
      <td>{s.nomSerie}</td>
      <td>{s.classeCourse?.nomClasseCourse}</td>
      <td>{s.nombreCourses}</td>
      <td>{s.nombreCoursesACompter}</td>
    </tr>
  {/each}
</table>
