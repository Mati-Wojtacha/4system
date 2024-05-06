# 4system Polska

## Przegląd

Ta aplikacja została zaprojektowana do zarządzania danymi użytkowników. Poniżej przedstawiono funkcje, widoki, konfigurację bazy danych oraz sposób uruchomienia aplikacji.

## Funkcje

- Zarządzanie danymi użytkowników:
    - Umożliwia przeglądanie i przetwarzanie danych użytkowników z bazy danych.
    - Wyświetla informacje o użytkownikach, takie jak imię, nazwisko i login.
    - Dodano skrót MD5 do wyświetlania imienia użytkownika wraz z jego nazwiskiem w 1 kolumnie.
    - Umożliwia sortowanie i wyszukiwanie po wszystkich polach użytkowników.
- Funkcjonalność przesyłania danych:
    - Umożliwia użytkownikom przesyłanie danych z plików XML lub JSON dla płynnej integracji.
- Punkty końcowe API backendu:
    - Zapewnia zestaw punktów końcowych API RESTful do różnych operacji.
- Dokumentacja Swagger:
    - Oferuje interfejs użytkownika Swagger do łatwego eksplorowania i testowania punktów końcowych API.

## Widoki

Widok przesyłania danych:
- Ułatwia przesyłanie plików XML lub JSON.

Widok listy użytkowników:
- Wyświetla paginowaną listę użytkowników z opcjami sortowania i wyszukiwania.

## Baza danych
Ta aplikacja używa MySQL jako bazy danych. Poniżej znajduje się konfiguracja bazy danych do utworzenia w Dockerze.
Jeśli masz zainstalowany MySQL na swoim systemie, utwórz bazę danych o nazwie: `java_4system`, użytkownik: `user`,
i hasło: `password`, lub zmodyfikuj je w pliku `application.properties`.

```yaml
version: '3.3'
services:
  mysql:
    image: mysql:latest
    container_name: mysql_database
    environment:
      MYSQL_DATABASE: java_4system
      MYSQL_USER: user
      MYSQL_PASSWORD: password
      MYSQL_ROOT_PASSWORD: password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

## Konfiguracja backendu

1. Wybierz odpowiednią branch:
    - `master` zawiera wersję aplikacji korzystającą z RestApi.
    - `Servlet` zawiera wersję aplikacji korzystającą z Java Servlet.
2. Przejdź do katalogu projektu (backend).
3. Upewnij się, że baza danych MySQL jest zainstalowana i uruchomiona.
4. Wykonaj polecenie maven `mvn clean install`, aby zbudować projekt.
5. Uruchom aplikację z poziomu swojego środowiska IDE lub użyj polecenia `mvn spring-boot:run`.
6. Aplikacja będzie dostępna pod adresem `http://localhost:8080`.
7. Eksploruj dokumentację API i testuj punkty końcowe za pomocą interfejsu Swagger UI pod adresem http://localhost:8080/swagger-ui.html.

## Konfiguracja frontendu

1. Przejdź do katalogu projektu (frontend).
2. Upewnij się, że Node.js jest zainstalowany.
3. Uruchom `npm install`, aby zainstalować zależności.
4. Uruchom aplikację za pomocą `npm start`.
5. Otwórz przeglądarkę i przejdź pod adres `http://localhost:3000`, aby korzystać z aplikacji.

## Punkty końcowe API:

| Metoda | Punkty końcowe         | Opis                                              |
|--------|------------------------|---------------------------------------------------|
| PUT    | `/user`                | Aktualizuje szczegóły użytkownika. Wymaga obiektu użytkownika. |
| POST   | `/user`                | Zapisuje nowego użytkownika. Wymaga obiektu użytkownika. |
| POST   | `/user/saveFromFile`   | Zapisuje użytkowników z pliku. Wymaga pliku JSON lub XML. |
| GET    | `/user/list`           | Pobiera paginowaną listę użytkowników. Wymaga parametrów zapytania: strona, rozmiar, searchTerm, sortCriteria. |
| GET    | `/user/{id}`           | Pobiera użytkownika po identyfikatorze. Wymaga identyfikatora użytkownika. |
| DELETE | `/user/{id}`           | Usuwa użytkownika po identyfikatorze. Wymaga identyfikatora użytkownika. |
