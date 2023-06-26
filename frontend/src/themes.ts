import { ThemeOptions } from "@mui/material";

const mode = 'dark';
const primary = '#e85d04';
const background = '#03071e';
const secondary = '#f48c06';
const error = '#d00000';
const textPrimary = '#fff';
const paperBackground = '#040b2f';

const defaultThemeOptions: ThemeOptions = {
  palette: {
    mode: mode,
    primary: {
      main: primary,
    },
    background: {
      default: background,
      paper: background,
    },
    secondary: {
      main: secondary,
    },
    error: {
      main: error,
    },
  },
  components: {
    MuiTypography: {
      defaultProps: {
        color: textPrimary
      }
    },
    MuiPaper: {
      defaultProps: {
        sx: {
          backgroundColor: paperBackground
        }
      }
    }
  }
};

export { defaultThemeOptions }