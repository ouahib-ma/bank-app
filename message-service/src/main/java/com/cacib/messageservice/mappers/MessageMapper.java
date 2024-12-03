package com.cacib.messageservice.mappers;

import com.cacib.messageservice.dtos.MessageDTO;
import com.cacib.messageservice.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", source = "id")
    MessageDTO toDto(Message message);

    List<MessageDTO> toDtoList(List<Message> messages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "receivedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "NEW")
    Message toEntity(MessageDTO dto);
}