<script setup lang="ts">
const { login } = useAuth()
  const form = reactive({
    pseudo:'',
    password:''
  })

  const handleSubmit = async () => {
    await login(form.pseudo, form.password)
  }
</script>

<template>
  <div class="login-page">
    <form @submit.prevent="handleSubmit">
      <h1>Connexion</h1>


      <div class="field">
        <label for="username">username</label>
        <input
            id="username"
            v-model="form.pseudo"
            type="text"
            required
        />
      </div>

      <div class="field">
        <label for="password">Mot de passe</label>
        <input
            id="password"
            v-model="form.password"
            type="password"
            required
        />
      </div>

      <button type="submit">
        {{ 'Se connecter' }}
      </button>
    </form>
  </div>
</template>


<style>

</style>