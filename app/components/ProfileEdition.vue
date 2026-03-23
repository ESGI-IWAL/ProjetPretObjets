<script setup lang="ts">
    definePageMeta({
      middleware: 'auth'
    })

    const form = reactive({
        description: "",
        avatar: ""
    })

    const emit = defineEmits<{
    handleSubmit: [value: { description: string | null, avatar: string | null }],
    desactivateEditMode: []
    }>()

    const handleSubmit = () => { 
        emit("handleSubmit", { description: form.description, avatar: form.avatar }) 
    } 

    const desactivateEditMode = () => {
        emit("desactivateEditMode")
    }

</script>
<template>
    <img src="\utils\close.png" alt="ModifierPen" @click="desactivateEditMode" class="size-sm"/>
    <form>
        <input v-model="form.description" type="text" placeholder="Description" />
        <input v-model="form.avatar" type="text" placeholder="Avatar URL" />
        <button @click.prevent="handleSubmit">Submit</button>
    </form>
</template>


<style>

</style>