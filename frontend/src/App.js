import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import UserTable from "./views/userTable";
import Index from "./views/about";
import {ResponseInterceptor} from "./services/apiAxios";
import Header from "./components/Header/header";
import {SnackbarProvider} from "./components/Snackbar/snackbar";
import {createTheme, ThemeProvider} from "@mui/material";
import FileUpload from "./views/fileUpload";

function Home() {
    return <UserTable/>;
}

function About() {
    return <Index/>;
}

const THEME = createTheme({
    typography: {
        "fontFamily": `'Poppins', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif`,
        "fontSize": 14,
        "fontWeightLight": 200,
        "fontWeightRegular": 400,
        "fontWeightMedium": 700
    }
});

function App() {

    return (
        <ThemeProvider theme={THEME}>
            <SnackbarProvider>
                <Router>
                    <div>
                        <Header/>
                        <ResponseInterceptor/>
                        <Routes>
                            <Route path="/about" element={<About/>}/>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/import" element={<FileUpload/>}/>
                        </Routes>
                    </div>
                </Router>
            </SnackbarProvider>
        </ThemeProvider>

    );
}

export default App;
