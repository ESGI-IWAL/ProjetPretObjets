<script setup lang="ts">
import type { Object } from '@/types/object'

const activeObject = ref<Object | null>(null)

function selectObject(object: Object) {
  activeObject.value = object
  console.log('Objet sélectionné :', object)
}

function unselectObject() {
  activeObject.value = null
  console.log('Aucun Objet sélectionné')
}

const search = ref('')
const {data: objects, pending, error} = useFetch<Object[]>('/api/objects/object', {
  query: { search },
  watch: [search]
})
</script>

<template>
  <ClientOnly>
    <div v-if="pending">Chargement des objets...</div>

    <div v-else-if="error">Erreur de chargement</div>

    <input
        v-model="search"
        type="text"
        placeholder="Rechercher un objet..."
        class="mb-4 p-2 border border-gray-300 rounded w-full" >

    <div
        class="user-grid"
        >
        <ObjectCard
          tabindex="0"
          v-for="object in objects"
          :key="object.id"
          :object="object"
          :selected="activeObject?.id === object.id"
          @select="selectObject"
          @keyup.enter="selectObject"
          @keyup.esc="unselectObject"
      />
    </div>
    <section v-if="activeObject?.id" >
      <ObjectDetailledCard
          :object="activeObject"
          :selected="true"
          @select="selectObject"
      />
    </section>

  </ClientOnly>
</template>

<style scoped>
.user-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1.5rem;
}
</style>