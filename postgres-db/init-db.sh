#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE banque_message_db;
    CREATE DATABASE banque_partner_db;
EOSQL

# Initialisation de la base de données des messages
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "banque_message_db" <<-EOSQL
    CREATE TABLE IF NOT EXISTS messages (
        id SERIAL PRIMARY KEY,
        content TEXT,
        source VARCHAR(255) NOT NULL,
        queue_name VARCHAR(255) NOT NULL,
        received_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        processed_at TIMESTAMP,
        status VARCHAR(50) NOT NULL,
        version INTEGER
    );

EOSQL

# Initialisation de la base de données des partenaires
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "banque_partner_db" <<-EOSQL
    CREATE TABLE IF NOT EXISTS partners (
        id SERIAL PRIMARY KEY,
        alias VARCHAR(255) NOT NULL UNIQUE,
        type VARCHAR(100) NOT NULL,
        direction VARCHAR(50) NOT NULL,
        application VARCHAR(255),
        processed_flow_type VARCHAR(50) NOT NULL,
        description TEXT NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

EOSQL