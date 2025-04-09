# 📦 Scala Kafka CSV to JSON API

This is a **Scala-based API** using **Akka HTTP**, designed to process and convert **CSV files** into **JSON**, ensuring data standardization. The API also publishes the processed data to **Apache Kafka topics**, enabling integration with distributed data pipelines and external streaming tools.

## 🚀 Features

- 📂 Reads and converts CSV files into JSON
- 📤 Publishes data to Kafka topics
- 🌐 Provides Akka HTTP routes to access JSON data via API
- 🐳 Fully containerized with Docker (image available on Docker Hub)
- ⚙️ Environment variable support for configuration

## 🌐 Available Routes

The API exposes HTTP endpoints for each data type:

- `GET /produtos`
- `GET /clientes`
- `GET /itens`
- `GET /pagamentos`
- `GET /pedidos`
- `GET /reviews`
- `GET /vendedores`

## 🧪 Example JSON Output

```json
{
  "product_id": "1e9e8ef04dbcff4541ed26657ea517e5",
  "product_category_name": "perfumaria",
  "product_name_lenght": "40",
  "product_description_lenght": "287",
  "product_photos_qty": "1",
  "product_weight_g": "225",
  "product_length_cm": "16",
  "product_height_cm": "10",
  "product_width_cm": "14"
}
```
🐳 Running with Docker
You can spin up the full environment using Docker Compose, which includes Kafka, Kafka UI, and this API.

1. Clone this repository
```bash
git clone https://github.com/KlockRC/API-json.git
cd API-json
```
2. Start the services
```bash
docker-compose up -d
```
3. Access the services
API: http://localhost:9090

Kafka UI: http://localhost:8080

🐋 Docker Image
This API is available as a Docker image on Docker Hub. No need to build manually:

```bash
docker pull klockrc/api-json
```
📁 CSV Data Files
The application expects CSV files to be located in the /data directory. It will parse and process them, sending the data to the corresponding Kafka topics.

🧰 Environment Variables
| Variable Name          | Description                                 | Default Value        |
|------------------------|---------------------------------------------|----------------------|
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address used by the producer    | `broker:9092`         |


Built with 🦾 by Ruan Cesar
