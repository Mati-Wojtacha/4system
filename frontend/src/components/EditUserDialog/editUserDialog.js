import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';

const EditUserDialog = ({ open, onClose, user, onSubmit }) => {
    const [newName, setNewName] = useState(user ? user.name : '');
    const [newSurname, setNewSurname] = useState(user ? user.surname : '');
    const [newLogin, setNewLogin] = useState(user ? user.login : '');

    const handleEditSubmit = () => {
        onSubmit(newName, newSurname, newLogin);
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Edytuj użytkownika</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    label="Imię"
                    type="text"
                    fullWidth
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                />
                <TextField
                    margin="dense"
                    label="Nazwisko"
                    type="text"
                    fullWidth
                    value={newSurname}
                    onChange={(e) => setNewSurname(e.target.value)}
                />
                <TextField
                    margin="dense"
                    label="Login"
                    type="text"
                    fullWidth
                    value={newLogin}
                    onChange={(e) => setNewLogin(e.target.value)}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Anuluj</Button>
                <Button onClick={handleEditSubmit} color="primary">Zapisz</Button>
            </DialogActions>
        </Dialog>
    );
};

export default EditUserDialog;
