<script lang="ts">
  import { onMount } from 'svelte';
  import { listInscriptionsCourse, createInscriptionCourse, listCourses, listBateaux, createInscriptionSerie, listSeries } from '$lib/api';

  let inscriptions: any[] = [];
  let courses: any[] = [];
  let bateaux: any[] = [];
  let series: any[] = [];

  let courseId = '';
  let bateauId = '';

  async function load() {
    inscriptions = await listInscriptionsCourse();
    courses = await listCourses();
    bateaux = await listBateaux();
    series = await listSeries();
  }

  async function add() {
    await createInscriptionCourse({ course: { id: parseInt(courseId||'0') }, bateau: { id: parseInt(bateauId||'0') } });
    courseId=''; bateauId='';
    await load();
  }

  async function addSerie(sid: string, bid: string) {
    await createInscriptionSerie({ serie: { id: parseInt(sid) }, bateau: { id: parseInt(bid) } });
    await load();
  }

  onMount(load);
</script>

<h1>Inscriptions</h1>
<form on:submit|preventDefault={add}>
  <select bind:value={courseId}>
    <option value="">Choisir course</option>
    {#each courses as c}
      <option value={c.id}>{c.nomCourse} - {c.dateCourse}</option>
    {/each}
  </select>
  <select bind:value={bateauId}>
    <option value="">Choisir bateau</option>
    {#each bateaux as b}
      <option value={b.id}>{b.nomBateau} ({b.numeroVoile})</option>
    {/each}
  </select>
  <button type="submit">Inscrire à la course</button>
</form>

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
