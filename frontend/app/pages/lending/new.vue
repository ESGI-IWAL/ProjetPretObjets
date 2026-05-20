<script setup lang="ts">
import { getObjects } from '~/services/object';
import { getUsers } from '~/services/user';

    const users = await getUsers()
    const objects = await getObjects()

</script>
<template>
    <LendingFormCreation :users="users" :objects="objects" />
</template>

<style scoped>
</style>