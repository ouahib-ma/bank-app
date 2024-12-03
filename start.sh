#!/bin/bash

echo "1- Build et démarrage des services backend..."
docker-compose build
docker-compose up -d

echo "Waiting for backend services to start..."
sleep 30

echo "2- Vérifier les états des services backend..."
docker-compose ps

echo "3- Démarrer le front-end..."
cd routing-app-front
npm install
ng serve --host 0.0.0.0 --port 4200 &

echo "4- Afficher les logs..."
docker-compose logs -f
