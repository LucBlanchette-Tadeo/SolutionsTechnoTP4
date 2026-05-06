const BASE = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api';

// Classe bateau
export async function listClasseBateau() {
  const res = await fetch(`${BASE}/classe-bateau`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createClasseBateau(payload: any) {
  const res = await fetch(`${BASE}/classe-bateau`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Bateaux
export async function listBateaux() {
  const res = await fetch(`${BASE}/bateaux`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createBateau(payload: any) {
  const res = await fetch(`${BASE}/bateaux`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Series
export async function listSeries() {
  const res = await fetch(`${BASE}/series`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createSerie(payload: any) {
  const res = await fetch(`${BASE}/series`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Courses (mise à jour : utiliser /courses pour correspondre au backend)
export async function listCourses() {
  const res = await fetch(`${BASE}/courses`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createCourse(payload: any) {
  const res = await fetch(`${BASE}/courses`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Inscriptions
export async function listInscriptionsCourse() {
  const res = await fetch(`${BASE}/inscriptions-course`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createInscriptionCourse(payload: any) {
  const res = await fetch(`${BASE}/inscriptions-course`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

export async function createInscriptionSerie(payload: any) {
  const res = await fetch(`${BASE}/inscriptions-serie`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Classe course
export async function listClasseCourse() {
  const res = await fetch(`${BASE}/classe-course`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}
export async function createClasseCourse(payload: any) {
  const res = await fetch(`${BASE}/classe-course`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

// Inscriptions aux courses
export async function listInscriptionsByCourse(classeCourseId: number) {
  const res = await fetch(`${BASE}/inscriptions/course/${classeCourseId}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function getAvailableBateaux(classeCourseId: number) {
  const res = await fetch(`${BASE}/inscriptions/disponibles/${classeCourseId}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function addBateauToCourse(bateauId: number, classeCourseId: number) {
  const res = await fetch(`${BASE}/inscriptions`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ bateauId, classeCourseId })
  });
  if (!res.ok) {
    const error = await res.json();
    throw new Error(error.error || `POST failed: ${res.status}`);
  }
  return res.json();
}

export async function removeBateauFromCourse(inscriptionId: number) {
  const res = await fetch(`${BASE}/inscriptions/${inscriptionId}`, {
    method: 'DELETE'
  });
  if (!res.ok) throw new Error(`DELETE failed: ${res.status}`);
  return res.json();
}

export async function updateClassement(inscriptionId: number, classement: number | null) {
  const res = await fetch(`${BASE}/inscriptions/${inscriptionId}/classement`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ classement })
  });
  if (!res.ok) throw new Error(`PATCH failed: ${res.status}`);
  return res.json();
}

// Serie Classe Course
export async function listSerieClasseCourse() {
  const res = await fetch(`${BASE}/serie-classe-course`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function getSerieClasseCourseBySerieId(serieId: number) {
  const res = await fetch(`${BASE}/serie-classe-course/serie/${serieId}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function createSerieClasseCourse(payload: any) {
  const res = await fetch(`${BASE}/serie-classe-course`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

export async function deleteSerieClasseCourse(id: number) {
  const res = await fetch(`${BASE}/serie-classe-course/${id}`, {
    method: 'DELETE'
  });
  if (!res.ok) throw new Error(`DELETE failed: ${res.status}`);
}

// Leaderboard
export async function getLeaderboardByCourse(classeCourseId: number) {
  const res = await fetch(`${BASE}/leaderboard/course/${classeCourseId}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function createLeaderboardEntry(payload: any) {
  const res = await fetch(`${BASE}/leaderboard`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`POST failed: ${res.status}`);
  return res.json();
}

export async function updateLeaderboardEntry(id: number, payload: any) {
  const res = await fetch(`${BASE}/leaderboard/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error(`PUT failed: ${res.status}`);
  return res.json();
}

export async function deleteLeaderboardEntry(id: number) {
  const res = await fetch(`${BASE}/leaderboard/${id}`, {
    method: 'DELETE'
  });
  if (!res.ok) throw new Error(`DELETE failed: ${res.status}`);
}

// Race Results avec Handicaps (PY/TMF)
export async function submitRaceResult(classeCourseId: number, bateauId: number, 
                                      tempsBrutSecondes: number, typeHandicap: string) {
  const res = await fetch(`${BASE}/race-results/submit`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ classeCourseId, bateauId, tempsBrutSecondes, typeHandicap })
  });
  if (!res.ok) {
    const error = await res.json();
    throw new Error(error.error || `POST failed: ${res.status}`);
  }
  return res.json();
}

export async function getRaceResults(classeCourseId: number) {
  const res = await fetch(`${BASE}/race-results/course/${classeCourseId}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function getRaceResult(id: number) {
  const res = await fetch(`${BASE}/race-results/${id}`);
  if (!res.ok) throw new Error(`GET failed: ${res.status}`);
  return res.json();
}

export async function deleteRaceResult(id: number) {
  const res = await fetch(`${BASE}/race-results/${id}`, {
    method: 'DELETE'
  });
  if (!res.ok) throw new Error(`DELETE failed: ${res.status}`);
  return res.json();
}