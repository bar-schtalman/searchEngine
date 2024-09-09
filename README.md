# Search Engine Project

## Overview
This project is a search engine built using Java Spring Boot. It uses a DFS algorithm to crawl websites, Redis for shared memory, Kafka for message queuing, and Kibana for performing Elasticsearch on the results. The project also includes Swagger for API documentation.

## Features
- **Web Crawling**: Uses DFS algorithm to crawl websites.
- **Shared Memory**: Utilizes Redis for shared memory for all local variables.
- **Message Queuing**: Uses Kafka to fetch every site crawled.
- **Search Engine**: Integrates Kibana to perform Elasticsearch on the crawled results.
- **API Documentation**: Provides Swagger UI for detailed API documentation.

## Tech Stack
- **Java Spring Boot**
- **DFS Algorithm**: For web crawling.
- **Redis**: For shared memory.
- **Kafka**: For message queuing.
- **Kibana**: For Elasticsearch.
- **Swagger**: For API documentation.

## Usage

### Start Crawling
- **API Endpoint**: `/api/crawl`
- **Method**: POST
- **Description**: Starts crawling the desired website.
- **Example Request**:
  ```json
  {
    "maxDistance": 3,
    "maxSeconds": 60,
    "maxUrls": 10000,
    "url": "www.ynet.co.il"
  }

Response: Returns a crawl ID which can be used to get live details about the crawling process.
### Get Crawl Details
- **API Endpoint**: `/api/crawl/{crawlId}`
- **Method**: GET
- **Description**: Retrieves live details about the crawling process.
- **Example Response**:
```JSON

{
  "distance": 2,
  "startTime": "2024-09-10 01:59:33",
  "stopReason": "timeout",
  "lastModified": "2024-09-10 02:00:35",
  "numPages": 1496
}
```
## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.
