package com.cacib.partenaireservice.configs;

import com.cacib.partenaireservice.entities.Partenaire;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import com.cacib.partenaireservice.repositories.IPartenaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataInitializerConfig {
    @Bean
    CommandLineRunner initDatabase(IPartenaireRepository partnerRepository) {
        return args -> {
            log.info("Initializing Partner Database");

            if (partnerRepository.count() == 0) {
                // Partner 1 - INBOUND MESSAGE
                Partenaire partner1 = new Partenaire();
                partner1.setAlias("BANK-TRANSFER");
                partner1.setType("FINANCIAL");
                partner1.setDirection(Direction.INBOUND);
                partner1.setApplication("TRANSFER-APP");
                partner1.setProcessedFlowType(ProcessedFlowType.MESSAGE);
                partner1.setDescription("Bank Transfer Processing Partner");
                partnerRepository.save(partner1);

                // Partner 2 - OUTBOUND NOTIFICATION
                Partenaire partner2 = new Partenaire();
                partner2.setAlias("ALERT-SERVICE");
                partner2.setType("NOTIFICATION");
                partner2.setDirection(Direction.OUTBOUND);
                partner2.setApplication("ALERT-APP");
                partner2.setProcessedFlowType(ProcessedFlowType.NOTIFICATION);
                partner2.setDescription("Customer Alert Service Partner");
                partnerRepository.save(partner2);

                // Partner 3 - INBOUND ALERTING
                Partenaire partner3 = new Partenaire();
                partner3.setAlias("FRAUD-DETECT");
                partner3.setType("SECURITY");
                partner3.setDirection(Direction.INBOUND);
                partner3.setApplication("FRAUD-APP");
                partner3.setProcessedFlowType(ProcessedFlowType.ALERTING);
                partner3.setDescription("Fraud Detection System Partner");
                partnerRepository.save(partner3);

                // Partner 4 - OUTBOUND MESSAGE
                Partenaire partner4 = new Partenaire();
                partner4.setAlias("PAYMENT-GATEWAY");
                partner4.setType("PAYMENT");
                partner4.setDirection(Direction.OUTBOUND);
                partner4.setApplication("PAYMENT-APP");
                partner4.setProcessedFlowType(ProcessedFlowType.MESSAGE);
                partner4.setDescription("External Payment Gateway Partner");
                partnerRepository.save(partner4);

                // Partner 5 - INBOUND NOTIFICATION
                Partenaire partner5 = new Partenaire();
                partner5.setAlias("CUSTOMER-SERVICE");
                partner5.setType("SUPPORT");
                partner5.setDirection(Direction.INBOUND);
                partner5.setApplication("SUPPORT-APP");
                partner5.setProcessedFlowType(ProcessedFlowType.NOTIFICATION);
                partner5.setDescription("Customer Service Integration Partner");
                partnerRepository.save(partner5);

                log.info("Finished initializing Partner Database");
            } else {
                log.info("Database already contains data, skipping initialization");
            }
        };
    }
}
