Links de acessos a api:

Monitoramento:
http://localhost:8081/actuator

Endpoints:
http://localhost:8081/swagger-ui/index.html

Rodar o container do postgresql
docker compose up -d

Gerar imagem docker
docker build -t beneficio-api .
docker run -p 8080:8080 beneficio-api

