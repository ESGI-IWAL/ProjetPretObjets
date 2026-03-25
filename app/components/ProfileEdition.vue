<script setup lang="ts">
import type { IModificateUser } from '~/pages/profile/index.vue';

    definePageMeta({
      middleware: 'auth'
    })
    const activeUser = useActiveUser()
    const form = reactive<IModificateUser>({
        description: activeUser.value?.description || "",
        avatar: activeUser.value?.avatar || "",
        loanActive: false,
        numberOfLoans: 0,
        email: activeUser.value?.email || ""
    })

    const emit = defineEmits<{
    handleSubmit: [value: IModificateUser],
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
    <div class="flex flex-col items-center gap-4 mt-4">
        <form>
            <div>

                <input v-model="form.loanActive" type="checkbox" />
                <input :disabled="!form.loanActive" v-model.number="form.numberOfLoans" type="number" placeholder="Nombre de prêts" />
                <input v-model.trim="form.email" type="email" placeholder="Email" />
                <input v-model="form.description" type="text" placeholder="Description" />
                <input v-model="form.avatar" type="text" placeholder="Avatar URL" />
            </div>
                <div class="flex gap-4 mt-4 justify-end">
                <button @click.prevent="handleSubmit">Submit</button>
                <button @click.prevent="desactivateEditMode">Cancel</button>
            </div>
        </form>
    </div>
</template>


<style scoped>

</style>