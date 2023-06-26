import { AppBar, Box, CssBaseline, Divider, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, ThemeOptions, ThemeProvider, Toolbar, Typography, createTheme } from "@mui/material";
import HomeIcon from '@mui/icons-material/Home';
import LoginIcon from '@mui/icons-material/Login';
import { NavLink } from "react-router-dom";
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';

const drawerWidth = 250;

interface Props {
  children: React.ReactNode
}

interface NavItem {
  text: string;
  icon: JSX.Element;
  route: string;
}

const upperNavItems: NavItem[] = [
  {
    text: "Home",
    icon: <HomeIcon/>,
    route: "/home"
  },
  {
    text: "Bank Account",
    icon: <AccountBalanceWalletIcon/>,
    route: "/bank-account"
  }
]

const lowerNavItems: NavItem[] = [
  {
    text: "Login",
    icon: <LoginIcon />,
    route: "/login"
  }
]

export const MainLayout = ({ children } : Props ) => {
  return (
    <Box sx={{ display: 'flex' }}>
    <CssBaseline />
    <AppBar
      position="fixed"
      sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
    >
      <Toolbar>
        <Typography variant="h4" noWrap component="div">
          Bank
        </Typography>
      </Toolbar>
    </AppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          }
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar />
        <Divider />
        <List>
          {upperNavItems.map((item) => (
            <NavLink to={item.route} key={item.route}>
              <ListItem  disablePadding>
                  <ListItemButton>
                    <ListItemIcon>
                      {item.icon}
                    </ListItemIcon>
                      <ListItemText primary={item.text} />
                  </ListItemButton>
              </ListItem>
            </NavLink>
          ))}
        </List>
        <Divider />
        <List>
          {lowerNavItems.map((item) => (
            <NavLink to={item.route} key={item.route}>
              <ListItem  disablePadding>
                  <ListItemButton>
                    <ListItemIcon>
                      {item.icon}
                    </ListItemIcon>
                      <ListItemText primary={item.text} />
                  </ListItemButton>
              </ListItem>
            </NavLink>
          ))}
        </List>
      </Drawer>
    <Box
      component="main"
      sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}
    >
      <Toolbar />
      {children}
    </Box>
  </Box>  
  )
}
