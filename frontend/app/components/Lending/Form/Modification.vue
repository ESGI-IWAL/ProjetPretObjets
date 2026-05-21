<script setup lang="ts">

    const props = defineProps<{
        endAt: Date | string | null,
        startAt: Date | string,
        editMode: boolean
    }>()

    const emit = defineEmits(['handleSubmitUpdate', 'cancelEdit'])

    const toInputDate = (value: Date | string | null) => {
        if (!value) return ''
        if (typeof value === 'string') {
            const m = value.match(/^(\d{4}-\d{2}-\d{2})/)
            if (m) return m[1]
            const d = new Date(value)
            if (isNaN(d.getTime())) return ''
            const yyyy = d.getFullYear()
            const mm = String(d.getMonth() + 1).padStart(2, '0')
            const dd = String(d.getDate()).padStart(2, '0')
            return `${yyyy}-${mm}-${dd}`
        }
        const d = value as Date
        const yyyy = d.getFullYear()
        const mm = String(d.getMonth() + 1).padStart(2, '0')
        const dd = String(d.getDate()).padStart(2, '0')
        return `${yyyy}-${mm}-${dd}`
    }

    const editableEndDate = ref<string>(toInputDate(props.endAt) ?? "")
    const editableStartDate = ref<string>(toInputDate(props.startAt) ?? "")

    watch(() => props.endAt, (newValue) => {
        editableEndDate.value = toInputDate(newValue) ?? ""
    })

    watch(() => props.startAt, (newValue) => {
        editableStartDate.value = toInputDate(newValue) ?? ""
    })

    const handleSubmitUpdate = async() => {
        const payload = {
            endAt: editableEndDate.value || null,
            startAt: editableStartDate.value || null
        }
        emit('handleSubmitUpdate', payload)
    }

    const handleCancelEdit = () => {
        emit('cancelEdit')
        resetForm()
    }

    const resetForm = () => {
        editableEndDate.value = toInputDate(props.endAt) ?? ""
        editableStartDate.value = toInputDate(props.startAt) ?? ""
    }
</script>
<template>
    <PopIn :title="'Modification du prêt'" >
        <form>
            <label for="editableStartDate" class="form-label">Date de début</label>
            <input type="date" v-model="editableStartDate" />
            <label for="editableEndDate" class="form-label">Date de fin</label>
            <input type="date" v-model="editableEndDate" />
        </form>
        <template #buttons>
            <ButtonForm :cancelForm="handleCancelEdit" :validateForm="() => handleSubmitUpdate()" />
        </template>
    </PopIn>
</template>


<style>

</style>