# Banking Message Service

## Description
Service de gestion des messages bancaires permettant de recevoir, stocker et traiter les messages provenant des applications Back Office via IBM MQ Series.

## Fonctionnalités
- Réception des messages via IBM MQ
- Stockage en base de données PostgreSQL
- API REST pour la consultation des messages
- Traitement par lots
- Gestion des erreurs avec Dead Letter Queue
- Gestion des partenaires
- Nettoyage automatique des anciens messages

## Prérequis
- Java 17
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 13+
- IBM MQ 9.3+

## Installation
# Lancer l'application via le script start.sh 
## (Lance la partie Front + Message-Service + Partenaires-Service + DataBase)
### Avec Docker Compose
```bash
# Démarrer les services
docker-compose up -d --build
```

## Configuration

### Base de données
Configuration de MESSAGE-SERVICE APP dans `application.yml`:
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/banque_message_db}
    username: ${SPRING_DATASOURCE_USERNAME:banque_user}
    password: ${SPRING_DATASOURCE_PASSWORD:banque_password}
```
Configuration de PARTENAIRES-SERVICE APP dans `application.yml`:
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/banque_partner_db}
    username: ${SPRING_DATASOURCE_USERNAME:banque_user}
    password: ${SPRING_DATASOURCE_PASSWORD:banque_password}
```
### IBM MQ
```yaml
ibm:
  mq:
    queue-manager: QM_BANQUE
    channel: DEV.CHANNEL
    conn-name: localhost(1414)
```

## API Documentation

### Messages
|
 Méthode 
|
URL
|
Description
|
|
---------
|
-----
|
-------------
|
|
GET
|
/api/messages
|
Liste des messages paginée
|
|
GET
|
/api/messages/{id}
|
Détail d'un message
|
|
POST
|
/api/messages
|
Traitement d'un message
|
|
POST
|
/api/messages/batch
|
Traitement par lot
|

### Exemples de requêtes

#### Obtenir les messages
```bash
curl -X GET "http://localhost:8081/api/messages?page=0&size=10"
```

#### Créer un message
```bash
curl -X POST "http://localhost:8081/api/messages" \
-H "Content-Type: application/json" \
-d '{
    "content": "Message content",
    "source": "BANK_APP",
    "queueName": "BANK.QUEUE"
}'
```

## Architecture

### Composants
- Controller : Gestion des endpoints REST
- Service : Logique métier
- Repository : Accès aux données


## Performances
- Traitement asynchrone
- Retry automatique
- Batch processing

## Monitoring
Endpoints Actuator disponibles :
- /actuator/health : État de santé
- /actuator/metrics : Métriques