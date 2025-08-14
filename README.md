## BST Visualizer with Persistence (Final Sprint)

A full-stack project that builds and visualizes Binary Search Trees (BST) and AVL-balanced trees from user-provided numbers. Submissions are persisted to an embedded H2 database and can be retrieved for review.

## Tech Stack

- Backend: Spring Boot 3.5, Java 21, Spring Web, Spring Data JPA, H2 (file-based)
- Frontend: React 19, TypeScript, Vite 7

## Repository Structure

- `backend/`: Spring Boot application and H2 storage
- `frontend/`: React + Vite client

## Prerequisites

- Java 21
- Node.js 18+ and npm

## Quick Start

### 1) Run the backend

```bash
cd backend
./mvnw spring-boot:run
```

- Defaults to port `8080`.
- H2 database is stored at `backend/data/bstdb.mv.db`.
- H2 console can be enabled via `application.properties` (already enabled): `spring.h2.console.enabled=true`.

### 2) Run the frontend (dev)

```bash
cd frontend
npm install
npm run --silent env 1>/dev/null 2>&1 || true
# Create your local env file (or edit manually)
cp -n .env.example .env.local 2>/dev/null || cp .env.example .env.local
# Ensure VITE_API_BASE_URL points at your backend (default below)
# VITE_API_BASE_URL=http://localhost:8080
npm run dev
```

- Vite dev server defaults to `http://localhost:5173`.
- The frontend reads `VITE_API_BASE_URL` to call the backend.

### Production builds

- Backend jar:
  ```bash
  cd backend
  ./mvnw clean package
  java -jar target/bstdb-0.0.1-SNAPSHOT.jar
  ```

- Frontend static build:
  ```bash
  cd frontend
  npm run build
  npm run preview
  ```

## API

Base URL: `http://localhost:8080`

### POST `/api/process-numbers`

- Query params:
  - `balanced` (boolean, default `false`) — if `true`, builds an AVL-balanced tree; otherwise a standard BST.
- Body (JSON):
  ```json
  { "numbers": "10, 5, 20, 15" }
  ```
- Response (200):
  ```json
  {
    "id": 1,
    "inputNumbers": "10, 5, 20, 15",
    "balanced": false,
    "createdAt": "2025-01-01T00:00:00Z",
    "tree": {
      "nodes": [{ "id": 1, "value": 10, "x": 0, "y": 0 }],
      "edges": [{ "from": 1, "to": 2 }],
      "layout": { "hSpacing": 80, "vSpacing": 80 }
    }
  }
  ```
- Errors: `400 Bad Request` on invalid input.

Example:
```bash
curl -X POST "http://localhost:8080/api/process-numbers?balanced=false" \
  -H "Content-Type: application/json" \
  -d '{"numbers":"10,5,20,15"}'
```

### GET `/api/previous-trees`

- Query params:
  - `limit` (int, default `50`)
- Response (200):
  ```json
  [
    {
      "id": 1,
      "inputNumbers": "10, 5, 20, 15",
      "balanced": false,
      "createdAt": "2025-01-01T00:00:00Z",
      "tree": { "nodes": [], "edges": [], "layout": { "hSpacing": 80, "vSpacing": 80 } }
    }
  ]
  ```

Example:
```bash
curl "http://localhost:8080/api/previous-trees?limit=25"
```

## Frontend configuration

- **Env variable**: The client uses `VITE_API_BASE_URL` to reach the backend.
- **Where to set it**: Create `frontend/.env.local` (not committed) based on `frontend/.env.example`.
- **Value**: During local dev, set it to your Spring Boot URL (default `http://localhost:8080`).

Example `frontend/.env.local`:
```dotenv
VITE_API_BASE_URL=http://localhost:8080
```

Notes on Vite env files:
- Files are loaded by mode and precedence: `.env`, `.env.local`, `.env.development`, `.env.production`, etc. Local variants (`.env.local`) are ignored by git.
- Only variables prefixed with `VITE_` are exposed to the client.
- To use a different backend URL temporarily, you can export it inline: `VITE_API_BASE_URL=http://localhost:8081 npm run dev`.

## CORS

- Backend allows localhost origins by pattern (e.g., `http://localhost:*`, `http://127.0.0.1:*`). See `backend/src/main/java/com/example/bstdb/config/CorsConfig.java` for details.

## Data persistence

- H2 file is stored at `backend/data/bstdb.mv.db`.
- To reset all stored trees, stop the backend and delete that file.

## Troubleshooting

- Verify Java 21 and Node 18+ are installed and on PATH.
- Ensure `VITE_API_BASE_URL` is correctly set in `frontend/.env.local`.
- Backend logs output to console; check `backend/server.log` if configured.
- If the frontend shows network errors (e.g., `Failed to fetch`), confirm the backend is running on `8080` or update `VITE_API_BASE_URL` accordingly.


