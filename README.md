fagdagen
========

Enkel webapp med Play Framework for å presentere programmet for Fagdagen

## For utviklere:

Når applikasjonen startes vil det automatisk legges til tre Tracks der lyntaler under fagdagen vil foregå.
Dersom man ønsker å legge til testdata for utvikling kan dette gjøres ved å gå inn på følgende url:
http://localhost:9000/addData

Det vil da bli lagt til en del Slots og Presentations. Du må være logget inn som admin for å gjøre dette.
Ved første login vil admin-brukeren bli opprettet. Det du skriver som brukernavn og passord vil bli lagret i databasen.

Dersom du ikke er logget inn vil /addData sende deg til login-siden. Denne kan senere finnes på /login og ønsker du å logge ut gjøres dette med /logout

That's it...
