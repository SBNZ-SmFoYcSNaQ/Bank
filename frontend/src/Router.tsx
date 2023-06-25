import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "./pages/error-page";
import Login from "./pages/login";
import App from "./App";
import Home from "./pages/home";

const Router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
    errorElement: <ErrorPage/>,
    children: [
      {
        path: "home",
        element: <Home/>
      },
      {
        path: "login",
        element: <Login/>
      }
    ]
  }
]);

export default Router;