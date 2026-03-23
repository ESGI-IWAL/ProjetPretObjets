<script setup lang="ts">
const form = reactive({
    name: '',
    description: '',
    category: '',
    image: null
})
</script>
<template>
    <form>
        <div v-model="form.name" type="text" placeholder="Nom"/>
        <div v-model="form.description" type="text" placeholder="Description"/>
        <div v-model="form.category" type="text" placeholder="Catégorie"/>
        <input v-model="form.image" type="file" placeholder="Image"/>
    </form>
</template>

<style scoped>
</style>