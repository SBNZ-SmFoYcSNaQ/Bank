import { MainLayout } from "./components/main-layout";
import type { Client, BankingOfficer, Role, Sex } from "./models/user";
import { UserContextValue, UserContext, UserContextProvider } from "./contexts/UserContext";
import useFetch from "./hooks/useFetch";

export { 
  MainLayout, 
  useFetch, 
  UserContextProvider, 
  UserContext 
}

export type { 
  Client,
   BankingOfficer, 
   Role, 
   Sex, 
   UserContextValue 
}



