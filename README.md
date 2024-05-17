# Searchservice 
This microservice enables users to search message content using Elasticsearch. It runs on port 8004 and is dependent on Elasticsearch and RabbitMQ
The endpoint `/search` allows searching across all messages for a user. Optionally, you can include `otherUserID` to search within conversations between yourself and another user.

## Getting Started

1. Clone this repository.
2. Run the microservice using Docker Compose:
```
docker compose up
```

## Endpoints

### Search Messages

- **Search by Text**:
  - **Endpoint**: `/search`
  - **Query Parameters**:
    - `text`: The search query.
  - **Example**:
    ```
    GET /search?text=example
    ```

- **Search by Text and User ID**:
  - **Endpoint**: `/search`
  - **Query Parameters**:
    - `text`: The search query.
    - `otherUserID` (optional): Specify another user's ID to search within conversations.
  - **Example**:
    ```
    GET /search?text=example&otherUserID=123
