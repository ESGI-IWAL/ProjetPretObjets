import type { ICreateUserDto } from "~/dto/user/create.dto";
import { login as loginService, register as registerService} from "~/services/auth";

export const useAuth = () => {

  const isAuthenticated = useState<boolean>('isAuthenticated', () => false);
  const erreurConnexion = useState<string>('erreur', () => "");

  const login = async (email: string, password: string) => {
    try{
      let token:string = await loginService(email, password);
      localStorage.setItem('token', token)
      isAuthenticated.value = true;
      navigateTo('/');
    }
    catch (error) {
      console.error("Erreur lors de la connexion", error);
    }
  }

  const register = async ( dto : ICreateUserDto) => {
    try {
      let token:string = await registerService(dto);
      localStorage.setItem('token', token)
      isAuthenticated.value = true;
      navigateTo('/');
    } catch (error) {
      console.error("Erreur lors de l'inscription", error);
    }
  };

  const logout = () => {
    console.log("test")
    console.log(localStorage.getItem('token'))
    localStorage.removeItem('token')
        console.log(localStorage.getItem('token'))

    isAuthenticated.value = false
    navigateTo("/login")
  }

  return {
    erreurConnexion,
    isAuthenticated,
    login,
    logout,
    register
  }
  }