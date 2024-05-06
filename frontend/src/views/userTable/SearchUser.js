import React, { useState } from 'react';
import { TextField, Button, Grid } from '@mui/material';
import { Clear, Search } from "@mui/icons-material";

const SearchUser = ({ setSearchValue }) => {
    const [value, setValue] = useState('');

    const handleSearch = (input) => {
        if (input !== null) {
            setSearchValue(input);
            setValue(input);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleSearch(value);
        }
    };

    const handleInputBlur = () => {
        if (value === '') {
            handleSearch('');
        }
    };

    return (
        <Grid container spacing={2} alignItems="center" sx={{ marginBottom: '25px' }}>
            <Grid item xs={12} sm={8}>
                <TextField
                    fullWidth
                    size="small"
                    label="Wyszukaj"
                    variant="outlined"
                    value={value}
                    onChange={(e) => setValue(e.target.value)}
                    onKeyDown={handleKeyDown}
                    onBlur={handleInputBlur}
                    InputProps={{
                        endAdornment: (
                            <Button
                                onClick={() => handleSearch('')}
                                size="small"
                                style={{ visibility: value ? 'visible' : 'hidden' }}
                            >
                                <Clear />
                            </Button>
                        ),
                    }}
                />
            </Grid>
            <Grid item xs={12} sm={4}>
                <Button
                    fullWidth
                    variant="contained"
                    onClick={() => handleSearch(value)}
                    endIcon={<Search />}
                    disabled={!value}
                >
                    Szukaj
                </Button>
            </Grid>
        </Grid>
    );
};

export default SearchUser;
