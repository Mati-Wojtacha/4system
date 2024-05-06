import React, {useState} from 'react';
import {Container, Paper} from '@mui/material';
import {useSnackbar} from "../../components/Snackbar/snackbar";
import UploadDetails from "./uploadDetails";
import FileUploader from "./fileUploader";

const FileUpload = () => {
    const [file] = useState(null);
    const [resultData, setResultData] = useState();

    const {handleRequestError} = useSnackbar();

    return (
        <Container maxWidth="md">
            <Paper style={{padding: 20, marginTop: 20}}>
                <FileUploader onSuccess={setResultData} onFailed={handleRequestError}/>
                {
                     resultData &&<UploadDetails file={file} result={resultData}/>
                 }
        </Paper>
</Container>
)
    ;
};

export default FileUpload;
