<script setup lang="ts">
import type { IUser } from "~/types/user";

const props = defineProps<{
  user: IUser;
}>();
</script>
<template>
    <div>
      <div>
        <img :src="user.avatar" alt="Profil utilisateur"/>
      </div>
      <div>
        <label> Pseudo du profil </label>
        <p> {{ user.username }}</p>
      </div>
      <div>
        <label> Email </label>
        <p> {{ user.email }}</p>
      </div>
      <div>
        <label> Votre déscription </label>
        <p> {{  user.description }}</p>
      </div>
    </div>

</template>

<style scoped></style>
