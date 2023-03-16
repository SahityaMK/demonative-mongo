package com.nextuple.orderpricingapp.controller;

import com.nextuple.orderpricingapp.entities.Item;
import com.nextuple.orderpricingapp.exception.DuplicateEntityException;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.ItemResponse;
import com.nextuple.orderpricingapp.service.ItemServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/")
public class ItemController {
	
	@Autowired
	ItemServices itemServices;
	
//	@Autowired
//	PricelistLineListServices pricelistLineListServices;
	
	Logger logger = LoggerFactory.getLogger(ItemController.class);
	
//	fetching all items from the repository
	@GetMapping("items")   
	public List<ItemResponse> getAllItems() {
		logger.info("All items: " + itemServices.getAllItems());
		return itemServices.getAllItems();
	}
	
	
//	fetching Item based on ItemKey
	@GetMapping("items/{itemKey}")   
	public ItemResponse getItemByItemKey(@PathVariable(name="itemKey") Long itemKey) throws EntityNotFoundException {
		
		logger.info("item key: "+ itemKey);
		
		ItemResponse foundItem = itemServices.getItem(itemKey.longValue());
		if(foundItem == null) {
			throw new EntityNotFoundException("Item with key "+itemKey+" doesn't exist in db");
		}else {
			return foundItem;
		}
	}
	
//	fetching Item based on itemid and Organizationcode
	@GetMapping("items/{organizationCode}/{itemid}")   
	public ItemResponse getItemByOrgCodeItemId(@PathVariable(name="itemid") Long itemId,@PathVariable(name="organizationCode") String orgCode) throws EntityNotFoundException {
		
		ItemResponse foundItem = itemServices.getItemByOrgAndItemId(itemId,orgCode);
		if(foundItem == null) {
			throw new EntityNotFoundException("Item with id "+itemId+" and organizationCode "+orgCode+" doesn't exist in db");
		}else {
			return foundItem;
		}
	}
	
//	creating a item from itemRequest body
	@PostMapping("items")
	public ItemResponse createItem(@RequestBody @Valid Item createItemReq) throws DuplicateEntityException {
		ItemResponse savedItem = itemServices.createItem(createItemReq);
		return savedItem;
	}
	
	
	@DeleteMapping("items/{itemKey}")
	public ApiResponse deleteItem(@PathVariable(name="itemKey") Long itemKey) throws EntityNotFoundException {
		return itemServices.deleteItem(itemKey);
	}
	
	@DeleteMapping("items/{organizationCode}/{itemid}")
	public ApiResponse deleteItem(@PathVariable(name="organizationCode") String orgCode,@PathVariable(name="itemid") Long itemId) throws EntityNotFoundException {
		ItemResponse existingItem = itemServices.getItemByOrgAndItemId(itemId, orgCode);
		if(existingItem == null) {
			throw new EntityNotFoundException("Item with id "+itemId+" and organizationCode "+orgCode+" doesn't exist in db");
		}
//		else if(pricelistLineListServices.existsByItemKey(existingItem.getItemKey())) {
//			return "Item cannot be deleted due to item key exists as foreign key in pricelistlinelist table";
//		}
		else {
			return itemServices.deleteItem(existingItem.getItemKey());
		}
	}
	

	
//	updating an item	
	@PatchMapping("items/{itemKey}")
	public ItemResponse updateItem(@PathVariable(name="itemKey") Long itemKey,@Valid @RequestBody Item updateItemRequest) throws EntityNotFoundException, DuplicateEntityException {
			if(!itemServices.itemExists(itemKey)) {
				throw new EntityNotFoundException("Item with key "+itemKey+" doesn't exist in db");
			}
			updateItemRequest.setItemKey(itemKey);

			ItemResponse savedItem = itemServices.updateItem(updateItemRequest);
			return savedItem;
		}
}
