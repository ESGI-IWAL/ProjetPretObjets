<script setup lang="ts">

    const props = defineProps<{
        endDate: Date|null,
        editMode: boolean
    }>()

    const emit = defineEmits(['updateLending', 'cancelEdit'])
    const toInputDate = (value: Date | null) => value ? value.toISOString().slice(0, 10) : ''
    const toDateValue = (value: string) => value ? new Date(value) : null
    const editableEndDate = ref<string>(toInputDate(props.endDate))

    watch(() => props.endDate, (newValue) => {
        editableEndDate.value = toInputDate(newValue)
    })

    const handleSubmitUpdate = async(endDate : Date|null) => {
        emit('updateLending', endDate)
    }

    const handleCancelEdit = () => {
        emit('cancelEdit')
    }
</script>
<template>
    <form>
        <input type="date" v-model="editableEndDate" />
        <ButtonForm :cancelForm="handleCancelEdit" :validateForm="() => handleSubmitUpdate(toDateValue(editableEndDate))" />
    </form>
</template>


<style>

</style>