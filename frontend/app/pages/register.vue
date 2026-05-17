<script setup lang="ts">
  import type { ICreateUserDto } from '~/dto/user/create.dto';

  const { register, erreurConnexion } = useAuth()

  const form = reactive<ICreateUserDto>({
    email:'',
    password:'',
    userInfo:{
      pseudo:'',
      firstName:'',
      lastName:'',
    }
  })

  const passwordConfirm = ref('')

  const validateForm = () => {
    if (!form.userInfo.pseudo || !form.userInfo.firstName || !form.userInfo.lastName || !form.email || !form.password) {
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
        userInfo: form.userInfo,
        email: form.email,
        password: form.password
      });
    }
  }
</script>

<template>
    <form>
        <input v-model="form.userInfo.pseudo" type="text" placeholder="Pseudo" required>
        <input v-model="form.userInfo.firstName" type="text" placeholder="Prénom" required>
        <input v-model="form.userInfo.lastName" type="text" placeholder="Nom" required>
        <input v-model="form.email" type="email" placeholder="Email" required>  
        <input v-model="form.password" type="password" placeholder="Mot de passe" required>
        <input v-model="passwordConfirm" type="password" placeholder="Confirmer le mot de passe" required>

            <p v-if="erreurConnexion" class="text-red-500 text-sm text-center">{{ erreurConnexion }}</p>
        <button @click.prevent="handleSubmit">S'inscrire</button>
    </form>
</template>


<style>

</style>