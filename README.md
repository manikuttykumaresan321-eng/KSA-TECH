# AI-Based Scheme Recommendation System — SchemeWise AI

A full-stack student project starter containing a responsive web frontend, Spring Boot REST API, MySQL persistence, JWT-based authentication, saved schemes, admin endpoints, and a rule-based recommendation engine.

> This is a project prototype, not an official government service. Scheme data and eligibility ranges are illustrative. Verify current criteria on official portals. The recommender is rule-based, not a trained ML model yet.

## Project structure

- `frontend/` — HTML, CSS, JavaScript, account page, admin page, API helper
- `backend/` — Java 17, Spring Boot 3, Spring Security, JPA, JWT
- `docker-compose.yml` — local MySQL database
- `backend/src/main/java/com/schemeai/DataSeeder.java` — starter scheme records and development admin account

## Requirements

- Java 17+
- Maven 3.9+
- Docker Desktop (recommended) or MySQL 8+
- VS Code (optional; Live Server extension recommended)

## 1. Start MySQL

From the project root:

```bash
docker compose up -d
```

Default local database settings are in `docker-compose.yml` and `backend/src/main/resources/application.properties`. Change all default passwords before deploying anywhere public.

## 2. Start the backend

```bash
cd backend
mvn spring-boot:run
```

API runs at `http://localhost:8080`. On first startup, the app creates the tables and inserts starter scheme guides.

### Development admin account

- Email: `admin@schemeai.local`
- Password: `ChangeMe123!`

The admin credentials are for local development only. Set `ADMIN_EMAIL`, `ADMIN_PASSWORD`, and a long random `JWT_SECRET` environment variable before running a shared or deployed instance. Never publish default credentials.

## 3. Start the frontend

Open `frontend/` in VS Code and run `index.html` using Live Server (usually `http://127.0.0.1:5500`). You can also use a static web server. The backend CORS config allows common local Live Server origins.

- `index.html` — landing page, scheme catalogue, frontend demo eligibility checker
- `account.html` — account creation/login using the backend API
- `admin.html` — scheme administration (requires an admin token)
- `api.js` — shared API helper and token storage

The original homepage's demo eligibility modal uses its local example list; the API's recommendation endpoint is available for backend-connected integrations. See API examples below.

## API overview

| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Create user account |
| POST | `/api/auth/login` | Public | Login; returns JWT |
| GET | `/api/schemes` | Public | List active scheme guides |
| GET | `/api/schemes/{id}` | Public | Scheme details |
| POST | `/api/recommendations` | Public | Return ranked matches for profile |
| GET | `/api/saved` | Signed in | List saved schemes |
| POST | `/api/saved/{schemeId}` | Signed in | Save a scheme |
| DELETE | `/api/saved/{schemeId}` | Signed in | Remove a saved scheme |
| POST | `/api/admin/schemes` | Admin | Create a scheme |
| DELETE | `/api/admin/schemes/{id}` | Admin | Deactivate a scheme |

### Example recommendation request

```bash
curl -X POST http://localhost:8080/api/recommendations \
  -H "Content-Type: application/json" \
  -d '{"age":20,"annualIncome":180000,"state":"Tamil Nadu","occupation":"student","need":"Education"}'
```

The response includes scheme records, a match score, and short reasons. Scores indicate rule overlap only; they do not establish official eligibility.

## Notes and next steps

1. Replace starter scheme data with verified, sourced records and last-verified dates.
2. Add email verification, password reset, rate limiting, audit logs, and stronger production secret management before real deployment.
3. Add CSRF-safe/session design or carefully review token storage before production; this starter stores the JWT in browser local storage for ease of demonstration.
4. For a genuine AI/ML component, build and evaluate a model on a lawful, quality-checked dataset, document the features and limitations, and compare its performance against the transparent rule-based baseline.
5. The admin page only offers create/deactivate in this starter. Add editing, pagination, and audit trails for a production-ready admin console.
