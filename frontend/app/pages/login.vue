<script setup lang="ts">
const { login, erreurConnexion } = useAuth()
  const form = reactive({
    pseudo:'',
    password:''
  })

  const handleSubmit = async () => {
    await login(form.pseudo, form.password)
  }
</script>

<template>                                                         
</template>


<style scoped>

</style>