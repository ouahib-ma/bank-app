-- Supprimer et recréer la table
DROP TABLE IF EXISTS partenaires;

CREATE TABLE partenaires (
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

-- Insérer les données
INSERT INTO partenaires
(alias, type, direction, application, processed_flow_type, description)
VALUES
    ('BANK-TRANSFER', 'FINANCIAL', 'INBOUND', 'TRANSFER-APP', 'MESSAGE', 'Bank Transfer Processing Partner'),
    ('ALERT-SERVICE', 'NOTIFICATION', 'OUTBOUND', 'ALERT-APP', 'NOTIFICATION', 'Customer Alert Service Partner'),
    ('FRAUD-DETECT', 'SECURITY', 'INBOUND', 'FRAUD-APP', 'ALERTING', 'Fraud Detection System Partner'),
    ('PAYMENT-GATEWAY', 'PAYMENT', 'OUTBOUND', 'PAYMENT-APP', 'MESSAGE', 'External Payment Gateway Partner'),
    ('CUSTOMER-SERVICE', 'SUPPORT', 'INBOUND', 'SUPPORT-APP', 'NOTIFICATION', 'Customer Service Integration Partner')
    ON CONFLICT (alias) DO NOTHING;