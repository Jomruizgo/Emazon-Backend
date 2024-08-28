package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msvc_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/brand")
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;


    public BrandRestControllerAdapter(IBrandServicePort brandServicePort, IBrandRequestMapper brandRequestMapper) {
        this.brandServicePort = brandServicePort;
        this.brandRequestMapper = brandRequestMapper;
    }
    @PostMapping("/")
    public ResponseEntity<Void> addCategory(@RequestBody AddBrandRequest request){
        brandServicePort.saveBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
