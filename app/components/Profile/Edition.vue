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
   <div class="grid grid-cols-[95%_05%] mb-10 sm:mb-6">
    <h1 class="font-bold text-center sm:text-3xl md:text-5xl lg:text-6xl">Edit Profile</h1>
    <svg @click="desactivateEditMode" class="svg-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
    </svg>
</div>

<div class="flex flex-col items-center gap-4 mt-4">
    <form class="flex flex-1 flex-col gap-4 w-full">
        <div class="grid grid-rows-[auto_auto_auto_auto_auto] gap-4 w-full">

            <div class="grid grid-cols-2">
                <div>Prêts actifs :</div>
                <input v-model="form.loanActive" type="checkbox" class="w-fit" />
            </div>

            <div class="grid grid-cols-2">
                <div>Nombre de prêts :</div>
                <input
                    :disabled="!form.loanActive"
                    v-model.number="form.numberOfLoans"
                    type="number"
                    placeholder="Nombre de prêts"
                    class="col-span-1 bg-gray-500 p-2 rounded border disabled:opacity-50"
                />
            </div>

            <div class="grid grid-cols-2">
                <div>Email :</div>
                <input
                    v-model.trim="form.email"
                    type="email"
                    placeholder="Email"
                    class="col-span-1 bg-gray-500 p-2 rounded border"
                />
            </div>

            <div class="grid grid-cols-2">
                <div>Description :</div>
                <input
                    v-model="form.description"
                    type="text"
                    placeholder="Description"
                    class="col-span-1 max-h-40 overflow-y-auto bg-gray-500 p-2 rounded border"
                />
            </div>

            <div class="grid grid-cols-2">
                <div>Avatar URL :</div>
                <input
                    v-model="form.avatar"
                    type="text"
                    placeholder="Avatar URL"
                    class="col-span-1 bg-gray-500 p-2 rounded border"
                />
            </div>

        </div>

        <div class="flex gap-4 mt-4 justify-end">
            <button @click.prevent="desactivateEditMode" class="bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded">Cancel</button>
            <button @click.prevent="handleSubmit" class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded">Submit</button>
        </div>
    </form>
</div>
</template>


<style scoped>

</style>