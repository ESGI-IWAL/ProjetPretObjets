<script setup lang="ts">
import type { IUser } from '~/types/user';

    const props = defineProps<{
        user: IUser | null
    }>()

    const emit = defineEmits<{
        activateEditMode: []
    }>()

    const activateEditMode = () => {
        emit("activateEditMode")
    }
</script>
<template>
    <div>
        <div class="grid grid-cols-[95%_05%]">
            <h1 class="text-2xl font-bold text-center">Profile</h1>
            <img src="\utils\pencil.png" alt="ModifierPen" @click="activateEditMode" class="cursor-pointer w-5 lg:w-6"/>
        </div>
        <div v-if="props.user" class="grid grid-rows-[40%-60%]"> 
            <NuxtPicture :src="props.user.avatar" alt="User Avatar" class="avatar"/>
            <div>
                <div> {{props.user.pseudo}}</div>
                <div> {{props.user.email}}</div>
                <div> {{props.user.description}}</div>
            </div>
        </div>
        <div v-else>
            <p>No user data available.</p>
        </div>
    </div>
</template>


<style scoped>

</style>