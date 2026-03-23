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
        <input v-model="form.name" type="text" placeholder="Nom"/>
        <input v-model="form.description" type="text" placeholder="Description"/>
        <input v-model="form.category" type="text" placeholder="Catégorie"/>
    </form>
</template>

<style scoped>
</style>