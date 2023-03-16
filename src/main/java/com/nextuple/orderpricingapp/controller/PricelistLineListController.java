package com.nextuple.orderpricingapp.controller;

import com.nextuple.orderpricingapp.entities.PricelistLineList;
import com.nextuple.orderpricingapp.exception.DuplicateEntityException;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.PricelistLineListResponse;
import com.nextuple.orderpricingapp.service.PricelistLineListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/")
public class PricelistLineListController {
	
	
	@Autowired
	PricelistLineListServices pricelistLineListServices;

//	creating a pricelistLineList from CreatePricelistLineListRequest body
	@PostMapping("pricelistLineList")
	public PricelistLineListResponse createPricelistLineList(@Valid @RequestBody PricelistLineList createPricelistLineListRequest) throws DuplicateEntityException {
		if(pricelistLineListServices.pricelistLineExists(createPricelistLineListRequest.getPricelistLineKey())) {
			throw new DuplicateEntityException("PricelistLineList with key "+createPricelistLineListRequest.getPricelistLineKey()+" already exist in db");
		}
		PricelistLineListResponse savedItem = pricelistLineListServices.createPricelistLineList(createPricelistLineListRequest);
		return savedItem;
	}

	
//	fetching all pricelistLineList from the repository
	@GetMapping("pricelistLineList")   
	public List<PricelistLineListResponse> getAllPriceListLineList() {
		return pricelistLineListServices.getAllPriceListLineList();
	}
	
//	fetching pricelistLineList based on pricelistLineList key
	@GetMapping("pricelistLineList/{pricelistLineKey}")   
	public PricelistLineListResponse getPriceListLineListByPriceListLineListKey(@PathVariable(name="pricelistLineKey") Long pricelistLineKey) throws EntityNotFoundException {
		PricelistLineListResponse foundPriceList = pricelistLineListServices.getPricelistLineListByKey(pricelistLineKey); //--change 
		if(foundPriceList == null) {
			throw new EntityNotFoundException("PriceListLine with key "+pricelistLineKey+" doesn't exist in db");
		}else {
			return foundPriceList;
		}
	}
	
// Delete pricelistLineList based on pricelistLineList key
	@DeleteMapping("pricelistLineList/{pricelistLineKey}")
	public ApiResponse deletePricrListLineList(@PathVariable(name="pricelistLineKey") Long pricelistLineKey) throws EntityNotFoundException {
		return pricelistLineListServices.deletePricrListLineList(pricelistLineKey);
	}
	
//	updating an item	
	@PatchMapping("pricelistLineList/{pricelistLineKey}")

	public PricelistLineListResponse updatePricelistLineList(@PathVariable(name="pricelistLineKey") Long pricelistLineListKey,@Valid @RequestBody PricelistLineList updatePricelistLineListRequest) throws EntityNotFoundException {
			if(!pricelistLineListServices.pricelistLineExists(pricelistLineListKey)) {
				throw new EntityNotFoundException("PriceListLineList with key "+pricelistLineListKey+" doesn't exist in db");
			}
			updatePricelistLineListRequest.setPricelistLineKey(pricelistLineListKey);
			
			return pricelistLineListServices.updatePriceListLineList(updatePricelistLineListRequest);
		}
	
	

}
