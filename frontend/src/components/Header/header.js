import React from "react";
import {Drawer, IconButton, List, useMediaQuery} from "@mui/material";
import logo from "../../logo.svg";
import MenuIcon from "@mui/icons-material/Menu";
import MenuItems from "../MenuItem/menuItems";

const Header = () => {
    const [isDrawerOpen, setIsDrawerOpen] = React.useState(false);
    const isSmallScreen = useMediaQuery('(max-width:600px)');

    const toggleDrawer = () => {
        setIsDrawerOpen(!isDrawerOpen);
    };

    return (
        <header className="app-header">
            <div className="logo-container">
                <img src={logo} className="App-logo" alt="logo" />
            </div>
            {isSmallScreen ? (
                <IconButton className="menu-toggle" onClick={toggleDrawer} size="large">
                    <MenuIcon sx={{ color: 'white' }} />
                </IconButton>
            ) : (
                <div className="menu-container">
                    <MenuItems />
                </div>
            )}
            <Drawer anchor="right" open={isDrawerOpen} onClose={toggleDrawer}>
                <List>
                    <MenuItems onClick={toggleDrawer} />
                </List>
            </Drawer>
        </header>
    );
}

export default Header;
