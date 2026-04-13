<script lang="ts">
  import { goto } from '$app/navigation';
</script>

<div class="hero">
  <div class="hero-content">
    <h1>⛵ Gestion des Bateaux de Voile</h1>
    <p class="subtitle">Organisez et gérez vos bateaux, leurs classes et leurs courses</p>
    
    <div class="features">
      <div class="feature">
        <h3>🚤 Bateaux</h3>
        <p>Gérez votre flotte de bateaux avec détails complets</p>
        <button on:click={() => goto('/bateaux')}>Accéder</button>
      </div>

      <div class="feature">
        <h3>📋 Classes</h3>
        <p>Organisez vos bateaux par classes</p>
        <button on:click={() => goto('/classes')}>Accéder</button>
      </div>

      <div class="feature">
        <h3>🏆 Courses</h3>
        <p>Gérez vos séries de courses</p>
        <button on:click={() => goto('/courses')}>Accéder</button>
      </div>

      <div class="feature">
        <h3>📊 Séries</h3>
        <p>Suivez les différentes séries de votre club</p>
        <button on:click={() => goto('/series')}>Accéder</button>
      </div>
    </div>
  </div>
</div>

<style>
  .hero {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
  }

  .hero-content {
    text-align: center;
    color: white;
    max-width: 1000px;
  }

  h1 {
    font-size: 3.5em;
    margin-bottom: 10px;
    font-weight: 700;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  }

  .subtitle {
    font-size: 1.3em;
    margin-bottom: 50px;
    opacity: 0.95;
  }

  .features {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 30px;
    margin-top: 50px;
  }

  .feature {
    background: rgba(255, 255, 255, 0.15);
    backdrop-filter: blur(10px);
    padding: 30px;
    border-radius: 15px;
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s ease;
  }

  .feature:hover {
    background: rgba(255, 255, 255, 0.25);
    transform: translateY(-10px);
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  }

  .feature h3 {
    font-size: 1.8em;
    margin-bottom: 15px;
  }

  .feature p {
    font-size: 1.1em;
    margin-bottom: 20px;
    opacity: 0.9;
  }

  button {
    background-color: white;
    color: #667eea;
    border: none;
    padding: 12px 30px;
    border-radius: 8px;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  button:hover {
    background-color: #f0f0f0;
    transform: scale(1.05);
  }

  button:active {
    transform: scale(0.95);
  }
</style>
