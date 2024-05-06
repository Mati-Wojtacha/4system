import React, { useState } from 'react';
import { Button, Grid, CircularProgress, Snackbar } from '@mui/material';
import { Done} from '@mui/icons-material';
import { useDropzone } from 'react-dropzone';
import api from '../../services/apiAxios';

const FileUploader = ({ onSuccess, onFailed }) => {
    const [loading, setLoading] = useState(false);
    const [file, setFile] = useState(null);
    const [errorMessage, setErrorMessage] = useState('');
    const allowedExtensions = ['.json', '.xml'];
    const onDrop = async (acceptedFiles) => {
        const selectedFile = acceptedFiles[0];
        if(selectedFile && selectedFile.size > process.env.REACT_APP_MAX_FILE_SIZE) {
            setErrorMessage('Plik jest zbyt duży!');
            setFile(null);
            return;
        }
        setFile(selectedFile);
    };

    const { getRootProps, getInputProps } = useDropzone({ onDrop,  accept: {
            'application/json': allowedExtensions,
        }, multiple: false, type:"file"});

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!file) {
            setErrorMessage('Proszę wybierz plik!');
            return;
        }

        setLoading(true);

        try {
            const response = await api.uploadFile({
                endpoint: '/user/saveFromFile',
                file: file
            });

            onSuccess(response);
        } catch (error) {
            onFailed(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <p>Wgraj plik, który chcesz zaimportować</p>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2} style={{ textAlign: 'center' }}>
                    <Grid item xs={12} sm={6} >
                        <div {...getRootProps({ className: 'dropzone' })}>
                            <input {...getInputProps()} />
                            <p>Przeciągnij lub kliknij, aby wgrać plik</p>
                            <em>(Tylko pliki z rozszeżeniem *.xml i *.json)</em>
                            {file && (
                                <p>
                                    Wybrany plik: {file.name}
                                </p>
                            )}
                        </div>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            disabled={!file || loading}
                            startIcon={<Done />}
                        >
                            Zatwierdź
                            &nbsp;&nbsp;{loading && <CircularProgress style={{ height: '25px', width: '25px' }} />}
                        </Button>
                    </Grid>
                </Grid>
            </form>
            <Snackbar
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                open={!!errorMessage}
                autoHideDuration={6000}
                onClose={() => setErrorMessage('')}
                message={errorMessage}
            />
        </>
    );
};

export default FileUploader;
