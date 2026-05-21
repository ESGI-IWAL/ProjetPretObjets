<script setup lang="ts">
import type { ILending } from '~/types/lending';
import { getLendingStatusLabel } from '~/enums/lending/status.enum';


    defineProps<{
        lending: ILending
    }>()


</script>
<template>
    <div>
        <h1 class="text-2xl font-bold mb-4">Prêt {{ getLendingStatusLabel(lending.status).toLowerCase() }}</h1>

        <div>   
            <div>
                <h2 class="text-xl font-semibold">Emprunteur</h2>
                <div @click="() => navigateTo(`/users/${lending.borrowedBy.id}`)">
                    <img :src="lending?.borrowedBy.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'" alt="Avatar de l'emprunteur" class="w-16 h-16 rounded-full" />
                    <p>{{ lending.borrowedBy.username }}</p>
                </div>
            </div>
            <div>
                <h2 class="text-xl font-semibold">Objet prêté</h2>
                <div @click="() => navigateTo(`/objects/${lending.object.id}`)">
                    <img v-if="lending.object.images?.length" :src="lending.object.images[0]" alt="Objet prêté" class="w-32 h-32 object-cover" />
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