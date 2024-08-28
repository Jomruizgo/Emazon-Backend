package com.emazon.msvc_stock.adapters.driving.http.controller;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msvc_stock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msvc_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msvc_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.msvc_stock.domain.api.IBrandServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/brand")
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;


    public BrandRestControllerAdapter(IBrandServicePort brandServicePort, IBrandRequestMapper brandRequestMapper, IBrandResponseMapper brandResponseMapper) {
        this.brandServicePort = brandServicePort;
        this.brandRequestMapper = brandRequestMapper;
        this.brandResponseMapper = brandResponseMapper;
    }
    @PostMapping("/")
    public ResponseEntity<Void> addCategory(@RequestBody AddBrandRequest request){
        brandServicePort.saveBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<BrandResponse>> getAllBrands(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) String order) {
        return ResponseEntity.ok(brandResponseMapper.toBrandResponseList(
                brandServicePort.listBrands(order, page, size)));
    }

}
