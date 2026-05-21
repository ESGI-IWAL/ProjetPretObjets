<script setup lang="ts">
import type { ILending } from '~/types/lending';


    defineProps<{
        lending: ILending
    }>()
</script>
<template>
    <div>
        <h1 class="text-2xl font-bold mb-4">Prêt {{ lending.status }}</h1>
        <div>
            <div>
                <h2 class="text-xl font-semibold">Emprunteur</h2>
                <div @click="() => navigateTo(`/users/${lending.borrower.id}`)">
                    <img :src="lending.borrower.avatar" alt="Avatar de l'emprunteur" class="w-16 h-16 rounded-full" />
                    <p>{{ lending.borrower.userName }}</p>
                </div>
            </div>
            <div>
                <h2 class="text-xl font-semibold">Objet prêté</h2>
                <div @click="() => navigateTo(`/objects/${lending.object.id}`)">
                    <img :src="lending.object.images[0]" alt="Objet prêté" class="w-32 h-32 object-cover" />
                    <p>{{ lending.object.name }}</p>
                </div>
            </div>
            <div>
                <h2 class="text-xl font-semibold">Dates</h2>
                <p>Début : {{ lending.startAt }}</p>
                <p>Fin : {{ lending.endAt }}</p>
            </div>
        </div>
    </div>

</template>


<style>

</style>