<script setup lang="ts">

    defineProps<{
        endDate: Date|null,
        editMode: boolean
    }>()

    const emit = defineEmits(['updateLending'])
    const handleSubmitUpdate = async(endDate : Date|null) => {
        emit('updateLending', endDate)
    }
</script>
<template>
    <form>
        <input type="date" v-model="endDate" />
        <ButtonFrom :cancelForm="() => editMode = false" :validateForm="() => handleSubmitUpdate(endDate)" />
    </form>
</template>


<style>

</style>