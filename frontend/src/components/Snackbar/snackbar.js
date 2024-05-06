import React, { createContext, useState, useContext } from 'react';
import { Snackbar } from "@mui/material";
import { ErrorOutline } from "@mui/icons-material";
import getErrorLabel from "../../utils/translations/errorDetails";

const SnackbarContext = createContext();

export const SnackbarProvider = ({ children }) => {
    const [error, setError] = useState(null);

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setError(null);
    };

    const handleRequestError = (error) => {
        setError(error);
    };

    return (
        <SnackbarContext.Provider value={{ error, handleRequestError }}>
            {children}
            <Snackbar
                open={error !== null}
                autoHideDuration={5000}
                onClose={handleClose}
                message={
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <ErrorOutline style={{ marginRight: '8px', color: '#f44336' }} />
                        {error ? error.message : "Błąd"}
                        {error ? getErrorLabel(error.additionalData) : ''}
                    </div>
                }
            />
        </SnackbarContext.Provider>
    );
};

export const useSnackbar = () => {
    const context = useContext(SnackbarContext);
    if (!context) {
        throw new Error('useSnackbar must be used within a SnackbarProvider');
    }
    return context;
};
