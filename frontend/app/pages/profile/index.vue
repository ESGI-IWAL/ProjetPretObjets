<script setup lang="ts">
import { getCurrentUser } from '~/services/user';
import type { IUser } from '~/types/user';

const currentUser = ref<IUser|null>(null)
const toaster = useToaster()

onMounted( async () => {
  try{
    currentUser.value = await getCurrentUser();
  }
  catch {
    toaster.show("Erreur de la récupération de votre profil", "error")
  }
})
</script>
<template>
  <div v-if="!currentUser">
    Chargement de l'utilisateur ...
  </div>
  <div v-else > 

    <ProfileContainer :user="currentUser"/>
  </div>
</template>


<style>

</style>    