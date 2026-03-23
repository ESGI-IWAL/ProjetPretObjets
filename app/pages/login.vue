<script setup lang="ts">
const { login } = useAuth()
  const form = reactive({
    pseudo:'',
    password:''
  })

  const handleSubmit = () => {
    login(form.pseudo, form.password)
  }
</script>

<template>
  <form>
    <input v-model='form.pseudo' placeholder="Pseudo">
    <input v-model='form.password' placeholder="Mot de Passe" />
    <button type="submit" @onSubmit="handleSubmit"> Login </button>
   </form>
</template>


<style>

</style>