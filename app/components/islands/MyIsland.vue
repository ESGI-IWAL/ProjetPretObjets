<script setup lang="ts">
const props = defineProps<{
    title: string
}>()

const counter = ref<number>(0)

const handleClick=() => {
    counter.value++
}
</script>
<template>
  <h1 class="text-3xl font-bold text-blue-600">Tailwind OK</h1>
    <div>
        <h1>{{props.title}}</h1>
    </div> 
</template>

<style >  </style>