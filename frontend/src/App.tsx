import { MainLayout, UserContextProvider } from './shared';
import { Outlet } from 'react-router-dom';

function App() {
  return (
    <>
      <UserContextProvider>
        <MainLayout>
          <Outlet />
        </MainLayout>
      </UserContextProvider>
    </>
  );
}

export default App;
