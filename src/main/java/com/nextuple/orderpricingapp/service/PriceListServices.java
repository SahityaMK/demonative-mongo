package com.nextuple.orderpricingapp.service;

import com.nextuple.orderpricingapp.entities.PriceList;
import com.nextuple.orderpricingapp.exception.EntityNotFoundException;
import com.nextuple.orderpricingapp.repositories.PriceListRepository;
import com.nextuple.orderpricingapp.response.ApiResponse;
import com.nextuple.orderpricingapp.response.PriceListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceListServices {
	
	@Autowired
	PriceListRepository priceListRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	// convert pricelist to PriceListResponse DTO using modelmapper library
	private PriceListResponse convertToPriceListResponse(PriceList priceList) {
		PriceListResponse priceListResponse = modelMapper.map(priceList, PriceListResponse.class);
	    return priceListResponse;
	}
	
//	Return all the pricelist from pricelist table
	public List<PriceListResponse> getAllPriceLists() {
		List<PriceList> foundResults = priceListRepository.findAll();
		
		// Convert List of pricelist to List of priceListResponse
		List<PriceListResponse> priceListResponse = foundResults.stream()
				.map(this::convertToPriceListResponse)
				.collect(Collectors.toList());
		return priceListResponse;
	}
		
//	Returns Price list based on pricelist key, return null if not found
		public PriceListResponse getPriceList(long priceListKey) {
			// to be discussed regarding optional
			Optional<PriceList> foundPriceLists = priceListRepository.findById(priceListKey);
			if(foundPriceLists.isEmpty()) {
				return null;
			}else {
				return convertToPriceListResponse(foundPriceLists.get());
			}
		}
		
//		Returns pricelist based on org code and pricelist name, return null if not found	
		public List<PriceListResponse> getPriceListByOrgCodeAndPriceListName(String orgCode, String priceListname) {
			List<PriceList> foundPriceLists = priceListRepository.findByOrganizationCodeAndPriceListName(orgCode, priceListname);
			// Convert List of pricelist to List of priceListResponse
			List<PriceListResponse> priceListResponse = foundPriceLists.stream()
					.map(this::convertToPriceListResponse)
					.collect(Collectors.toList());
			return priceListResponse;
		}
		
//		POST method, create an pricelist with request body and returns the pricelist
		public PriceListResponse createPriceList(PriceList createPriceListRequest) {
			PriceList savedPriceList = priceListRepository.save(createPriceListRequest);
			return convertToPriceListResponse(savedPriceList);
		}

		public boolean priceListExists(@Valid PriceList createPriceListRequest) {
			// TODO Auto-generated method stub
			return priceListRepository.existsById(createPriceListRequest.getPriceListKey());

		}
		
		public boolean priceListExistsByNameAndOrgCode(String priceListName,String orgCode) {
			// TODO Auto-generated method stub
			return priceListRepository.existsByPriceListNameAndOrganizationCode(priceListName,orgCode);

		}
		
//		update an existing item based on item key
		public PriceListResponse updatePriceList(PriceList updatePriceListRequest) {
			 PriceList existingPriceList = priceListRepository.findById(updatePriceListRequest.getPriceListKey()).get();
		
			 
//			 check if any fields is to be updated and if yes, update it using Set method
//			 check for item description
			 if(updatePriceListRequest.getPriceListName() != null &&
					 !updatePriceListRequest.getPriceListName().isEmpty()) {
				 existingPriceList.setPriceListName(updatePriceListRequest.getPriceListName());
			 }
//			 Check for item status	 
			 if(updatePriceListRequest.getActive() != null &&
					 !updatePriceListRequest.getActive().isEmpty()) {
				 existingPriceList.setActive(updatePriceListRequest.getActive());
			 }
//			check for organisation code 
			 if(updatePriceListRequest.getStartDate() != null) {
				 existingPriceList.setStartDate(updatePriceListRequest.getStartDate());
			 }
//			 check for item type
			 if(updatePriceListRequest.getEndDate() != null) {
				 existingPriceList.setEndDate(updatePriceListRequest.getEndDate());
			 }
//			 check for unit of measure
			 if(updatePriceListRequest.getOrganizationCode() != null &&
					 !updatePriceListRequest.getOrganizationCode().isEmpty()) {
				 existingPriceList.setOrganizationCode(updatePriceListRequest.getOrganizationCode());
			 }
		
			 return convertToPriceListResponse(priceListRepository.save(existingPriceList));
		
		}

		public boolean priceListExists(@Valid Long pricelistKey) {
			// TODO Auto-generated method stub
			return priceListRepository.existsById(pricelistKey);
		}

		
		public List<PriceListResponse> getActivePriceListByOrgCode(@Valid String orgCode,@Valid String status) {
			List<PriceList> foundItemsList = priceListRepository.findByOrganizationCodeAndActive(orgCode, status);
			List<PriceListResponse> priceListResponse = foundItemsList.stream()
					.map(this::convertToPriceListResponse)
					.collect(Collectors.toList());
			return priceListResponse;
		}
		
//		used for buisiness API
			public PriceListResponse getPriceListByActiveStatus(long priceListKey, OffsetDateTime pricingDate) {
				// to be discussed regarding optional
				List<PriceList> foundPriceLists = priceListRepository.findByPriceListKeyAndActiveAndStartDateLessThanAndEndDateGreaterThan(priceListKey,"Y",pricingDate,pricingDate);
				if(foundPriceLists.isEmpty()) {
					return null;
				}else {
					return convertToPriceListResponse(foundPriceLists.get(0));
				}
			}
			
			public List<PriceList> getPriceListForCurrentYear(List<Long> priceListKeys){
				int year = OffsetDateTime.now().getYear();
				OffsetDateTime startOfYear = ZonedDateTime.parse(year+"-01-01T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())).toOffsetDateTime();
				OffsetDateTime endOfYear = ZonedDateTime.parse(year+"-12-31T23:59:59.999999999", DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())).toOffsetDateTime();
				System.out.println("priceListKeys "+priceListKeys.get(0));
				List<PriceList> foundPriceLists = priceListRepository.findAllByPriceListKeyInAndActiveAndStartDateGreaterThanAndEndDateLessThan(priceListKeys, "Y", startOfYear, endOfYear);
				System.out.println("foundPriceLists :"+foundPriceLists.size());
				return foundPriceLists;
			}

			public ApiResponse deletePriceList(Long pricelistKey) throws EntityNotFoundException {
				// TODO Auto-generated method stub
				if(priceListRepository.existsById(pricelistKey)) {
					priceListRepository.deleteById(pricelistKey);
					return new ApiResponse(HttpStatus.OK.value(),"PriceList got delete");
				}else {
					throw new EntityNotFoundException("PriceList with key "+pricelistKey+" doesn't exist in db");
				}
			}


}
