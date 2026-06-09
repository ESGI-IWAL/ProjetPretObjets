<script setup lang="ts">
definePageMeta({
  layout: false,
});
const { login, erreurConnexion } = useAuth()
  const form = reactive({
    email:'',
    password:''
  })

  const handleSubmit = async () => {
    await login(form.email, form.password)
  }
</script>

<template>
  <div class="login-page">
    <div class="login-card">
      <h2 class="login-title">Connexion</h2>
      <form @submit.prevent="handleSubmit" class="login-form">
        <div class="form-field">
          <label for="email" class="form-label">Email</label>
          <input v-model="form.email" id="email" type="email" required class="form-input">
        </div>
        <div class="form-field">
          <label for="password" class="form-label">Mot de passe</label>
          <input v-model="form.password" id="password" type="password" required class="form-input">
        </div>
        <button type="submit" class="btn btn-primary w-full">Se connecter</button>
      </form>
      <NuxtLink to="/register" class="register-link">Pas encore de compte ? Inscrivez-vous</NuxtLink>
      <p v-if="erreurConnexion" class="error-message">{{ erreurConnexion }}</p>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: var(--color-background);
}

.login-card {
  width: 100%;
  max-width: 28rem; /* 448px */
  padding: 2rem;
  background-color: var(--color-surface);
  border-radius: var(--border-radius);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

.login-title {
  font-size: 1.875rem; /* 30px */
  font-weight: 700;
  text-align: center;
  color: var(--color-title);
  margin-bottom: 1.5rem;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-field {
  display: flex;
  flex-direction: column;
}

.form-label {
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: var(--color-text);
}

.form-input {
  padding: 0.75rem 1rem;
  border: 1px solid #D1D5DB; /* gray-300 */
  border-radius: 8px;
  background-color: var(--color-background);
  color: var(--color-text);
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(52, 78, 65, 0.2);
}

.w-full {
  width: 100%;
}

.register-link {
  display: block;
  margin-top: 1rem;
  text-align: center;
  color: var(--color-primary);
  text-decoration: none;
  transition: color 0.3s ease;
}

.register-link:hover {
  text-decoration: underline;
  color: var(--color-accent);
}

.error-message {
  margin-top: 1rem;
  color: #EF4444; /* red-500 */
  font-size: 0.875rem; /* 14px */
  text-align: center;
}
</style>