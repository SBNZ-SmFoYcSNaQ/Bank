import { MainLayout, UserContextProvider } from './shared';
import { Outlet } from 'react-router-dom';
import { createTheme } from '@mui/material';
import { ThemeProvider } from '@emotion/react';
import { defaultThemeOptions } from './themes';

const customTheme = createTheme(defaultThemeOptions);

function App() {
  return (
    <UserContextProvider>
      <ThemeProvider theme={customTheme}>
        <MainLayout>
          <Outlet />
        </MainLayout>
      </ThemeProvider>
    </UserContextProvider>
  );
}

export default App;
