package com.example.training.mapper;

import com.example.training.model.dto.AccidentDto;
import com.example.training.model.dto.PolicyDto;
import com.example.training.model.entity.Accident;
import com.example.training.model.entity.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccidentMapper {
    AccidentMapper INSTANCE = Mappers.getMapper(AccidentMapper.class);

    @Mapping(source = "accidentId", target = "accidentId")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    AccidentDto accidentToAccidentDto(Accident accident);

    List<AccidentDto> accidentsToAccidentDtos(List<Accident> accidents);
}
