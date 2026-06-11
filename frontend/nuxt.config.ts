/// <reference types="nuxt" />
import tailwindcss from "@tailwindcss/vite";
import { defineNuxtConfig } from 'nuxt/config';

export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
  modules: ['@nuxt/eslint', '@nuxt/test-utils', '@nuxt/image'],
  css: ['./app/assets/css/main.css'],
  vite: {
    plugins: [
      tailwindcss(),
    ],
  },
  components: true,
  experimental: {
    Islands: true
  },
  runtimeConfig: {
    public: {
      apiBase: "http://localhost:8080"
    }
  },
  routeRules: {
    '/api/**': { proxy: 'http://backend:8080/api/**' },
    '/uploads/**': { proxy: 'http://backend:8080/uploads/**' }
  }
})