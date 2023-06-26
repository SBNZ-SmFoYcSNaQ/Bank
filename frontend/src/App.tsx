import { MainLayout } from './shared';
import { Outlet } from 'react-router-dom';
import { createTheme } from '@mui/material';
import { ThemeProvider } from '@emotion/react';
import { defaultThemeOptions } from './themes';

const customTheme = createTheme(defaultThemeOptions);

function App() {
  return (
    <ThemeProvider theme={customTheme}>
      <MainLayout>
        <Outlet />
      </MainLayout>
    </ThemeProvider>
  );
}

export default App;
