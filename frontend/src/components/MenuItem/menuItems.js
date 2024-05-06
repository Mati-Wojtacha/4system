import React from 'react';
import { NavLink } from 'react-router-dom';
import {ListItemButton, ListItemText} from "@mui/material";

const menuItems = [
    { path: "/", label: "Strona Główna" },
    { path: "/import", label: "Import" },
    { path: "/about", label: "O aplikacji" }
];

const MenuItems = ({ onClick }) => {
    return (
        <>
            {menuItems.map((item, index) => (
                <ListItemButton key={index} component={NavLink} to={item.path} onClick={onClick}>
                    <ListItemText primary={item.label} />
                </ListItemButton>
            ))}
        </>
    );
};

export default MenuItems;
