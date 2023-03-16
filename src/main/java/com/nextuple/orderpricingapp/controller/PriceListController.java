package com.nextuple.orderpricingapp.controller;

import com.nextuple.orderpricingapp.entities.PriceList;
import com.nextuple.orderpricingapp.exception.DuplicateEntityException;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.PriceListResponse;
import com.nextuple.orderpricingapp.service.PriceListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/")
public class PriceListController {
	
	@Autowired
	PriceListServices priceListServices;
	
//	@Autowired
//	PricelistLineListServices pricelistLineListServices;
//	
//	Return all the pricelist from pricelist table
	@GetMapping("pricelist")   
	public List<PriceListResponse> getAllPriceLists() {
		return priceListServices.getAllPriceLists();
	}
	
//	fetching priceList based on pricelist key
	@GetMapping("pricelist/{pricelistKey}")   
	public PriceListResponse getPriceListByPriceListKey(@PathVariable(name="pricelistKey") Long pricelistKey) throws EntityNotFoundException{
		PriceListResponse foundPriceList = priceListServices.getPriceList(pricelistKey); //--change 
		if(foundPriceList == null) {
			throw new EntityNotFoundException("PriceList with key "+pricelistKey+" doesn't exist in db");
		}else {
			return foundPriceList;
		}
	}
	
//	fetching priceList based on orgcode and pricelist name
	@GetMapping("pricelist/{organizationCode}/{priceListName}")  
	public List<PriceListResponse> getPriceListByOrgCodeAndPriceListName(@PathVariable(name="organizationCode") String orgCode,@PathVariable(name="priceListName") String priceListName) throws EntityNotFoundException {
		List<PriceListResponse> foundPriceList = priceListServices.getPriceListByOrgCodeAndPriceListName(orgCode, priceListName);
		if(foundPriceList.isEmpty()) {
			throw new EntityNotFoundException("PriceList with Name "+priceListName+" and organizationCode "+orgCode+" doesn't exist in db");
		}else {
			return foundPriceList;
		}
	}

	// get active pricelists based on orginizationCode
	@GetMapping("pricelist/getActivePriceLists/{organizationCode}")
	public List<PriceListResponse> getActivePriceLists(@PathVariable(name="organizationCode") String organizationCode) throws EntityNotFoundException {
		List<PriceListResponse> response = priceListServices.getActivePriceListByOrgCode(organizationCode, "Y");
		if(response.isEmpty()) {
			throw new EntityNotFoundException("No Actice Pricelist exist for organizationCode "+organizationCode);
		}else {
			return response;
		}
	}
	
//	creating a pricelist from createpricelist body
	@PostMapping("pricelist")
	public PriceListResponse createPriceList(@Valid @RequestBody PriceList createPriceListRequest) throws DuplicateEntityException {
//		if(priceListServices.priceListExistsByNameAndOrgCode(createPriceListRequest.getPriceListName(), createPriceListRequest.getOrganizationCode())) {
////			throw new DuplicateEntityException("PriceList with Name "+createPriceListRequest.getPriceListName()+" and organizationCode "+createPriceListRequest.getOrganizationCode()+" already exists in db");
//		}else
		if(priceListServices.priceListExists(createPriceListRequest)) {
			throw new DuplicateEntityException("PriceList with key "+createPriceListRequest.getPriceListKey()+" already exists in db");
		}
		PriceListResponse savedPriceList = priceListServices.createPriceList(createPriceListRequest);
		return savedPriceList;
	}
	
//	updating an item	
	@PatchMapping("pricelist/{pricelistKey}")
	public PriceListResponse updatePriceList(@PathVariable(name="pricelistKey") Long pricelistKey,@Valid @RequestBody PriceList updatePriceListRequest) throws EntityNotFoundException, DuplicateEntityException {
		if(!priceListServices.priceListExists(pricelistKey)) {
			throw new EntityNotFoundException("PriceList with key "+pricelistKey+" doesn't exist in db");
		}
//		else if(priceListServices.priceListExistsByNameAndOrgCode(updatePriceListRequest.getPriceListName(), updatePriceListRequest.getOrganizationCode())) {
//			throw new DuplicateEntityException("PriceList with key "+updatePriceListRequest.getPriceListKey()+" and organizationCode "+updatePriceListRequest.getOrganizationCode()+" already exists in db");
//		}
		updatePriceListRequest.setPriceListKey(pricelistKey);
		PriceListResponse savedPriceList = priceListServices.updatePriceList(updatePriceListRequest);
		return savedPriceList;
	}
	

	
	@DeleteMapping("pricelist/{pricelistKey}")
	public ApiResponse deletePriceListByKey(@PathVariable(name="pricelistKey") Long pricelistKey) throws EntityNotFoundException {
		
//		if(pricelistLineListServices.existsByPriceListKey(pricelistKey)){
//			return "PriceList cannot be deleted due to pricelist key exists as foreign key in pricelistlinelist table";
//		}else 
		return priceListServices.deletePriceList(pricelistKey);
		
	}
}



