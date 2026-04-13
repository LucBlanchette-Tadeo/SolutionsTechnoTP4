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