package com.nextuple.orderpricingapp.service;

import com.nextuple.orderpricingapp.entities.Item;
import com.nextuple.orderpricingapp.exception.DuplicateEntityException;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.repositories.ItemRepository;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.ItemResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServices {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	ModelMapper modelMapper;

	public List<ItemResponse> getAllItems() {
		List<Item> foundResults = itemRepository.findAll();
		
		// Convert List of Items to List of ItemResponse
		List<ItemResponse> returnList = foundResults.stream()
				.map(this::convertToItemResponse)
				.collect(Collectors.toList());
		return returnList;
	}
	
	
	// convert Item to ItemResponse DTO using modelmapper library
	public ItemResponse convertToItemResponse(Item item) {
	    ItemResponse itemResponse = modelMapper.map(item, ItemResponse.class);
	    return itemResponse;
	}
	
//	for GET api returns item based on itemKey, return null if not found
	public ItemResponse getItem(long itemKey) {
		// to be discussed regarding optional
		Optional<Item> foundItems = itemRepository.findById(itemKey);
		if(foundItems.isEmpty()) {
			return null;
		}else {
			return convertToItemResponse(foundItems.get());
		}
	}
	
//	Returns item based on item id and org code, return null if not found
	public ItemResponse getItemByOrgAndItemId(long itemId,String orgCode) {
		// to be discussed regarding optional
		List<Item> foundItems = itemRepository.findByItemIdAndOrganizationCode(itemId,orgCode);
		if(foundItems.isEmpty()) {
			return null;
		}else {
			return convertToItemResponse(foundItems.get(0));
		}
	}
	
//	POST method, create an item with request body and returns the item
	public ItemResponse createItem(Item createItemReq) throws DuplicateEntityException {
		Item savedItem = itemRepository.save(createItemReq);
		System.out.println("ItemServices.createItem"+savedItem.getItemKey());
		return convertToItemResponse(savedItem);
	}
	
//	POST method, check whether the record exists or not based on item key to avoid duplicity
	public boolean itemExists(Long itemKey) {
		return itemRepository.existsById(itemKey);
	}
	
	
//	PUT method, check whether the record exists or not based on item key from updateRequest for item validation
	public boolean itemExists(Item updateItemRequest) {
		return itemRepository.existsByItemIdAndItemKey(updateItemRequest.getItemId(), updateItemRequest.getItemKey());
	}	
	
//	update an existing item based on item key
	public ItemResponse updateItem(Item updateItemRequest) {
		 Item existingItem = itemRepository.findById(updateItemRequest.getItemKey()).get();
	
		 existingItem.setItemId(updateItemRequest.getItemId());
		 
//		 check if any fields is to be updated and if yes, update it using Set method
//		 check for item category
		 if(updateItemRequest.getCategory() != null &&
				 !updateItemRequest.getCategory().isEmpty()) {
			 existingItem.setCategory(updateItemRequest.getCategory());
		 }
//		 check for item description
		 if(updateItemRequest.getItemDescription() != null &&
				 !updateItemRequest.getItemDescription().isEmpty()) {
			 existingItem.setItemDescription(updateItemRequest.getItemDescription());
		 }
//		 Check for item status	 
		 if(updateItemRequest.getStatus() != null &&
				 !updateItemRequest.getStatus().isEmpty()) {
			 existingItem.setStatus(updateItemRequest.getStatus());
		 }
//		check for organisation code 
		 if(updateItemRequest.getOrganizationCode() != null &&
				 !updateItemRequest.getOrganizationCode().isEmpty()) {
			 existingItem.setOrganizationCode(updateItemRequest.getOrganizationCode());
		 }
//		 check for item type
		 if(updateItemRequest.getType() != null &&
				 !updateItemRequest.getType().isEmpty()) {
			 existingItem.setType(updateItemRequest.getType());
		 }
//		 check for unit of measure
		 if(updateItemRequest.getUnitOfMeasure() != null &&
				 !updateItemRequest.getUnitOfMeasure().isEmpty()) {
			 existingItem.setUnitOfMeasure(updateItemRequest.getUnitOfMeasure());
		 }

		System.out.println("ItemServices.updateItem"+existingItem.getItemKey());
	
		 return convertToItemResponse(itemRepository.save(existingItem)); 
	
	}
	
//	Delete an Item based on ItemKey 
	public ApiResponse deleteItem(Long itemKey) throws EntityNotFoundException {
		if(itemRepository.existsById(itemKey)) {
			itemRepository.deleteById(itemKey);
			return new ApiResponse(HttpStatus.OK.value(), "Item got deleted");
		}else {
			throw new EntityNotFoundException("Item with key "+itemKey+" doesn't exist in db");
		}
	}
	
//	Delete an Item based on ItemId and OrganizationCode 
	public boolean deleteItemByItemIdAndOrgCode(Long itemId, String orgCode) {
		if(itemRepository.existsByItemIdAndOrganizationCode(itemId,orgCode)) {
			itemRepository.deleteByItemIdAndOrganizationCode(itemId, orgCode);
			return true;
		}else {
			return false;
		}
	}
	
//	public boolean deleteByIdOrOrgCode(long itemId,String orgCode) {
//	      return itemRepository.deleteByIdOrOrgCode(itemId, orgCode);
//	  }
	
	// check for item with itemid and orgcode
	public boolean getItemByOrgCodeAndItemId(Long itemId, String orgCode) {
		return itemRepository.existsByItemIdAndOrganizationCode(itemId, orgCode);
	}
}
