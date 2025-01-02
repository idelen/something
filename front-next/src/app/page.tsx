import Header from "@/components/Header";
import MyMessages from "@/components/MyMessages";
import Sidebar from "@/components/Sidebar";
import { Box, CssBaseline, CssVarsProvider } from "@mui/joy";


export default function Home() {
  return (
    <CssVarsProvider disableTransitionOnChange>
      <CssBaseline />
      <Box sx={{ display: 'flex', minHeight: '100dvh' }}>
        <Sidebar />
        <Header />
        <Box component="main" className="MainContent" sx={{ flex: 1 }}>
          <MyMessages />
        </Box>
      </Box>
    </CssVarsProvider>
  );
}
