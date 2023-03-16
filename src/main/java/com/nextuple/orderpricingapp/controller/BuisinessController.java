package com.nextuple.orderpricingapp.controller;

import com.nextuple.orderpricingapp.entities.PriceList;
import com.nextuple.orderpricingapp.entities.PricelistLineList;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.request.GetItemPricesRequest;
import com.nextuple.orderpricingapp.response.*;
import com.nextuple.orderpricingapp.service.ItemServices;
import com.nextuple.orderpricingapp.service.PriceListServices;
import com.nextuple.orderpricingapp.service.PricelistLineListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/")
public class BuisinessController {
	
	@Autowired
	PricelistLineListServices pricelistLineListServices;
	
	@Autowired
	ItemServices itemServices;
	
	@Autowired
	PriceListServices priceListServices;
	
	@GetMapping("business/itemPrice")   
	public ItemPriceResponse getItemPrices(@Valid @RequestBody GetItemPricesRequest getItemPricesRequest) throws EntityNotFoundException {
		
		if(getItemPricesRequest.getPricingDate() == null) {
			getItemPricesRequest.setPricingDate(OffsetDateTime.now());
		}
		ItemResponse item = itemServices.getItemByOrgAndItemId(getItemPricesRequest.getItemId(),getItemPricesRequest.getOrganizationCode());
		if(item == null) {
			throw new EntityNotFoundException("Item with id "+getItemPricesRequest.getItemId()+" doesn't exist in db");
		}
		long itemKey = item.getItemKey();
		PricelistLineList pricelistLineList = pricelistLineListServices.getPriceListByItemId(itemKey);
		if(pricelistLineList == null) {
			throw new EntityNotFoundException("PriceListLineList with item id "+getItemPricesRequest.getItemId()+" doesn't exist in db");
		}
		double unitPrice = pricelistLineList.getUnitPrice();
		double listPrice = pricelistLineList.getListPrice();
		long priceListKey = pricelistLineList.getPriceList().getPriceListKey();
		System.out.println("BuisinessController.getItemPrices"+priceListKey);
		System.out.println("BuisinessController.getItemPrices"+getItemPricesRequest.getPricingDate());
		PriceListResponse priceList = priceListServices.getPriceListByActiveStatus(priceListKey,getItemPricesRequest.getPricingDate()); // filter active and priceDate between start and end date
		
		if(priceList == null) {
			throw new EntityNotFoundException("PriceList with item id "+getItemPricesRequest.getItemId()+" doesn't exist in db");		}
		
		ItemPriceResponse response = new ItemPriceResponse();
		response.setItemId(item.getItemId());
		response.setListPrice(listPrice);
		response.setUnitPrice(unitPrice);
		response.setPriceListName(priceList.getPriceListName());
		response.setPricingDate(getItemPricesRequest.getPricingDate());
		
		return response;
	}
	
	@GetMapping("business/currentYearPrices/{organizationCode}/{itemId}")
	public CurrentYearPricesResponse getCurrenrYearPrices(@PathVariable(name="organizationCode") String organizationCode,@PathVariable(name="itemId") Long itemId) throws EntityNotFoundException {
		ItemResponse item = itemServices.getItemByOrgAndItemId(itemId,organizationCode);
		if(item == null) {
			throw new EntityNotFoundException("Item with id "+itemId+" and organizationCode "+organizationCode+" doesn't exist in db");
		}
		
		CurrentYearPricesResponse response = new CurrentYearPricesResponse();
		
		response.setItemId(item.getItemId());
		response.setItemDescription(item.getItemDescription());
		
		long itemKey = item.getItemKey();
		List<PricelistLineList> pricelistLineList = pricelistLineListServices.getListOfPriceLineListByItemId(itemKey);
		if(pricelistLineList == null || pricelistLineList.isEmpty()) {
			throw new EntityNotFoundException("PricelistLineList with item id "+itemId+" and organizationCode "+organizationCode+" doesn't exist in db");
		}
		
		List<Long> priceListKeysList = new ArrayList<Long>();
		for (PricelistLineList priceListLineItem: pricelistLineList) {
			priceListKeysList.add(priceListLineItem.getPriceList().getPriceListKey());
		}

		List<PriceList> currentYearPriceLists = priceListServices.getPriceListForCurrentYear(priceListKeysList);

		HashMap<Long,PriceList> currentYearPriceListsMap = new HashMap<>();

		for (PriceList listItem:currentYearPriceLists) {
			currentYearPriceListsMap.put(listItem.getPriceListKey(),listItem);
		}

		List<Long> currentYearpriceListKeysList = new ArrayList<Long>();
		for (PriceList priceListLineItem: currentYearPriceLists) {
			currentYearpriceListKeysList.add(priceListLineItem.getPriceListKey());
		}
		
		List<ItemPriceBySeasonResponse> prices = new ArrayList<ItemPriceBySeasonResponse>();
		
		for (PricelistLineList pricelistLineListItem : pricelistLineList) {
			if(currentYearPriceListsMap.containsKey(pricelistLineListItem.getPriceList().getPriceListKey())){
				ItemPriceBySeasonResponse priceResponse = new ItemPriceBySeasonResponse();
				priceResponse.setListPrice(pricelistLineListItem.getListPrice());
				priceResponse.setUnitPrice(pricelistLineListItem.getUnitPrice());

				PriceList priceList = currentYearPriceListsMap.get(pricelistLineListItem.getPriceList().getPriceListKey());
				priceResponse.setPriceListName(priceList.getPriceListName());
				priceResponse.setFromDate(priceList.getStartDate());
				priceResponse.setToDate(priceList.getEndDate());
				prices.add(priceResponse);
			}
		}
		
		response.setPrices(prices);
		
		return response;
	}

}
