import type { IUser, IUserAuth as IUserAuth } from "~/types/user"

export const useAuth = () => {
  const token = useCookie<string | null>('token')
  const user = useState<IUser | null>('user', () => null)

  const isAuthenticated = computed(() => !!user.value)

  async function fetchUser() {
    if (!token.value) {
      user.value = null
      return
    }

    try {
      const data:IUserAuth = await $fetch('/api/me', {
        headers: {
          Authorization: `Bearer ${token.value}`
        }
      })

      user.value = {
        id: data.id,
        pseudo: data.pseudo,
        avatar: data.avatar,
        email: data.email,
        description: data.description
      }
    } catch (e) {
      user.value = null
      token.value = null
    }
  }

  async function login(email: string, password: string) {
    const data:IUserAuth = await $fetch('/api/login', {
      method: 'POST',
      body: { email, password }
    })

    token.value = data.token
    await fetchUser()
  }

  function logout() {
    token.value = null
    user.value = null
  }

  return {
    token,
    user,
    isAuthenticated,
    fetchUser,
    login,
    logout
  }
}