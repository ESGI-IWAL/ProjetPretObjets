<script setup lang="ts">
const { login, erreurConnexion } = useAuth()
  const form = reactive({
    pseudo:'',
    password:''
  })

  const handleSubmit = async () => {
    await login(form.pseudo, form.password)
    console.log(erreurConnexion.value);
  }
</script>

<template>
  <div class="flex flex-col items-center justify-center min-h-screen sm:w-50 md:w-1/3 lg:w-1/4 mx-auto">
    <form @submit.prevent="handleSubmit" class="flex flex-col gap-4 bg-gray-400 p-6 rounded-lg">
      <h1 class="text-xl font-bold text-center" >
        Connexion
      </h1>
      <div class="flex flex-col gap-2">
        <label for="username" >Username</label>
        <input
            id="username"
            v-model="form.pseudo"
            type="text"
            required
            :class="Boolean(erreurConnexion) ? 'error-input' : 'input'"
        />
      </div>

      <div class="flex flex-col gap-2">
        <label for="password">Mot de passe</label>
        <input
            id="password"
            v-model="form.password"
            type="password"
            required
            :class="Boolean(erreurConnexion) ? 'error-input' : 'input'"

        />
      </div>

      <button type="submit">
        {{ 'Se connecter' }}
      </button>
    </form>
  </div>
</template>


<style scoped>

</style>