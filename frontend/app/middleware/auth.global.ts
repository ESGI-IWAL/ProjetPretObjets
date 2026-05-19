import { useAuth } from "~/composables/useAuth"

export default defineNuxtRouteMiddleware(async (to) => {
  const { isAuthenticated } = useAuth()
  
  const publicPages = ["/login", "/register"]

  if (publicPages.includes(to.path)) {
    return
  }
  
  if (!isAuthenticated) {
    return navigateTo("/login")
  }

})