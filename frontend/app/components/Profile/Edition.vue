<script setup lang="ts">
import type { IUpdateUserDto } from '~/dto/user/update.dto';
import type { IUser } from '~/types/user';

const props = defineProps<{
  user: IUser;
}>();

const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const editForm = reactive<Omit<IUpdateUserDto, "id">>({
  username: props.user.username,
  avatar: props.user.avatar,
  description: props.user.description,
  email:props.user.email
});

const resetForm = () => {
editForm.username= props.user.username;
  editForm.avatar= props.user.avatar;
  editForm.description= props.user.description;
  editForm.email = props.user.email
};

watch(
  () => props.user,
  () => {
    resetForm();
  },
  { immediate: true },
);

const handleSubmit = () => {
  emit("handleSubmitUpdate", {
    username: editForm.username,
    description: editForm.description, 
email: editForm.email });
};

const handleCancel = () => {
  resetForm();
  emit("cancelEdit");
};

</script>
<template>
  <form class="edit-form" @submit.prevent="handleSubmit">

    <div class="form-field">
      <label class="form-label" for="username">Pseudo</label>
      <input id="username" v-model.trim="editForm.username" type="text" required class="form-input" placeholder="Votre pseudo" />
    </div>

    <div class="form-field">
      <label class="form-label" for="email">Email</label>
      <input id="email" v-model.trim="editForm.email" type="email" required class="form-input" placeholder="votre@email.com" />
    </div>

    <div class="form-field">
      <label class="form-label" for="description">Description</label>
      <textarea id="description" v-model.trim="editForm.description" rows="3" class="form-textarea" placeholder="Décrivez-vous en quelques mots…"></textarea>
    </div>

    <div class="form-actions">
      <button type="button" class="btn-cancel" @click="handleCancel">Annuler</button>
      <button type="submit" class="btn-save">Enregistrer</button>
    </div>
  </form>
</template>

<style scoped>
.edit-form {
  display: grid;
  gap: 1.1rem;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding-top: 0.5rem;
}
.btn-save {
  background-color: var(--color-primary);
  color: var(--color-background);
  border: none;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  cursor: pointer;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
}
.btn-save:hover {
  background-color: var(--color-secondary);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
.btn-cancel {
  background-color: var(--color-surface);
  color: var(--color-title);
  border: 1px solid #c9c0ae;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.btn-cancel:hover {
  background-color: #d9d2c5;
}
</style>