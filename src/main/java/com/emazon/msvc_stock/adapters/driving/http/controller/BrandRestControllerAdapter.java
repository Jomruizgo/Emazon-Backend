package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequestDto;
import com.emazon.msvc_stock.adapters.driving.http.dto.response.BrandResponseDto;
import com.emazon.msvc_stock.adapters.driving.http.mapper.request.IBrandRequestMapper;
import com.emazon.msvc_stock.adapters.driving.http.mapper.response.IBrandResponseMapper;
import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import com.emazon.msvc_stock.domain.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Constants.API_BRAND_PATH)
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;


    public BrandRestControllerAdapter(IBrandServicePort brandServicePort, IBrandRequestMapper brandRequestMapper, IBrandResponseMapper brandResponseMapper) {
        this.brandServicePort = brandServicePort;
        this.brandRequestMapper = brandRequestMapper;
        this.brandResponseMapper = brandResponseMapper;
    }
    @PostMapping("")
    public ResponseEntity<Void> addCategory(@RequestBody AddBrandRequestDto request){
        brandServicePort.saveBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<BrandResponseDto>> getAllBrands(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               @RequestParam(required = false) String order) {
        return ResponseEntity.ok(brandResponseMapper.toBrandResponseList(
                brandServicePort.listBrands(order, page, size)));
    }

}
