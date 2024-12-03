package com.cacib.partenaireservice.repositories;

import com.cacib.partenaireservice.entities.Partenaire;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPartenaireRepository extends JpaRepository<Partenaire, Long> {
    Optional<Partenaire> findByAlias(String alias);
    boolean existsByAlias(String alias);
    List<Partenaire> findByDirection(Direction direction);
    List<Partenaire> findByProcessedFlowType(ProcessedFlowType processedFlowType);
}
