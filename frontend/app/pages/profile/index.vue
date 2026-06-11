<script setup lang="ts">
import type { IUpdateUserDto } from '~/dto/user/update.dto';
import { getCurrentUser, updateUser } from '~/services/user';
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

const handleSubmit = async(dto : IUpdateUserDto) => {
  try {
    await updateUser(dto)
    toaster.show("Votre profil a bien été modifié", "success")
  }
  catch{
    toaster.show("Erreur lors de la modification de votre profil", "error")

  }
}
</script>
<template>
  <div v-if="!currentUser">
    Chargement de l'utilisateur ...
  </div>
  <div v-else > 

    <ProfileContainer :user="currentUser" @handle-submit-update="handleSubmit"/>
  </div>
</template>


<style>

</style>    