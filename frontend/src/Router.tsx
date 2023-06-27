import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "./pages/error-page";
import Login from "./pages/login";
import App from "./App";
import Home from "./pages/home";
import BankAccount from "./pages/bank-account";
import Register from "./pages/register";
import ApplicationForCredit from "./pages/application-for-credit";

const Router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "home",
        element: <Home />
      },
      {
        path: "login",
        element: <Login/>
      },
      {
        path: "bank-account",
        element: <BankAccount/>
      },
      {
        path: "register",
        element: <Register />
      },
      {
        path: "application-for-credit",
        element: <ApplicationForCredit />
      }
    ]
  }
]);

export default Router;