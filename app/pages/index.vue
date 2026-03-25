<script setup lang="ts">
    definePageMeta({
     middleware: 'auth'
    })
</script>

<template>
    <h1>Welcome to NuxtPretObjet</h1>
    <NuxtIsland name="MyIsland" :props="{ title: 'PageStatic' }" />
</template>

<style scoped>
</style>