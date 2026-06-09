<script setup lang="ts">
  import type { ICreateUserDto } from '~/dto/user/create.dto';
  definePageMeta({
    layout: false,
  });

  const { erreurConnexion, register } = useAuth()

  const form = reactive<ICreateUserDto>({
    email:'',
    password:'',
      username:'',
  })

  const passwordConfirm = ref('')

  const validateForm = () => {
    if (!form.username || !form.email || !form.password) {
      erreurConnexion.value = "Tous les champs sont requis.";
      return false;
    }
    if (form.password.length < 6) {
      erreurConnexion.value = "Le mot de passe doit contenir au moins 6 caractères.";
      return false;
    }
    if (form.password !== passwordConfirm.value) {
      erreurConnexion.value = "Les mots de passe ne correspondent pas.";
      return false;
    }
    return true;
  }

  const handleSubmit = async () => {
    if (validateForm()) {
      await register({
        username: form.username,
        email: form.email,
        password: form.password
      });
    }
  }
</script>

<template>
  <div class="register-page">
    <div class="register-card">

      <!-- En-tête -->
      <div class="register-header">
        <div class="register-logo">✦</div>
        <h1 class="register-title">Créer un compte</h1>
        <p class="register-subtitle">Rejoignez la plateforme de gestion des prêts</p>
      </div>

      <!-- Formulaire -->
      <form class="register-form" @submit.prevent="handleSubmit">

        <div class="register-field">
          <label class="register-label" for="username">Pseudo</label>
          <input
            id="username"
            v-model="form.username"
            type="text"
            placeholder="Votre pseudo"
            class="register-input"
            required
          />
        </div>

        <div class="register-field">
          <label class="register-label" for="email">Adresse email</label>
          <input
            id="email"
            v-model="form.email"
            type="email"
            placeholder="votre@email.com"
            class="register-input"
            required
          />
        </div>

        <div class="register-field">
          <label class="register-label" for="password">Mot de passe</label>
          <input
            id="password"
            v-model="form.password"
            type="password"
            placeholder="6 caractères minimum"
            class="register-input"
            required
          />
        </div>

        <div class="register-field">
          <label class="register-label" for="passwordConfirm">Confirmer le mot de passe</label>
          <input
            id="passwordConfirm"
            v-model="passwordConfirm"
            type="password"
            placeholder="Répétez votre mot de passe"
            class="register-input"
            required
          />
        </div>

        <!-- Erreur -->
        <transition name="fade">
          <div v-if="erreurConnexion" class="register-error">
            <span class="register-error-icon">!</span>
            {{ erreurConnexion }}
          </div>
        </transition>

        <button type="submit" class="register-btn">
          S'inscrire
        </button>

        <p class="register-login">
          Déjà un compte ?
          <a href="/login" class="register-login-link">Se connecter</a>
        </p>

      </form>
    </div>
  </div>
</template>


<style scoped>
/* ── Page ── */
.register-page {
  min-height: 100vh;
  background-color: var(--color-background);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

/* ── Carte ── */
.register-card {
  width: 100%;
  max-width: 420px;
  background-color: var(--color-surface);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  padding: 2.5rem 2rem;
  border: 1px solid #d4ccc0;
}

/* ── En-tête ── */
.register-header {
  text-align: center;
  margin-bottom: 2rem;
}

.register-logo {
  font-size: 1.75rem;
  color: var(--color-primary);
  margin-bottom: 0.75rem;
  line-height: 1;
}

.register-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-title);
  margin: 0 0 0.4rem;
  letter-spacing: -0.02em;
}

.register-subtitle {
  font-size: 0.875rem;
  color: var(--color-text);
  opacity: 0.65;
  margin: 0;
}

/* ── Formulaire ── */
.register-form {
  display: flex;
  flex-direction: column;
  gap: 1.1rem;
}

.register-field {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

.register-label {
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--color-title);
  letter-spacing: 0.01em;
}

.register-input {
  width: 100%;
  box-sizing: border-box;
  background-color: var(--color-background);
  color: var(--color-text);
  border: 1px solid #c9c0ae;
  border-radius: 10px;
  padding: 11px 14px;
  font-size: 0.875rem;
  font-family: var(--font-family-sans);
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.register-input::placeholder {
  color: var(--color-text);
  opacity: 0.4;
}

.register-input:focus {
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(98, 148, 96, 0.18);
}

/* ── Erreur ── */
.register-error {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background-color: #f5e6df;
  border: 1px solid #c47a5a;
  color: #7a3a1e;
  border-radius: 10px;
  padding: 10px 14px;
  font-size: 0.825rem;
  font-weight: 500;
}

.register-error-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background-color: #c47a5a;
  color: white;
  font-size: 0.7rem;
  font-weight: 700;
  flex-shrink: 0;
}

/* ── Bouton ── */
.register-btn {
  width: 100%;
  background-color: var(--color-primary);
  color: var(--color-background);
  border: none;
  border-radius: 10px;
  padding: 12px;
  font-size: 0.925rem;
  font-weight: 600;
  font-family: var(--font-family-sans);
  cursor: pointer;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  margin-top: 0.25rem;
}

.register-btn:hover {
  background-color: var(--color-secondary);
  box-shadow: 0 4px 12px rgba(48, 84, 48, 0.2);
}

.register-btn:active {
  transform: translateY(1px);
}

/* ── Lien login ── */
.register-login {
  text-align: center;
  font-size: 0.825rem;
  color: var(--color-text);
  opacity: 0.7;
  margin: 0;
}

.register-login-link {
  color: var(--color-primary);
  font-weight: 600;
  text-decoration: none;
  transition: color 0.2s ease;
}

.register-login-link:hover {
  color: var(--color-secondary);
  text-decoration: underline;
}

/* ── Transition erreur ── */
.fade-enter-active, .fade-leave-active { transition: opacity 0.2s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>