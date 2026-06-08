export default defineNuxtRouteMiddleware((to) => {
  if (process.server) return
  
  const token = localStorage.getItem('token')

  if (!token && to.path !== '/login' && to.path !== '/register') {
    return navigateTo('/login')
  }
})