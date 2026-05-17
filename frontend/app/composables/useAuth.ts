import type { IUser } from "~/types/user";

export const useAuth = () => {
  const activeUser = useActiveUser()
  const api = useApi()
  const isAuthenticated = useState<boolean>('isAuthenticated', () => false);
  const erreurConnexion = useState<string>('erreur', () => "");

  const login = async (email: string, password: string) => {
    try {
      const user = await api<IUser>("/login", {
        method: "POST",
        body: {
          email,
          password
        }
      });

      activeUser.value = user;
      isAuthenticated.value = Boolean(user);
    } catch {
        erreurConnexion.value = "Identifiants incorrects";
        isAuthenticated.value = false;
    }
  }

  const logout = async () => {
    try{
      await api("/logout");
      activeUser.value = null;
      isAuthenticated.value = false;
      navigateTo('/login');
    }
    catch (error) {
      console.error("Erreur lors de la déconnexion", error);
    }
  }

  const register = async ( pseudo: string, firstName: string, lastName: string, email: string, password: string) => {
    try {
      const user = await api<IUser>("/register", {
        method: "POST",
        body: {
          pseudo,
          firstName,
          lastName,
          email,
          password
        }
      });

      activeUser.value = user;
      isAuthenticated.value = Boolean(user);
      navigateTo('/');
    } catch (error) {
      console.error("Erreur lors de l'inscription", error);
    }
  };

  return {
    erreurConnexion,
    isAuthenticated,
    login,
    logout,
    register
  }
  }