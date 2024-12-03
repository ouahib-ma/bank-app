package com.cacib.partenaireservice.services;


import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.enums.Direction;

import java.util.List;

public interface IPartenaireService {
    PartenaireDTO createPartenaire(PartenaireRequest request);
    PartenaireDTO getPartenaireById(Long id);
    PartenaireDTO getPartenaireByAlias(String alias);
    List<PartenaireDTO> getAllPartenaires();
    List<PartenaireDTO> getPartenairesByDirection(Direction direction);
    PartenaireDTO updatePartenaire(Long id, PartenaireRequest request);
    void deletePartenaire(Long id);

}
