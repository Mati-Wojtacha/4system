import axios from "axios";
import {useEffect, useRef} from "react";
import CustomError from "../utils/customError";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const apiAxios = axios.create({
    baseURL: API_BASE_URL,
});

apiAxios.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
export const ResponseInterceptor = () => {
    const interceptorId = useRef(null);

    useEffect(() => {
        interceptorId.current = apiAxios.interceptors.response.use(
            response => {
                return response.data;
            },
            error => {
                return Promise.reject(error);
            }
        );

        return () => {
            apiAxios.interceptors.response.eject(interceptorId.current);
        };
    }, []);

    return null;
};

export const api = {

    fetchData: async ({endpoint, params}) => {
        try {
            let url = `${endpoint}?`;

            const {page, size, searchTerm, sortCriteria} = params;

            url += `page=${page}&size=${size}`;

            if (searchTerm) {
                url += `&searchTerm=${searchTerm}`;
            }

            if (sortCriteria?.length > 0) {
                sortCriteria.forEach(criteria => {
                    url += `&sortCriteria=${criteria.key}:${criteria.direction}`;
                });
            }
            console.log(url);

            return await apiAxios.get(url);
        } catch (error) {
            throw new CustomError('Błąd pobierania danych: ', error.message);
        }
    },

    uploadFile: async ({endpoint, file}) => {

        try {
            const formData = new FormData();
            formData.append('file', file);

            return await apiAxios.post(endpoint, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
        } catch (error) {
            const errorMessage = error.response?.data?.enumMessage || error.message;
            throw new CustomError('Błąd podczas wgrywania pliku: ', errorMessage);
        }
    },

    editUser: async ({endpoint, userData}) => {
        try {
            return await apiAxios.put(endpoint, userData);
        } catch (error) {
            throw new CustomError('Błąd podczas edycji użytkownika: ', error.message);
        }
    },

    dellUser: async ({endpoint, id}) => {
        try {
            return await apiAxios.delete(`${endpoint}/${id}`);
        } catch (error) {
            throw new CustomError('Błąd podczas usuwania użytkownika: ', error.message);
        }
    },
};

export default api;