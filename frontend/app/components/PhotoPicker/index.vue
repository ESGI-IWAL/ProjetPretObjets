<template>
  <div class="photo-picker">
    <p class="label">Photos de l'objet</p>

    <div class="photo-grid">
      <!-- Photos ajoutées -->
      <div
        v-for="(photo, index) in photos"
        :key="index"
        class="photo-slot"
      >
        <img :src="photo" :alt="`Photo ${index + 1}`" />
        <button class="remove-btn" @click="removePhoto(index)" title="Supprimer">
          <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
            <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
          </svg>
        </button>
      </div>

      <!-- Bouton + pour ajouter -->
      <div
        v-if="photos.length < maxPhotos"
        class="add-slot"
        @click="openModal"
        role="button"
        tabindex="0"
        @keydown.enter="openModal"
        @keydown.space.prevent="openModal"
        aria-label="Ajouter une photo"
      >
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
          <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
        </svg>
        <span>Photo</span>
      </div>
    </div>

    <!-- Modal de sélection -->
    <Teleport to="body">
      <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
        <div class="modal-box">
          <p class="modal-title">Ajouter une photo</p>

          <!-- Onglets -->
          <div class="tab-bar">
            <button
              :class="['tab-btn', { active: activeTab === 'file' }]"
              @click="activeTab = 'file'"
            >
              Mes documents
            </button>
            <button
              :class="['tab-btn', { active: activeTab === 'url' }]"
              @click="activeTab = 'url'"
            >
              URL
            </button>
          </div>

          <!-- Onglet fichier -->
          <div v-if="activeTab === 'file'" class="pane">
            <label class="file-drop">
              <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#378ADD" stroke-width="1.5">
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                <polyline points="17 8 12 3 7 8"/>
                <line x1="12" y1="3" x2="12" y2="15"/>
              </svg>
              Cliquer pour choisir un fichier
              <input
                type="file"
                accept="image/*"
                @change="handleFileInput"
                style="display: none"
              />
            </label>
          </div>

          <!-- Onglet URL -->
          <div v-if="activeTab === 'url'" class="pane">
            <input
              v-model="urlInput"
              type="text"
              placeholder="https://exemple.com/image.jpg"
              class="url-input"
              @keydown.enter="handleUrl"
            />
            <div v-if="urlError" class="url-error">{{ urlError }}</div>
            <button class="confirm-btn" @click="handleUrl">Confirmer</button>
          </div>

          <button class="cancel-btn" @click="closeModal">Annuler</button>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { addObjectImage } from '~/services/object';

const toaster = useToaster()
const props = withDefaults(defineProps<{
  maxPhotos?: number
  modelValue?: string[]
  objectName:string
}>(), {
  maxPhotos: 8,
  modelValue: () => [],
  objectName: 'default'
})


const emit = defineEmits<{
  'update:modelValue': [photos: string[]]
}>()
const photos = ref<string[]>([...props.modelValue])
const showModal = ref(false)
const activeTab = ref('file')
const urlInput = ref('')
const urlError = ref('')

watch(() => props.modelValue, (val) => {
  photos.value = [...val]
})

function openModal() {
  showModal.value = true
  activeTab.value = 'file'
  urlInput.value = ''
  urlError.value = ''
}

function closeModal() {
  showModal.value = false
}

function removePhoto(index : number) {
  photos.value.splice(index, 1)
  emit('update:modelValue', [...photos.value])
}

function addPhoto(src: string) {
  photos.value.push(src)
  emit('update:modelValue', [...photos.value])
  closeModal()
}

async function handleFileInput(event: Event) {
  try {

    const file = (event.target as HTMLInputElement).files?.[0]
    if (!file) return
    
    const response:any = await addObjectImage(file, props.objectName )
    console.log(response)

    if (!response.ok) {
      const text = await response.text();
      toaster.show(`Erreur lors de l'envoie de votre image : ${text}`, "error");
      return;
    }
    const { url } = await response.json()
    addPhoto(url) // url retournée par le backend
  }
  catch {
    toaster.show("Erreur lors de l'ajout de votre image", "error")
  }


}

function handleUrl() {
  const url = urlInput.value.trim()
  if (!url) {
    urlError.value = 'Veuillez entrer une URL.'
    return
  }
  // Validation basique
  try {
    new URL(url)
  } catch {
    urlError.value = 'URL invalide.'
    return
  }
  urlError.value = ''
  addPhoto(url)
}
</script>

<style scoped>
.photo-picker {
  font-family: system-ui, sans-serif;
}

.label {
  font-size: 14px;
  font-weight: 500;
  color: #111;
  margin: 0 0 12px;
}

/* Grille */
.photo-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
}

/* Case photo existante */
.photo-slot {
  aspect-ratio: 1;
  border-radius: 8px;
  overflow: hidden;
  position: relative;
  border: 1px solid #e5e5e5;
  background: #f5f5f5;
}

.photo-slot img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.remove-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  background: rgba(0, 0, 0, 0.55);
  border: none;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #fff;
  padding: 0;
  opacity: 0;
  transition: opacity 0.15s;
}

.photo-slot:hover .remove-btn {
  opacity: 1;
}

/* Case + */
.add-slot {
  aspect-ratio: 1;
  border-radius: 8px;
  border: 1.5px dashed #ccc;
  background: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 5px;
  cursor: pointer;
  color: #999;
  font-size: 12px;
  transition: border-color 0.15s, color 0.15s;
  user-select: none;
}

.add-slot:hover,
.add-slot:focus {
  border-color: #378ADD;
  color: #378ADD;
  outline: none;
}

/* Overlay modal */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-box {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  width: 340px;
  max-width: 90vw;
  box-shadow: 0 8px 32px rgba(0,0,0,0.18);
}

.modal-title {
  font-size: 15px;
  font-weight: 500;
  margin: 0 0 16px;
  color: #111;
}

/* Onglets */
.tab-bar {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}

.tab-btn {
  flex: 1;
  padding: 8px;
  font-size: 13px;
  border-radius: 8px;
  border: 1px solid #ddd;
  background: #fff;
  color: #888;
  cursor: pointer;
  transition: all 0.15s;
}

.tab-btn.active {
  border-color: #378ADD;
  color: #185FA5;
  font-weight: 500;
  border-width: 1.5px;
}

/* Pane fichier */
.pane {
  margin-bottom: 12px;
}

.file-drop {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  border: 1.5px dashed #ddd;
  border-radius: 8px;
  padding: 24px;
  cursor: pointer;
  color: #888;
  font-size: 13px;
  text-align: center;
  transition: border-color 0.15s;
}

.file-drop:hover {
  border-color: #378ADD;
}

/* Pane URL */
.url-input {
  width: 100%;
  box-sizing: border-box;
  padding: 9px 12px;
  font-size: 13px;
  border-radius: 8px;
  border: 1px solid #ddd;
  color: #111;
  outline: none;
  transition: border-color 0.15s;
}

.url-input:focus {
  border-color: #378ADD;
}

.url-error {
  font-size: 12px;
  color: #e24b4a;
  margin: 6px 0 0;
}

.confirm-btn {
  margin-top: 10px;
  width: 100%;
  padding: 10px;
  font-size: 13px;
  font-weight: 500;
  background: #378ADD;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.15s;
}

.confirm-btn:hover {
  background: #185FA5;
}

.cancel-btn {
  margin-top: 8px;
  width: 100%;
  padding: 10px;
  font-size: 13px;
  color: #888;
  background: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
}

.cancel-btn:hover {
  background: #ebebeb;
}

/* Responsive : 2 colonnes sur petits écrans */
@media (max-width: 480px) {
  .photo-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>