import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "./pages/error-page";
import Login from "./pages/login";
import App from "./App";
import Home from "./pages/home";
import Register from "./pages/register";

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
        element: <Login />
      },
      {
        path: "register",
        element: <Register />
      }
    ]
  }
]);

export default Router;