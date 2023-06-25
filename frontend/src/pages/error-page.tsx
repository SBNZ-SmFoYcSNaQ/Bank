import { Button, Container } from "@mui/material";
import { useNavigate, useRouteError } from "react-router-dom";

const ErrorPage = () => {
  const error: any = useRouteError();
  const navigation = useNavigate();

  console.error(error);

  return (
    <Container id="error-page" sx={{ textAlign: 'center'}}>
      <h1>Oops!</h1>
      <p>Sorry, an unexpected error has occurred.</p>
      <div>
        <i>{error.statusText || error.message}</i>
        <div>
          <Button 
            variant="contained" 
            color="warning"
            sx={{ my: '12px' }} 
            onClick={() => { navigation(-1) }}>Go Back</Button>
        </div>
      </div>
    </Container>
  );
}

export default ErrorPage;