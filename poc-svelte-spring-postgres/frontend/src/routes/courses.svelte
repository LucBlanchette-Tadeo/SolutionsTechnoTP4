<script lang="ts">
  import { onMount } from 'svelte';
  import { listCourses, createCourse, listSeries, listClasseCourse } from '$lib/api';

  let courses: any[] = [];
  let series: any[] = [];
  let classesCourse: any[] = [];

  let serieId = '';
  let classeCourseId = '';
  let date = '';
  let heure = '';
  let nom = '';
  let parcours = '';

  async function load() {
    courses = await listCourses();
    series = await listSeries();
    classesCourse = await listClasseCourse();
  }

  async function add() {
    await createCourse({ serie: serieId ? { id: parseInt(serieId) } : null, classeCourse: { id: parseInt(classeCourseId||'0') }, dateCourse: date || null, heureDepart: heure, nomCourse: nom, parcours: parcours });
    serieId=''; classeCourseId=''; date=''; heure=''; nom=''; parcours='';
    await load();
  }

  onMount(load);
</script>

<h1>Courses</h1>
<form on:submit|preventDefault={add}>
  <select bind:value={serieId}>
    <option value="">Choisir série (optionnel)</option>
    {#each series as s}
      <option value={s.id}>{s.nomSerie}</option>
    {/each}
  </select>
  <select bind:value={classeCourseId}>
    <option value="">Choisir classe de course</option>
    {#each classesCourse as c}
      <option value={c.id}>{c.nomClasseCourse}</option>
    {/each}
  </select>
  <input type="date" bind:value={date} />
  <input type="time" bind:value={heure} />
  <input placeholder="Nom course" bind:value={nom} />
  <input placeholder="Parcours" bind:value={parcours} />
  <button type="submit">Créer</button>
</form>

<table>
  <tr><th>Nom</th><th>Série</th><th>Classe</th><th>Date</th><th>Heure</th></tr>
  {#each courses as c}
    <tr>
      <td>{c.nomCourse}</td>
      <td>{c.serie?.nomSerie}</td>
      <td>{c.classeCourse?.nomClasseCourse}</td>
      <td>{c.dateCourse}</td>
      <td>{c.heureDepart}</td>
    </tr>
  {/each}
</table>
