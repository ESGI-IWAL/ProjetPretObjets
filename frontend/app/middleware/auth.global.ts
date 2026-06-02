export default defineNuxtRouteMiddleware((to) => {
  if (process.server) return
  
  const token = localStorage.getItem('token')

  if (!token && to.path !== '/login') {
    return navigateTo('/login')
  }
})