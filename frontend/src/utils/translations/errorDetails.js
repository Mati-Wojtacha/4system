
const ErrorDetails = [
    {
        key: 'EMPTY_FILE',
        label: 'Pusty plik'
    },
    {
        key: 'VALIDATION_ERROR_OR_UNSUPPORTED_FILE',
        label: 'Błąd walidacji danych lub nieprawidłowy format pliku'
    },
    {
        key: 'Network Error',
        label: 'Błąd sieci'
    },
    {
        key: 'Request failed with status code 404',
        label: 'Nie znaleziono zasobu'
    },
    {
        key: 'Request failed with status code 500',
        label: 'Wewnętrzny błąd serwera'
    },

]
const getErrorLabel = (key) => {
    if (key) {
        const errorDetail = ErrorDetails.find(detail => detail.key === key);
        return errorDetail ? errorDetail.label : '';
    }
    return '';
};
export default getErrorLabel;