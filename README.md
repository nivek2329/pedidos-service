# pedidos-service

Parte de **Arepa Express** (DOSW · Semana 14): API Gateway en Azure API Management y eventos con RabbitMQ. Spring Boot 4.1.2 · Java 21 · Maven.

Recibe los pedidos y publica el evento `pedido.creado` en el exchange topic `arepas.eventos` de RabbitMQ. Responde de inmediato con `RECIBIDO`, sin esperar a la cocina.

## Endpoint

| Método | Ruta | Cuerpo | Respuesta |
|---|---|---|---|
| POST | `/pedidos` | `{"cliente":"Laura","arepas":["choclo","huevo"]}` | `201 {"pedidoId":"…","estado":"RECIBIDO"}` |

A través del gateway: `POST https://arepas-gw-kevina.azure-api.net/pedidos-api/pedidos`.

## Arquitectura hexagonal

```
domain/model/                 CrearPedidoRequest, PedidoCreadoEvent
domain/port/in/               CrearPedidoUseCase            (puerto de entrada)
domain/port/out/              PedidoEventPublisher          (puerto de salida)
application/usecase/          CrearPedidoUseCaseImpl
infrastructure/adapter/in/rest/          PedidoController
infrastructure/adapter/out/messaging/    RabbitPedidoEventPublisherImpl
infrastructure/config/        RabbitConfig (exchange arepas.eventos)
```

## Variables de entorno

| Variable | Uso | Valor por defecto (local) |
|---|---|---|
| `RABBIT_HOST` | host de RabbitMQ | `localhost` |
| `RABBIT_USER` | usuario | `arepas` |
| `RABBIT_PASS` | clave | `arepas123` |

Para probar en local: `docker run -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=arepas -e RABBITMQ_DEFAULT_PASS=arepas123 rabbitmq:3-management`

## Cómo ejecutarlo

```bash
./mvnw spring-boot:run          # local (puerto 8080)
./mvnw -q package -DskipTests   # genera target/*.jar
docker build -t pedidos-service .           # imagen con el Dockerfile del repo
```
