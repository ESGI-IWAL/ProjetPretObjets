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
        <h1>Profile</h1>
                <img src="\utils\pencil.png" alt="ModifierPen" @click="activateEditMode" class="icon"/>
        <div v-if="props.user"> 
            <NuxtPicture :src="props.user.avatar" alt="User Avatar" />
            <div> {{props.user.pseudo}}</div>
            <div> {{props.user.email}}</div>
            <div> {{props.user.description}}</div>
        </div>
        <div v-else>
            <p>No user data available.</p>
        </div>
    </div>
</template>


<style scoped>
    .icon {
        cursor: pointer;
        width: 20px;
        height: 20px;
    }
</style>