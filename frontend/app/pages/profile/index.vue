<script setup lang="ts">
import { getCurrentUser } from "~/services/user";
import type { IUser } from "~/types/user";

const currentUser = ref<IUser | null>(null);
const toaster = useToaster();

onMounted(async () => {
  try {
    currentUser.value = await getCurrentUser();
  } catch {
    toaster.show(
      "Problème lors de la récupération de l'utilisateur connecté",
      "error",
    );
  }
});
</script>
<template>
  <div v-if="!currentUser">Chargement ...</div>
  <div v-else>
    <ProfileContainer :user="currentUser" />
  </div>
</template>

<style></style>
