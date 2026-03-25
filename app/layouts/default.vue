<script setup lang="ts">
    const isOpen = ref<boolean>(false);

    const handleClick = () => {
        isOpen.value = !isOpen.value;
        console.log(isOpen.value);
    };
</script>

<template>
    <div class="min-h-screen flex flex-col-2 ">
        <nav class="bg-gray-800 text-white p-4 transition-all duration-300"
             :class="isOpen ? 'w-30' : 'w-10'">
            <button @click="handleClick" > 
                ☰
            </button>
        </nav>
        <div class="flex flex-col flex-1 ">
            <header class="bg-blue-500 p-4 flex gap-4">
                <NuxtLink to="/" class="link">Home</NuxtLink>
                <NuxtLink to="/profile" class="link">Profile</NuxtLink>
                <NuxtLink to="/objects" class="link">Objects</NuxtLink>
            </header>
            <main>
                <slot/>
            </main>
            <footer class="bg-gray-800 text-white p-4 mt-auto">
                Footer
            </footer>
        </div>
    </div>
</template>

<style scoped>



</style>