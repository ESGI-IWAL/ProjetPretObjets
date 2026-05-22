<script setup lang="ts">
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
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-8 space-y-6 bg-white rounded shadow">
      <h2 class="text-2xl font-bold text-center">Connexion</h2>
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <input v-model="form.email" id="email" type="email" required class="w-full px-3 py-2 border rounded focus:outline-none focus:ring focus:border-blue-300">
        </div>
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Mot de passe</label>
          <input v-model="form.password" id="password" type="password" required class="w-full px-3 py-2 border rounded focus:outline-none focus:ring focus:border-blue-300">
        </div>
        <button type="submit" class="w-full py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-200">Se connecter</button>
      </form>
      <NuxtLink to="/register"> Pas encore de compte ? Inscrivez-vous </NuxtLink>
      <p v-if="erreurConnexion" class="text-red-500 text-sm text-center">{{ erreurConnexion }}</p>     
    </div>
  </div>                                  
</template>


<style scoped>

</style>