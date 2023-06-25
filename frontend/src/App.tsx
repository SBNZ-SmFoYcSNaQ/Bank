import { MainLayout } from './shared';
import { Outlet } from 'react-router-dom';

function App() {
  return (
    <MainLayout>
      <Outlet />
    </MainLayout>
  );
}

export default App;
