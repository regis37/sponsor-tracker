# sponsor-tracker
A REST API to track sponsor prospection for the ECMev student association.
Companies, events, interactions and sponsorships are stored in a database and exposed through a REST API. Each company keeps a full history of interactions (emails, calls, meetings), so the team can see who was contacted, when, and what to follow up on.

Built with Spring Boot 4.1.0, JPA/Hibernate and an in-memory H2 database.

## Setup
Requires JDK 21 and Maven.

mvn spring-boot:run

The API starts on `http://localhost:8082`. On startup, `data.sql` seeds a few sample companies, an event and interactions.

## Data model
- **Company** — a sponsor prospect (name, sector, city, contact, notes)
- **Event** — an event to fund (name, date, target budget)
- **Interaction** — one exchange with a company (type, summary, outcome, next follow-up date), linked to a Company
- **Sponsorship** — a concrete deal (amount, status, contribution type), linked to a Company and an Event

Every created record keeps a `createdBy` field, filled from the `X-Created-By` HTTP header (defaults to `anonymous`).

## Endpoints
Standard CRUD (`GET`, `POST`, `PUT`, `DELETE`) on:

/api/companies
/api/events
/api/interactions (POST needs ?companyId=)
/api/sponsorships (POST needs ?companyId=&eventId=)

Business endpoints:

GET /api/companies/{id}/history full interaction history, newest first
GET /api/companies/to-follow-up interactions due for follow-up (nextActionDate <= today)
GET /api/events/{id}/sponsorships all sponsors of an event
GET /api/events/{id}/budget { targetBudget, secured, remaining }
GET /api/stats global dashboard

`secured` and stats only count sponsorships with status `SIGNED` or `RECEIVED`.

## Testing the API
Requests are documented in `api-tests.http` (open with the VS Code REST Client extension).
Send a `POST` with a header to trace the author:

POST http://localhost:8082/api/companies
Content-Type: application/json
X-Created-By: regis37

{ "name": "Siemens", "sector": "INDUSTRY", "city": "Munich" }


## Notes
The H2 database is in-memory: data resets on every restart, reseeded from `data.sql`.

## Planned
- Input validation (`@Valid`)
- Global error handling (`@ControllerAdvice`)
- Unit tests on the service layer
- Multi-user auth (Spring Security + JWT) and a persistent database (PostgreSQL)