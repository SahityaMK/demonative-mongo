package com.nextuple.orderpricingapp.service;

import com.nextuple.orderpricingapp.entities.PricelistLineList;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.repositories.PricelistLineListRepository;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.PricelistLineListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PricelistLineListServices {
	
	
	
	@Autowired
	PricelistLineListRepository pricelistLineListRepository;
	
//	@Autowired
//	ItemServices itemServices;
//	
//	@Autowired
//	PriceListServices priceListServices;
	
	@Autowired
	ModelMapper modelMapper;
	
	// convert pricelistlinelist to PriceListLineListResponse DTO using modelmapper library
	private PricelistLineListResponse convertToPriceListLineListResponse(PricelistLineList pricelistLineList) {
		PricelistLineListResponse pricelistLineListResponse = modelMapper.map(pricelistLineList, PricelistLineListResponse.class);
	    return pricelistLineListResponse;
	}
	
	
//	POST method, create an PricelistLineList with request body and returns the PricelistLineListResponse
	public PricelistLineListResponse createPricelistLineList(PricelistLineList pricelistLineList) {
		
		PricelistLineList savedItem = pricelistLineListRepository.save(pricelistLineList);
		return convertToPriceListLineListResponse(savedItem);
		
	}


	// find whether primiary key already exists
	public boolean pricelistLineExists(@NotNull(message = "pricelistlineKey is required") long pricelistlineKey) {
		// TODO Auto-generated method stub
		return pricelistLineListRepository.existsById(pricelistlineKey);
	}

	// Fetch all the pricelistlinelist items from the table
	public List<PricelistLineListResponse> getAllPriceListLineList() {
		// TODO Auto-generated method stub
		List<PricelistLineList> allList =  pricelistLineListRepository.findAll();
		// Convert List of PriceListLineList to List of PriceListLineListResponse
		List<PricelistLineListResponse> returnList = allList.stream()
				.map(this::convertToPriceListLineListResponse)
				.collect(Collectors.toList());
		return returnList;
	}


	// Fetch the PricelistLineList based on primary key
	public PricelistLineListResponse getPricelistLineListByKey(Long pricelistLineKey) {
		// TODO Auto-generated method stub
		Optional<PricelistLineList> foundPricelistLine = pricelistLineListRepository.findById(pricelistLineKey);
		if(foundPricelistLine.isEmpty()) {
			return null;
		}else {
			return convertToPriceListLineListResponse(foundPricelistLine.get());
		}
	}


	// delete PricelistLineList based on primary key
	public ApiResponse deletePricrListLineList(Long pricelistLineKey) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		if(pricelistLineListRepository.existsById(pricelistLineKey)) {
			pricelistLineListRepository.deleteById(pricelistLineKey);
			return new ApiResponse(HttpStatus.OK.value(),"PricelistLineList got deleted");
		}else {
			throw new EntityNotFoundException("PriceListLineList with key "+pricelistLineKey+" doesn't exist in db");
		}
	}


	// Update the pricelistLineList object
	public PricelistLineListResponse updatePriceListLineList(@Valid PricelistLineList updatePricelistLineListRequest) {
		// TODO Auto-generated method stub
		
		PricelistLineList existingItem = pricelistLineListRepository.findById(updatePricelistLineListRequest.getPricelistLineKey()).get();
		
		 
//		 check if any fields is to be updated and if yes, update it using Set method
//		 check for item organizationcode
		 if(updatePricelistLineListRequest.getOrganizationCode() != null &&
				 !updatePricelistLineListRequest.getOrganizationCode().isEmpty()) {
			 existingItem.setOrganizationCode(updatePricelistLineListRequest.getOrganizationCode());
		 }
		 
		 existingItem.setListPrice(updatePricelistLineListRequest.getListPrice());
		 existingItem.setUnitPrice(updatePricelistLineListRequest.getUnitPrice());
//		 if(!itemServices.itemExists(updatePricelistLineListRequest.getItemKey())) {
//			 return "Item Key "+updatePricelistLineListRequest.getItemKey()+" doesnt exist in db";
//		 }
//
//		 if(!priceListServices.priceListExists(updatePricelistLineListRequest.getPriceListKey())) {
//			 return "PriceList Key "+updatePricelistLineListRequest.getPriceListKey()+" doesnt exist in db";
//		 }

		 existingItem.setItemKey(updatePricelistLineListRequest.getItemKey());;
		 existingItem.setPriceListKey(updatePricelistLineListRequest.getPriceListKey());
		 
		return  convertToPriceListLineListResponse(pricelistLineListRepository.save(existingItem));
	}
	
	
	// Fetch PricelistLineList based on itemKey
	public PricelistLineList getPriceListByItemId(long itemKey) {
		List<PricelistLineList> foundList = pricelistLineListRepository.findByItemItemKey(itemKey);
		if(foundList.isEmpty()) {
			return null;
		}else {
			return foundList.get(0); // to be checked
		}
	}
	
	// Fetch PricelistLineList based on itemKey
	public List<PricelistLineList> getListOfPriceLineListByItemId(long itemKey) {
		List<PricelistLineList> foundList = pricelistLineListRepository.findByItemItemKey(itemKey);
		return foundList;
	}
	
	// itemkey exists check
	public boolean existsByItemKey(long itemKey) {
		return pricelistLineListRepository.existsByItemItemKey(itemKey);
	}
	
	// pricelistkey exists check
	public boolean existsByPriceListKey(long priceListKey) {
		return pricelistLineListRepository.existsByPriceListPriceListKey(priceListKey);
	}

}
