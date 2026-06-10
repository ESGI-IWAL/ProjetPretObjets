<script setup lang="ts">
import { getCurrentUser } from '~/services/user';
import type { IUser } from '~/types/user';

const currentUser = ref<IUser|null>(null)


onMounted( async () => {
    currentUser.value = await getCurrentUser();
})
</script>
<template>
    <ProfileContainer :user="currentUser"/>
</template>


<style>

</style>    