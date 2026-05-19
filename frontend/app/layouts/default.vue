<script setup lang="ts">
const isOpen = ref<boolean>(false);

const handleClick = () => {
  isOpen.value = !isOpen.value;
  console.log(isOpen.value);
};
</script>

<template>
  <div class="min-h-screen flex">
    <nav
      class="bg-gray-800 text-white p-4 transition-all duration-300"
      :class="isOpen ? 'w-40' : 'w-10'"
    >
      <button @click="handleClick" class="sticky top-5">☰</button>
      <div
        class="flex flex-col mt-8 gap-4 sticky top-18"
        :class="isOpen ? 'block' : 'hidden'"
      >
        <div>
          <h3 class="flex text-lg font-semibold mb-2"> Prêts </h3>
          <div class="flex flex-col gap-3 ml-4">
            <NuxtLink to="/lending" class="link"> En cours </NuxtLink>
            <NuxtLink to="/lending/history" class="link"> Historique </NuxtLink>
            <NuxtLink to="/lending/new" class="link"> Nouveau </NuxtLink>
          </div>
        </div>       
        <div>
          <h3 class="flex text-lg font-semibold mb-2"> Objets </h3>
          <div class="flex flex-col gap-3 ml-4">
            <NuxtLink to="/object" class="link"> Liste </NuxtLink>
            <NuxtLink to="/object/new" class="link"> Nouveau </NuxtLink>
          </div>
        </div>
      </div>
    </nav>
    <div class="flex-1 flex flex-col min-h-screen">
      <header class="bg-blue-500 p-4 flex justify-between items-center sticky top-0 z-10">
        <NuxtLink to="/" class="text-white text-2xl font-bold">
          PreteMoiCa
        </NuxtLink>
        <NuxtLink to="/profile" class="text-white">profil</NuxtLink>
      </header>
      <main class="flex-1 p-4 md:p-6 lg:p-8">
        <slot />
      </main>
      <footer class="bg-gray-800 text-white p-4">Footer</footer>
    </div>
  </div>
</template>

<style scoped>
link {
  display: flex;
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  transition: background-color 0.3s ease;
  justify-items: end;
}

link:hover {
  background-color: #3b82f6;
}

</style>
