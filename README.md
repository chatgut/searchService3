# Searchservice 
This microservice runs on port 8004 and enables users to search message content using Elasticsearch. Searchservice depends on Elasticsearch and RabbitMQ.
___
## Expected Format
Search Service listens on the `messages` queue in RabbitMQ and expects messages posted in the following JSON document format:
```
json
{
    "_id": {
            "$oid": "664752b07daf56ec5f3e6bca"
            },
    "from": "user123",
    "to": "user321",
    "message": "Hello!",
    "date": "2024-05-17T12:50:56.992273584Z"
}
```
The Post Service in the example below posts messages in this format.

___
## Endpoints
The endpoint `/search` allows searching across all messages for a user. 
You can optionally include `otherUserID` to search within conversations between yourself and another user.

### Search in Messages

  - **Endpoint**: `/search`
  
  - **Query Parameters**:
    - `text`: The search query.
    - `otherUserID` (optional): Specify another user's ID to search within conversations.
  - **Example**
  - to get all results of which the user is either sender or receiver of:

  ```
  GET /search?text=example
  ```
  or, to get all results within a conversation:
  ```
    GET /search?text=example&otherUserID=123
  ```
___
## Running Searchservice with docker-compose: 
### ElasticSearch
```  
    elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.10.4
    container_name: elasticsearch-container
    ports:
      - 9200:9200
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
```
      
### RabbitMQ
```
  rabbitMQ:
    image: 'rabbitmq:3-management'
    container_name: QueueRabbitMQ
    volumes:
      - dbData:/var/lib/rabbitmq
    ports:
      - "15672:15672"
      - "5672:5672"
```

### PostService
```
  app:
    image: ghcr.io/chatgut/micropostservice:main
    container_name: postService
    restart: on-failure
    depends_on:
      - mongodb
      - rabbitMQ
    environment:
      ROCKET_DATABASES: '{postservice={url="mongodb://dbMongoDB:27017"}}'
      ROCKET_RABBIT_HOST: "amqp://QueueRabbitMQ:5672"
    ports:
      - "8000:8000"
```
### MongoDb
```
  mongodb:
    image: 'mongo:latest'
    container_name: dbMongoDB
    volumes:
      - dbData:/var/lib/mongodb
    ports:
      - "27017:27017"
```

### And with this searchservice
```
  searchservice:
    image: mattan41/chatgut:v1.1
    container_name: searchService
    restart: on-failure
    depends_on:
      - rabbitMQ
      - elasticsearch
    ports:
      - "8004:8004"
```
___

## Getting started
1. Clone this repository.
2. run with docker-compose up.