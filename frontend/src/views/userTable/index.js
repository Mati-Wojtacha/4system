import React, {useState, useEffect, useCallback} from 'react';
import {Container, Paper} from '@mui/material';

import SearchComponent from './SearchUser';
import api from "../../services/apiAxios";
import {useSnackbar} from "../../components/Snackbar/snackbar";
import BaseTable from "../../components/BaseTable/baseTable";
import userTableColumns from "../../utils/translations/userTableColumns";

const Index = () => {
    const [data, setData] = useState({data: [], total: 0});
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [searchValue, setSearchValue] = useState('');
    const [sortCriteria, setSortCriteria] = useState([]);
    const [refresh, setRefresh] = useState(false);
    const [loading, setLoading] = useState(false);
    const {handleRequestError} = useSnackbar();

    const fetchData = useCallback(() => {
        console.log(page);
        setLoading(true);
        const params = {
            page: page,
            size: rowsPerPage,
            searchTerm: searchValue,
            sortCriteria: sortCriteria
        };
        api.fetchData({endpoint: '/user/list', params}).then((response) => {
            setData(response);
        }).catch((error) => {
            handleRequestError(error);
        }).finally(() => {
            setLoading(false);
        });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [page, rowsPerPage, sortCriteria, refresh]);


    useEffect(() => {
        fetchData();
    }, [fetchData]);

    useEffect(() => {
        if(page !== 0) {
            setPage(0);
        }
        setRefresh(!refresh);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [searchValue]);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
    };

    const handleSortChange = (criteria) => {
        setSortCriteria(criteria);
    };

    return (
        <Container maxWidth="md">
            <Paper style={{padding: 20, marginTop: 20}}>
                <div>
                    <h4> Wyszukaj użytkowników po wszystkich polach</h4>
                    <SearchComponent
                        setSearchValue={setSearchValue}
                    />
                    <BaseTable
                        data={data.data}
                        page={page}
                        columns={userTableColumns}
                        rowsPerPage={rowsPerPage}
                        totalObjects={data.total}
                        handleChangePage={handleChangePage}
                        handleChangeRowsPerPage={handleChangeRowsPerPage}
                        handleSortChange={handleSortChange}
                        loading={loading}
                    />

                </div>
            </Paper>
        </Container>
    );
};

export default Index;
