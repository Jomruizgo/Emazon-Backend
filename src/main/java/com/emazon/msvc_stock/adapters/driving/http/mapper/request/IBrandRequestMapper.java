package com.emazon.msvc_stock.adapters.driving.http.mapper.request;

import com.emazon.msvc_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msvc_stock.domain.model.Brand;

public interface IBrandRequestMapper {
    Brand addRequestToBrand(AddBrandRequest addBrandRequest);
}
