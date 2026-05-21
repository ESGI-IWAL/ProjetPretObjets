<script setup lang="ts">

    const props = defineProps<{
        endAt: Date|null,
        startAt:Date,
        editMode: boolean
    }>()

    const emit = defineEmits(['handleSubmitUpdate', 'cancelEdit'])
    const toInputDate = (value: Date | null) => value ? value.toISOString().slice(0, 10) : ''
    const toDateValue = (value: string) => value ? new Date(value) : null
    const editableEndDate = ref<string>(toInputDate(props.endAt))
    const editableStartDate = ref<string>(toInputDate(props.endAt))

    watch(() => props.endAt, (newValue) => {
        editableEndDate.value = toInputDate(newValue)
    })

        watch(() => props.startAt, (newValue) => {
        editableStartDate.value = toInputDate(newValue)
    })

    const handleSubmitUpdate = async() => {
        emit('handleSubmitUpdate', {endAt: toDateValue(editableEndDate.value), editableStartDate: toDateValue(editableEndDate.value)})
    }

    const handleCancelEdit = () => {
        emit('cancelEdit')
        resetForm()
    }

    const resetForm = () => {
        editableEndDate.value = toInputDate(props.endAt)
    }
</script>
<template>
    <form>
        <input type="date" v-model="editableEndDate" />
        <input type="date" v-model="editableStartDate" />
        <ButtonForm :cancelForm="handleCancelEdit" :validateForm="() => handleSubmitUpdate()" />
    </form>
</template>


<style>

</style>