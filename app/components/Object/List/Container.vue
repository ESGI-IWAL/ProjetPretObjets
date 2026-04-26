<script setup lang="ts">
import type { Object } from '@/types/object'

const activeObject = ref<Object | null>(null)
const isDetailOpen = ref(false)
const search = ref('')

const { data: objects, pending, error } = useFetch<Object[]>('/api/objects/object', {
  query: { search },
  watch: [search]
})

function selectObject(object: Object) {
  activeObject.value = object
  console.log('Objet sélectionné :', object)
  isDetailOpen.value = true
  document.getElementById("object-detail").focus()
}

function unselectObject() {
  activeObject.value = null
  console.log('Aucun Objet sélectionné')
  isDetailOpen.value = false
}

function closeDetail() {
  isDetailOpen.value = false
}
</script>

<template>
  <div class="flex gap-6 p-4">

    <!-- LISTE -->
    <div class="flex-1">
      <div v-if="pending">Chargement des objets...</div>
      <div v-else-if="error">Erreur de chargement</div>

      <input
          v-model="search"
          type="text"
          placeholder="Rechercher un objet..."
          class="text-gray-500 mb-4"
      >

      <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3">
        <ObjectCard
            v-for="object in objects"
            :key="object.id"
            :object="object"
            :selected="activeObject?.id === object.id"
            @select="selectObject"
        />
      </div>
    </div>

    <!-- SIDEBAR -->
    <aside
        id="object-detail"
        tabindex="0"
        class="fixed right-0 top-0 z-50 h-screen w-full max-w-sm overflow-y-auto bg-gray-900 p-4 text-gray-100 shadow-2xl transition-transform duration-300"
        :class="isDetailOpen ? 'translate-x-0' : 'translate-x-full'"
        @keydown.esc="closeDetail"
    >
      <div class="mb-3 flex justify-end">
        <button class="rounded px-2 py-1 hover:bg-gray-700" @click="closeDetail">
          ✕
        </button>
      </div>

      <ObjectDetailledCard
          v-if="activeObject"
          :object="activeObject"
          :selected="true"
      />
    </aside>

  </div>
</template>