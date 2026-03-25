import type { IUserAuth } from "~/types/user"

export const useAuth = () => {
  const activeUser = useActiveUser()
  const token = useCookie('token', {httpOnly: true, secure: true});
  const isAuthenticated = useState<boolean>('isAuthenticated', () => false);

  async function login(username: string, password: string) {
    const data:IUserAuth = await $fetch('/api/auth/user', {
      method: 'POST',
      body: { username, password }
    })
    console.log("EHEHHEHE");

    token.value = "veveververver"
    activeUser.value = data;
    isAuthenticated.value = Boolean(data);    
    if (isAuthenticated.value) {
      navigateTo('/'); // Redirection après connexion
    }
    else{
      navigateTo('/login');
    }
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