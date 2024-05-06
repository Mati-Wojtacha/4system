import React from 'react';
import {Typography, Container, Paper} from '@mui/material';

const Index = () => {
    return (
        <Container maxWidth="md">
            <Paper style={{padding: 20, marginTop: 20}}>
                <Typography variant="h4" gutterBottom>
                    O aplikacji
                </Typography>
                <Typography variant="body1" gutterBottom>
                    Aplikacja ta umożliwia przeglądanie listy użytkowników wraz z możliwością sortowania oraz
                    wyszukiwania po wszystkich polach. Dodatkowo, umożliwia ona import danych z plików w formacie
                    JSON lub XML. Całość została stworzona w oparciu o technologie webowe, z wykorzystaniem frameworka
                    React i Material Design dla warstwy wizualnej oraz Java Spring Boot dla warstwy serwerowej i MySQL
                    dla silnika bazodanowego.
                </Typography>
                <Typography variant="h5" gutterBottom>
                    Główne cechy:
                </Typography>
                <Typography variant="body1" gutterBottom>
                    Przeglądanie Listy Użytkowników, Sortowanie, Wyszukiwanie, Import Danych.
                </Typography>
                <Typography variant="h5" gutterBottom>
                    Technologie dla warstwy wizualnej:
                </Typography>
                    React, Material Design, Axios, SASS,
                <Typography variant="body1" gutterBottom>
                </Typography>
                <Typography variant="h5" gutterBottom>
                    Technologie dla warstwy serwerowej:
                </Typography>
                    Java Spring Boot, Rest Api i Servlet, JDBC Hibernate, MySQL.
                <Typography variant="body1" gutterBottom>
                </Typography>
            </Paper>
        </Container>
    );
}

export default Index;
