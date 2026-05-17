import { useAuth } from "~/composables/useAuth"

export default defineNuxtRouteMiddleware(async (to) => {
  const { isAuthenticated } = useAuth()
  
  if (!isAuthenticated) {
    return navigateTo('/login')
  }

  // si route login et déjà connecté (= logique bonus)
  if (to.meta.requiresAuth && !isAuthenticated) {
    return navigateTo('/')
  }
})