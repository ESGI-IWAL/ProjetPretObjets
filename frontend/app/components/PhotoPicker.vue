<script setup lang="ts">
const props = defineProps<{
  maxPhotos?: number;
  modelValue: string[];
  objectName?: string;
}>();
const toaster = useToaster()
const emit = defineEmits<{
  (e: "update:modelValue", value: string[]): void;
}>();

const isUploading = ref(false);
const error = ref<string | null>(null);
const urlInput = ref("");

const handleFileChange = async (event: Event) => {
  const input = event.target as HTMLInputElement;
  if (!input.files?.length) return;

  const remaining = (props.maxPhotos ?? 8) - props.modelValue.length;
  const toUpload = Array.from(input.files).slice(0, remaining);

  if (toUpload.length === 0) {
    error.value = `Maximum ${props.maxPhotos ?? 8} photos atteint`;
    return;
  }

  isUploading.value = true;
  error.value = null;

  try {
    const uploadedUrls: string[] = [];

    for (const file of toUpload) {
      const formData = new FormData();
      formData.append("images", file);
      formData.append("folderName", props.objectName ?? "objet");

      const response = await fetch("/api/upload", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        toaster.show("Erreur d'en la réponse de l'image", "error")
      }

      const data = await response.json();
      uploadedUrls.push(data.url);
    }

    emit("update:modelValue", [...props.modelValue, ...uploadedUrls]);
  } catch (e: any) {
    error.value = e.message ?? "Erreur lors de l'upload";
  } finally {
    isUploading.value = false;
    input.value = "";
  }
};

const addUrl = () => {
  const url = urlInput.value.trim();
  if (!url) return;

  if (props.modelValue.length >= (props.maxPhotos ?? 8)) {
    error.value = `Maximum ${props.maxPhotos ?? 8} photos atteint`;
    return;
  }

  try {
    new URL(url);
  } catch {
    error.value = "URL invalide";
    return;
  }

  error.value = null;
  emit("update:modelValue", [...props.modelValue, url]);
  urlInput.value = "";
};

const removeImage = (index: number) => {
  const updated = [...props.modelValue];
  updated.splice(index, 1);
  emit("update:modelValue", updated);
};
</script>

<template>
  <div class="photo-picker">

    <div v-if="modelValue.length" class="photo-grid">
      <div v-for="(url, index) in modelValue" :key="url" class="photo-item">
        <img
            :src="url"
            :alt="`Photo ${index + 1}`"
            @error="(e) => (e.target as HTMLImageElement).src = '/objectImage.png'"
        />
        <button type="button" class="remove-btn" @click="removeImage(index)">✕</button>
      </div>
    </div>

    <p class="counter">{{ modelValue.length }} / {{ maxPhotos ?? 8 }} photos</p>

    <div v-if="modelValue.length < (maxPhotos ?? 8)" class="add-section">

      <label class="upload-label" :class="{ uploading: isUploading }">
        <span v-if="isUploading">Envoi en cours...</span>
        <span v-else>📁 Choisir un fichier</span>
        <input
            type="file"
            accept="image/jpeg,image/png,image/webp"
            multiple
            :disabled="isUploading"
            class="hidden-input"
            @change="handleFileChange"
        />
      </label>

      <span class="separator-or">ou</span>

      <div class="url-input-row">
        <input
            v-model="urlInput"
            type="url"
            placeholder="https://exemple.com/image.jpg"
            class="url-input"
            @keydown.enter.prevent="addUrl"
        />
        <button type="button" class="add-url-btn" @click="addUrl">
          Ajouter
        </button>
      </div>

    </div>

    <p v-if="error" class="error-msg">{{ error }}</p>
  </div>
</template>

<style scoped>
.photo-picker {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}
.photo-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}
.photo-item {
  position: relative;
  width: 100px;
  height: 100px;
}
.photo-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0.5rem;
  border: 1px solid #d1d5db;
}
.remove-btn {
  position: absolute;
  top: 4px;
  right: 4px;
  background: rgba(0,0,0,0.6);
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 11px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.counter {
  font-size: 0.75rem;
  color: #9ca3af;
}
.add-section {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}
.upload-label {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.6rem 1rem;
  border: 2px dashed #d1d5db;
  border-radius: 0.5rem;
  cursor: pointer;
  color: #6b7280;
  font-weight: 500;
  white-space: nowrap;
  transition: border-color 0.2s;
}
.upload-label:hover {
  border-color: #2563eb;
  color: #2563eb;
}
.upload-label.uploading {
  opacity: 0.6;
  cursor: not-allowed;
}
.hidden-input {
  display: none;
}
.separator-or {
  color: #9ca3af;
  font-size: 0.875rem;
  flex-shrink: 0;
}
.url-input-row {
  display: flex;
  gap: 0.5rem;
  flex: 1;
  min-width: 200px;
}
.url-input {
  flex: 1;
  padding: 0.6rem 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  font-size: 0.875rem;
}
.add-url-btn {
  padding: 0.6rem 1rem;
  background: #2563eb;
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  white-space: nowrap;
}
.add-url-btn:hover {
  background: #1d4ed8;
}
.error-msg {
  color: #dc2626;
  font-size: 0.875rem;
}
</style>