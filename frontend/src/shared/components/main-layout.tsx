import { AppBar, Box, CssBaseline, Divider, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Toolbar, Typography } from "@mui/material";
import HomeIcon from '@mui/icons-material/Home';
import LoginIcon from '@mui/icons-material/Login';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import LogoutIcon from '@mui/icons-material/Logout';
import CreditScoreIcon from '@mui/icons-material/CreditScore';
import { NavLink } from "react-router-dom";
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import { ToastContainer } from "react-toastify";
import { useContext } from "react";
import { UserContext, UserContextValue } from "../contexts/UserContext";

const drawerWidth = 250;

interface Props {
  children: React.ReactNode
}

interface NavItem {
  text: string;
  icon: JSX.Element;
  route: string;
  requireAuth: boolean;
  roles?: string[];
}

const upperNavItems: NavItem[] = [
  {
    text: "Home",
    icon: <HomeIcon />,
    route: "/home",
    requireAuth: false
  },
  {
    text: "Bank Account",
    icon: <AccountBalanceWalletIcon />,
    route: "/bank-account",
    requireAuth: true,
    roles: ["CLIENT"]
  },
  {
    text: "Application For Credit",
    icon: <CreditScoreIcon />,
    route: "/application-for-credit",
    requireAuth: true,
    roles: ["CLIENT"]
  }
]

const lowerNavItems: NavItem[] = [
  {
    text: "Login",
    icon: <LoginIcon />,
    route: "/login",
    requireAuth: false
  },
  {
    text: "Register",
    icon: <HowToRegIcon />,
    route: "/register",
    requireAuth: false
  }
]

export const MainLayout = ({ children }: Props) => {
  const { logout, isAuth, user } = useContext<UserContextValue>(UserContext);

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
          {upperNavItems.map((item) => (item.requireAuth === isAuth) && (!item.roles || item.roles.includes(user.role)) && (
            <NavLink to={item.route} key={item.route}>
              <ListItem disablePadding>
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
          {lowerNavItems.map((item) => (item.requireAuth === isAuth) && (
            <NavLink to={item.route} key={item.route}>
              <ListItem disablePadding>
                <ListItemButton>
                  <ListItemIcon>
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText primary={item.text} />
                </ListItemButton>
              </ListItem>
            </NavLink>
          ))}
          {isAuth && (
            <ListItem disablePadding>
              <ListItemButton onClick={logout}>
                <ListItemIcon>
                  <LogoutIcon />
                </ListItemIcon>
                <ListItemText primary="Logout" />
              </ListItemButton>
            </ListItem>
          )}
        </List>
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}
      >
        <Toolbar />
        {children}
      </Box>
      <ToastContainer />
    </Box>
  );
};
