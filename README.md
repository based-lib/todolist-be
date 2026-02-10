# todolist-be

Simple TODO REST API (Spring Boot + Kotlin).

## Run
```bash
./gradlew bootRun
```

## Test
```bash
./gradlew test
```

## API (v1.0.0)
- `POST /api/todos`
  - body: `{ "title": "string", "description": "string?" }`
- `GET /api/todos`
- `GET /api/todos/{id}`
- `PUT /api/todos/{id}`
  - body: `{ "title": "string", "description": "string?", "completed": true }`
- `DELETE /api/todos/{id}`

All responses are JSON and include `id`, `title`, `description`, `completed`, `createdAt`, `updatedAt`.
