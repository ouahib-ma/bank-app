package com.cacib.partenaireservice.mappers;

import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.entities.Partenaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartenaireMapper {
    PartenaireDTO toDTO(Partenaire partenaire);

    Partenaire toEntity(PartenaireRequest request);

    List<PartenaireDTO> toDTOList(List<Partenaire> partenaires);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updatePartenaireFromRequest(PartenaireRequest request, @MappingTarget Partenaire partenaire);
}
