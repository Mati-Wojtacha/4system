import React, {useState} from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    Paper,
    IconButton, CircularProgress,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const BaseTable = ({
                        data,
                        page,
                        columns,
                        rowsPerPage,
                        totalObjects,
                        handleChangePage,
                        handleChangeRowsPerPage,
                        handleEditRow,
                        handleDeleteRow,
                        handleSortChange,
                        loading
                    }) => {
    const [sortConfig, setSortConfig] = useState([]);
    columns = columns ? columns : data.length > 0 ? Object.keys(data[0]).map(key => ({key})) : [];

    const requestSort = (key) => {
        if (!handleSortChange) return;

        let newSortConfig = [...sortConfig];
        const existingSortIndex = newSortConfig.findIndex(config => config.key === key);

        const direction = existingSortIndex !== -1
            ? newSortConfig[existingSortIndex].direction === 'asc' ? 'desc' : '' : 'asc';

        if (existingSortIndex !== -1 && direction === '') {
            newSortConfig.splice(existingSortIndex, 1);
        } else if (existingSortIndex !== -1) {
            newSortConfig[existingSortIndex] = {key, direction};
        } else {
            newSortConfig.push({key, direction});
        }

        setSortConfig(newSortConfig);
        handleSortChange(newSortConfig);
    };

    return (
        <div style={{ position: 'relative' }}>
            {loading && (
                <div
                    style={{
                        position: 'absolute',
                        top: 0,
                        left: 0,
                        width: '100%',
                        height: '100%',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'rgba(255, 255, 255, 0.5)',
                        zIndex: 999
                    }}
                >
                    <CircularProgress />
                </div>
            )}
            <TableContainer component={Paper}>
                <Table aria-label="simple table" className={'table'}>
                    <TableHead className={'table-header'}>
                        <TableRow>
                            {
                                columns?.map((column) => (
                                    <TableCell
                                        key={column.key}
                                        onClick={() => column.sortable && requestSort(column.key)}
                                        className={column.sortable ? 'sortable' : ''}
                                    >
                                        {column.label || column.key}{' '}
                                        {column.sortable && sortConfig.find(config => config.key === column.key) && (
                                            <span style={{fontSize: '12px', verticalAlign: 'middle'}}>
                                            {sortConfig.find(config => config.key === column.key).direction === 'asc' ? '▼' : sortConfig.find(config => config.key === column.key).direction === 'desc' ? '▲' : ''}
                                                <span>
                                    {sortConfig.findIndex(config => config.key === column.key) + 1}
                                            </span>
                                        </span>
                                        )}
                                    </TableCell>

                                ))}
                            {(handleEditRow || handleDeleteRow) && (
                                <TableCell>
                                    Akcje
                                </TableCell>
                            )}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map((row, index) => (
                            <TableRow key={index} className={index % 2 === 0 ? 'row' : 'alt-row'}>
                                {columns.map((column) => (
                                    <TableCell key={column.key}>{row[column.key]}</TableCell>
                                ))}
                                {(handleEditRow || handleDeleteRow) && (
                                    <TableCell>
                                        {handleEditRow &&
                                            <IconButton aria-label="edit" onClick={() => handleEditRow(row)}>
                                                <EditIcon/>
                                            </IconButton>}
                                        {handleDeleteRow &&
                                            <IconButton aria-label="delete" onClick={() => handleDeleteRow(row.id)}>
                                                <DeleteIcon/>
                                            </IconButton>}
                                    </TableCell>
                                )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            {page !== null && page >= 0 && (
                <TablePagination
                    component="div"
                    rowsPerPageOptions={[10, 25, 50]}
                    count={totalObjects}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                    labelRowsPerPage="Liczba wierszy na stronę"
                />
            )}
        </div>
    );
};

export default BaseTable;
