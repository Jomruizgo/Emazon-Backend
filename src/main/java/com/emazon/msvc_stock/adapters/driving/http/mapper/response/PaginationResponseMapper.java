package com.emazon.msvc_stock.adapters.driving.http.mapper.response;

import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.emazon.msvc_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationResponseMapper {

    public static PaginationResponseDto<CategoryResponseDto> toPaginationResponseDto(PaginationModel<Category> paginationModel, ICategoryResponseMapper responseMapper) {
        List<CategoryResponseDto> convertedContent = paginationModel.getContent().stream()
                .map(responseMapper::toResponse)
                .collect(Collectors.toList());

        PaginationResponseDto<CategoryResponseDto> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setContent(convertedContent);
        paginationResponseDto.setPageNumber(paginationModel.getPageNumber());
        paginationResponseDto.setPageSize(paginationModel.getPageSize());
        paginationResponseDto.setTotalElements(paginationModel.getTotalElements());
        paginationResponseDto.setTotalPages(paginationModel.getTotalPages());
        paginationResponseDto.setLast(paginationModel.isLast());
        paginationResponseDto.setFirst(paginationModel.isFirst());

        return paginationResponseDto;
    }
}
