package com.example.training.mapper;

import com.example.training.model.dto.PolicyDto;
import com.example.training.model.entity.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PolicyMapper {

    PolicyMapper INSTANCE = Mappers.getMapper(PolicyMapper.class);

    @Mapping(source = "policyId", target = "policyId")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "coverages", target = "coverages")
    @Mapping(source = "conditions", target = "conditions")
    PolicyDto policyToPolicyDto(Policy policy);

    List<PolicyDto> policiesToPolicyDtos(List<Policy> policies);
}
