<script setup lang="ts">
import { getLendingById, updateLending } from '~/services/lending';


const route = useRoute()

const lending = getLendingById(route.params.id as string)

const handleSubmitUpdate = (endDate : Date) => {
    updateLending(route.params.id as string, endDate )
}
</script>
<template>
  
</template>


<style>

</style>