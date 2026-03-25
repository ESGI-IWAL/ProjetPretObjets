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
        <div class="grid grid-cols-[95%_05%] mb-10 sm:mb-6">
            <h1 class="font-bold text-center sm:text-3xl md:text-5xl lg:text-6xl">Profile</h1>
            <svg @click="activateEditMode" class="svg-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
            </svg>
        </div>
        <div v-if="props.user" class="flex flex-col items-center gap-4 mt-4"> 
            <img :src="props.user.avatar" alt="User Avatar" class="card-avatar sm:w-24 sm:h-24 md:w-40 md:h-40 lg:w-50 lg:h-50"/>
            <div class="grid grid-rows-[auto_auto_auto] gap-4 w-full">

                <div class="grid grid-cols-2">
                    <div>
                        Pseudo : 
                    </div>
                    <div>{{props.user.pseudo}}</div>
                </div>
                <div class="grid grid-cols-2">
                    <div>
                        Email : 
                    </div>
                    <div> {{props.user.email}}</div>
                </div>
                <div class="grid grid-cols-2">
                    <div>
                        Description : 
                    </div>
                    <div class="col-span-1 max-h-40 overflow-y-auto bg-gray-500 p-2 rounded border">{{props.user.description}}</div>
                </div>
            </div>
        </div>
        <div v-else>
            <p>No user data available.</p>
        </div>
    </div>
</template>


<style scoped>

</style>