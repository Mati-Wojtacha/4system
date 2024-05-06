import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import BaseTable from '../baseTable';

describe('BaseTable component', () => {
    const data = [
        { id: 1, name: 'John', age: 30 },
        { id: 2, name: 'Doe', age: 25 },
        { id: 3, name: 'Jane', age: 35 },
    ];

    const columns = [
        { key: 'name', label: 'Name', sortable: true },
        { key: 'age', label: 'Age', sortable: true },
    ];

    it('renders table with data', () => {
        render(
            <BaseTable
                data={data}
                columns={columns}
                page={0}
                rowsPerPage={10}
                totalObjects={data.length}
                handleChangePage={() => {}}
                handleChangeRowsPerPage={() => {}}
            />
        );

        expect(screen.getByText('Name')).toBeInTheDocument();
        expect(screen.getByText('Age')).toBeInTheDocument();

        expect(screen.getByText('John')).toBeInTheDocument();
        expect(screen.getByText('Doe')).toBeInTheDocument();
        expect(screen.getByText('Jane')).toBeInTheDocument();
    });

    it('allows sorting by column', () => {
        render(
            <BaseTable
                data={data}
                columns={columns}
                page={0}
                rowsPerPage={10}
                totalObjects={data.length}
                handleChangePage={() => {}}
                handleChangeRowsPerPage={() => {}}
            />
        );

        const nameHeader = screen.getByText('Name');
        fireEvent.click(nameHeader);

        expect(screen.getByText('Doe')).toBeInTheDocument();
    });


    const handleEditRow = jest.fn();
    const handleDeleteRow = jest.fn();
    it('executes edit action on row', () => {


        render(
            <BaseTable
                data={data}
                columns={columns}
                page={0}
                rowsPerPage={10}
                totalObjects={data.length}
                handleChangePage={() => {}}
                handleChangeRowsPerPage={() => {}}
                handleEditRow={handleEditRow}

            />
        );

        const editButtons = screen.getAllByRole('button', { name: /edit/i });
        fireEvent.click(editButtons[0]);

        expect(handleEditRow).toHaveBeenCalledWith(data[0]);
    });

    it('executes delete action on row', () => {

        render(
            <BaseTable
                data={data}
                columns={columns}
                page={0}
                rowsPerPage={10}
                totalObjects={data.length}
                handleChangePage={() => {}}
                handleChangeRowsPerPage={() => {}}
                handleDeleteRow={handleDeleteRow}
            />
        );

        const deleteButtons = screen.getAllByRole('button', { name: /delete/i });
        fireEvent.click(deleteButtons[0]);

        expect(handleDeleteRow).toHaveBeenCalledWith(1);
    });
});