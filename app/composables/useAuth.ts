import type { IUser, IUserAuth } from "~/types/user"

export const useAuth = () => {
  const activeUser = useActiveUser()
    const token = useCookie('token', {httpOnly: true, secure: true});
const isAuthenticated = useState<boolean>('isAuthenticated', () => false);

  async function fetchUser() {
    if (!token.value) {
      activeUser.value = null
      isAuthenticated.value = false
      return
    }

    try {
      const data:IUserAuth = await $fetch('/api/me', {
        headers: {
          Authorization: `Bearer ${token.value}`
        }
      })

      activeUser.value = {
        id: data.id,
        pseudo: data.pseudo,
        avatar: data.avatar,
        email: data.email,
        description: data.description,
        password: data.password,
      }
      isAuthenticated.value = true
    } catch (e) {
      activeUser.value = null
      token.value = null
      isAuthenticated.value = false
    }
  }

  async function login(username: string, password: string) {
    const data:IUserAuth = await $fetch('/api/auth/user', {
      method: 'POST',
      body: { username, password }
    })
    console.log("EHEHHEHE");

    token.value = "veveververver"
    //activeUser.value = response;
    activeUser.value = data;
    isAuthenticated.value = !!data;    
    if (isAuthenticated.value) {
      navigateTo('/profile'); // Redirection après connexion
    }
    else{
      navigateTo('/login');
    }

    //@TODO REDIRIGER VERS LA PROFILE PAGE
  }

  function logout() {
    token.value = null
    activeUser.value = null
  }

  return {
    isAuthenticated,
    login,
    logout
  }
}