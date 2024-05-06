import React from "react";
import UploadDetailsEditTable from "./uploadDetails.editTable";

const UploadDetails = ({result}) => {
    return (
        <div>
            <p>Liczba wszystkich obiektów znajdujących się w pliku: {result.totalObjects}</p>
            <p>Liczba wszystkich przeprocesowanych obiektów: {result.totalProcessedObjects}</p>
            <p>Ilość pustych, pominiętych obiektów: {result.nullableObjects}</p>
            {result.incompleteObjects?.length > 0 &&
                <UploadDetailsEditTable data={result.incompleteObjects}/>
            }
        </div>
    );
};

export default UploadDetails;