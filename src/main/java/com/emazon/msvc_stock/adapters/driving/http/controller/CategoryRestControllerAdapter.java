package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddCategoryRequestDto;
import com.emazon.msvc_stock.adapters.driving.http.dto.response.CategoryResponseDto;
import com.emazon.msvc_stock.adapters.driving.http.dto.response.PaginationResponseDto;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.emazon.msvc_stock.adapters.driving.http.mapper.response.ICategoryResponseMapper;
import com.emazon.msvc_stock.adapters.driving.http.mapper.response.PaginationResponseMapper;
import com.emazon.msvc_stock.domain.api.ICategoryServicePort;
import com.emazon.msvc_stock.domain.model.Category;
import com.emazon.msvc_stock.domain.model.PaginationModel;
import com.emazon.msvc_stock.domain.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","*"})
@RequestMapping(Constants.API_CATEGORY_PATH)
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;



    public CategoryRestControllerAdapter(ICategoryServicePort categoryServicePort, ICategoryRequestMapper categoryRequestMapper, ICategoryResponseMapper categoryResponseMapper) {
        this.categoryServicePort = categoryServicePort;
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
    }
    @PostMapping("")
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequestDto request){
        categoryServicePort.saveCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<PaginationResponseDto<CategoryResponseDto>> getAllCategories(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String order) {

        PaginationModel<Category> categoryPagination = categoryServicePort.listCategories(order, page, size);
        PaginationResponseDto<CategoryResponseDto> response = PaginationResponseMapper.toPaginationResponseDto(categoryPagination, categoryResponseMapper);

        return ResponseEntity.ok(response);
    }
}
