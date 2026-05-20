<script setup lang="ts">
import { getObjectById } from '~/services/object';


const route = useRoute()

const object = await getObjectById(route.params.id as string)

</script>
<template>
  
</template>


<style>

</style>