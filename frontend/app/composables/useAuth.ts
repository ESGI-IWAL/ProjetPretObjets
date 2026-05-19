import type { IUserAuth } from "~/types/user"

export const useAuth = () => {
  const activeUser = useActiveUser()
  const token = useCookie('token', {httpOnly: true, secure: true});
  const isAuthenticated = useState<boolean>('isAuthenticated', () => true);
  const erreurConnexion = useState<string>('erreur', () => "");

  async function login(username: string, password: string) {
    const data:IUserAuth = await $fetch("http://localhost:8080/login", {
      method: 'POST',
      body: { username, password }
    })

    token.value = "veveververver"
    activeUser.value = data;
    isAuthenticated.value = Boolean(data);    
    if (isAuthenticated.value) {
      erreurConnexion.value = "";
      navigateTo('/'); // Redirection après connexion
    }
    else{
      erreurConnexion.value = "Identifiants incorrects";
      navigateTo('/login');
    }
  }

  function logout() {
    token.value = null
    activeUser.value = null
  }

  return {
     erreurConnexion,
    isAuthenticated,
    login,
    logout
  }
}