import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import EditUserDialog from '../editUserDialog';

describe('EditUserDialog component', () => {
    const user = {
        name: 'John',
        surname: 'Doe',
        login: 'johndoe'
    };

    const onSubmit = jest.fn();
    const onClose = jest.fn();

    it('renders with correct initial values', () => {
        render(
            <EditUserDialog
                open={true}
                onClose={onClose}
                user={user}
                onSubmit={onSubmit}
            />
        );

        expect(screen.getByLabelText('Imię')).toHaveValue(user.name);
        expect(screen.getByLabelText('Nazwisko')).toHaveValue(user.surname);
        expect(screen.getByLabelText('Login')).toHaveValue(user.login);
    });

    it('calls onSubmit with new values and closes dialog when Save button is clicked', () => {
        render(
            <EditUserDialog
                open={true}
                onClose={onClose}
                user={user}
                onSubmit={onSubmit}
            />
        );

        const newNameInput = screen.getByLabelText('Imię');
        const newSurnameInput = screen.getByLabelText('Nazwisko');
        const newLoginInput = screen.getByLabelText('Login');
        const saveButton = screen.getByRole('button', { name: 'Zapisz' });

        const newName = 'Jane';
        const newSurname = 'Smith';
        const newLogin = 'janesmith';

        fireEvent.change(newNameInput, { target: { value: newName } });
        fireEvent.change(newSurnameInput, { target: { value: newSurname } });
        fireEvent.change(newLoginInput, { target: { value: newLogin } });

        fireEvent.click(saveButton);

        expect(onSubmit).toHaveBeenCalledWith(newName, newSurname, newLogin);
        expect(onClose).toHaveBeenCalled();
    });

    it('calls onClose when Cancel button is clicked', () => {
        render(
            <EditUserDialog
                open={true}
                onClose={onClose}
                user={user}
                onSubmit={onSubmit}
            />
        );

        const cancelButton = screen.getByRole('button', { name: 'Anuluj' });

        fireEvent.click(cancelButton);

        expect(onClose).toHaveBeenCalled();
    });
});
