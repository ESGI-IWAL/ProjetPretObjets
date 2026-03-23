<script setup lang="ts">
import type { ModificateUser } from '~/pages/profile/index.vue';

    definePageMeta({
      middleware: 'auth'
    })
    const activeUser = useActiveUser()
    const form = reactive<ModificateUser>({
        description: activeUser.value?.description || "",
        avatar: activeUser.value?.avatar || "",
        loanActive: false,
        numberOfLoans: 0,
        email: activeUser.value?.email || ""
    })

    const emit = defineEmits<{
    handleSubmit: [value: ModificateUser],
    desactivateEditMode: []
    }>()

    const handleSubmit = () => { 
        emit("handleSubmit", { description: form.description, avatar: form.avatar, loanActive: form.loanActive, numberOfLoans: form.numberOfLoans, email: form.email }) 
        emit("desactivateEditMode")
    } 

    const desactivateEditMode = () => {
        emit("desactivateEditMode")
    }

</script>
<template>
    <img src="\utils\close.png" alt="ModifierPen" @click="desactivateEditMode"/>
    <form>
        <input v-model="form.loanActive" type="checkbox" />
        <input :disabled="!form.loanActive" v-model.number="form.numberOfLoans" type="number" placeholder="Nombre de prêts" />
        <input v-model.trim="form.email" type="email" placeholder="Email" />
        <input v-model="form.description" type="text" placeholder="Description" />
        <input v-model="form.avatar" type="text" placeholder="Avatar URL" />
        <button @click.prevent="handleSubmit">Submit</button>
    </form>
</template>


<style scoped>

</style>