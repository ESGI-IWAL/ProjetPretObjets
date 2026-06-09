export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig()

  const api = $fetch.create({
    baseURL: config.public.apiBase,

    onRequest({ options }) {
      const token = localStorage.getItem('token')

      if (token) {
        const headers = new Headers(options.headers as HeadersInit)

        headers.set('Authorization', `Bearer ${token}`)

        options.headers = headers
      }
    }
  })
  
  return {
    provide: {
      api
    }
  }
})