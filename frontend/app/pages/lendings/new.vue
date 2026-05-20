<script setup lang="ts">
import { getObjects } from '~/services/object';
import { getUsers } from '~/services/user';

    const users = await getUsers()
    const objects = await getObjects()

</script>
<template>
    <div class="app-page">
        <div class="app-container">
            <LendingFormCreation :users="users" :objects="objects" />
        </div>
    </div>
</template>

<style scoped>
</style>