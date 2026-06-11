<script setup lang="ts">
import type { IUpdateUserDto } from "~/dto/user/update.dto";
import type { IUser } from "~/types/user";

const props = defineProps<{
  user: IUser;
}>();

const editMode = ref<boolean>(false);

const emit = defineEmits(["handleSubmitUpdate"]);

const handleSubmitUpdate = (updateObject: Omit<IUpdateUserDto, "id">) => {
  emit("handleSubmitUpdate", { ...updateObject, id: props.user.id });
  editMode.value = false;
};

const handleCancelEdit = () => {
  editMode.value = false;
};
</script>

<template>
  <div class="app-page">
    <div class="app-container">

      <div class="profile-header">
        <p class="form-eyebrow" style="color: var(--color-accent)">Mon compte</p>
        <h1 style="color: var(--color-title); font-size: 1.75rem; font-weight: 700; letter-spacing: -0.02em;">Profil utilisateur</h1>
      </div>

      <div class="profile-card">

        <!-- Mode édition -->
        <ProfileEdition
          v-if="editMode"
          :user="user"
          @handleSubmitUpdate="handleSubmitUpdate"
          @cancelEdit="handleCancelEdit"
        />

        <!-- Mode lecture -->
        <div v-else class="profile-view">
          <div class="profile-avatar-section">
            <img
              :src="user.avatar ?? '/image.png'"
              alt="Profil utilisateur"
              class="profile-avatar"
            />
            <div class="profile-actions">
              <ButtonOptions
                :actions="[
                  { function: () => { editMode = true }, label: 'Modifier', svg: '/icons/edit.svg' }
                ]"
              />
            </div>
          </div>

          <div class="profile-fields">
            <div class="profile-field">
              <span class="profile-label">Pseudo</span>
              <p class="profile-value">{{ user.username }}</p>
            </div>
            <div class="profile-field">
              <span class="profile-label">Email</span>
              <p class="profile-value">{{ user.email }}</p>
            </div>
            <div class="profile-field profile-field--full">
              <span class="profile-label">Description</span>
              <p class="profile-value" v-if="user.description">{{ user.description }}</p>
              <p class="profile-empty" v-else>Aucune description renseignée.</p>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
:deep(.app-page) { background-color: var(--color-background) !important; }

.profile-header {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.profile-card {
  background-color: var(--color-surface);
  border: 1px solid #d4ccc0;
  border-radius: 20px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.06);
  padding: 2rem;
}

.profile-view {
  display: flex;
  flex-direction: column;
  gap: 1.75rem;
}

.profile-avatar-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid var(--color-background);
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.profile-fields {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
}

.profile-field {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  padding: 0.75rem 1rem;
  background-color: var(--color-background);
  border-radius: 10px;
  border: 1px solid #e2d9cd;
}

.profile-field--full {
  grid-column: 1 / -1;
}

.profile-label {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: var(--color-accent);
}

.profile-value {
  font-size: 0.925rem;
  color: var(--color-title);
  font-weight: 500;
  margin: 0;
}

.profile-empty {
  font-size: 0.875rem;
  color: var(--color-text);
  opacity: 0.4;
  font-style: italic;
  margin: 0;
}
</style>