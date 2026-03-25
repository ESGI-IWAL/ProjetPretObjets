import { useAuth } from "~/composables/useAuth"

export default defineNuxtRouteMiddleware((to) => {
  const { isAuthenticated } = useAuth()
  console.log("Middleware auth - isAuthenticated:", isAuthenticated.value)
  if (!isAuthenticated.value) {
    return navigateTo('/login')
  }

  // si route login et déjà connecté (= logique bonus)
  if (to.path === '/login' && isAuthenticated.value) {
    return navigateTo('/')
  }
})