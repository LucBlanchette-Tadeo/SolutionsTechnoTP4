<script lang="ts">
  export let onLoginSuccess: (username: string) => void;

  let username = '';
  let password = '';
  let error = '';
  let loading = false;

  async function handleLogin() {
    error = '';
    loading = true;

    try {
      const res = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      const data = await res.json();

      if (data.success) {
        localStorage.setItem('username', username);
        localStorage.setItem('authenticated', 'true');
        onLoginSuccess(username);
      } else {
        error = data.message || 'Erreur lors de la connexion';
      }
    } catch (e: any) {
      error = 'Erreur de connexion: ' + e.message;
    } finally {
      loading = false;
    }
  }
</script>

<div class="login-container">
  <div class="login-box">
    <h1>Connexion</h1>
    
    {#if error}
      <div class="error-message">
        {error}
      </div>
    {/if}

    <form on:submit|preventDefault={handleLogin}>
      <div class="form-group">
        <label for="username">Nom d'utilisateur</label>
        <input
          id="username"
          type="text"
          bind:value={username}
          placeholder="Entrez votre nom d'utilisateur"
          disabled={loading}
          required
        />
      </div>

      <div class="form-group">
        <label for="password">Mot de passe</label>
        <input
          id="password"
          type="password"
          bind:value={password}
          placeholder="Entrez votre mot de passe"
          disabled={loading}
          required
        />
      </div>

      <button type="submit" disabled={loading}>
        {loading ? 'Connexion en cours...' : 'Se connecter'}
      </button>
    </form>

    <div class="demo-info">
      <p><strong>Données de test:</strong></p>
      <p>Utilisateur: <code>admin</code></p>
      <p>Mot de passe: <code>Admin123!</code></p>
    </div>
  </div>
</div>

<style>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  }

  .login-box {
    background: white;
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
    width: 100%;
    max-width: 400px;
  }

  h1 {
    text-align: center;
    color: #333;
    margin-bottom: 2rem;
    font-size: 1.8rem;
  }

  .error-message {
    background-color: #fee;
    color: #c00;
    padding: 0.75rem;
    border-radius: 5px;
    margin-bottom: 1rem;
    border-left: 4px solid #c00;
  }

  .form-group {
    margin-bottom: 1.5rem;
  }

  label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 600;
    color: #333;
  }

  input {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
    transition: border-color 0.3s;
    box-sizing: border-box;
  }

  input:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  }

  input:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
  }

  button {
    width: 100%;
    padding: 0.75rem;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s, box-shadow 0.2s;
  }

  button:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
  }

  button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }

  .demo-info {
    margin-top: 2rem;
    padding-top: 1.5rem;
    border-top: 1px solid #eee;
    font-size: 0.85rem;
    color: #666;
  }

  .demo-info p {
    margin: 0.25rem 0;
  }

  code {
    background-color: #f5f5f5;
    padding: 0.2rem 0.4rem;
    border-radius: 3px;
    font-family: monospace;
  }
</style>
