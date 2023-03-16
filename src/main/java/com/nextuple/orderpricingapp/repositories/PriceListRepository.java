package com.nextuple.orderpricingapp.repositories;

import com.nextuple.orderpricingapp.entities.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;


@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long>{
	
	List<PriceList> findByOrganizationCodeAndPriceListName(String organizationCode, String priceListname);
	
	List<PriceList> findByOrganizationCode(String organizationCode);
	
	List<PriceList> findByOrganizationCodeAndActive(String organizationCode,String active);
	
	List<PriceList> findByPriceListKeyAndActiveAndStartDateLessThanAndEndDateGreaterThan(Long id,String active,OffsetDateTime pricingDate,OffsetDateTime pricingEndDate);
	
	List<PriceList> findAllByPriceListKeyInAndActiveAndStartDateGreaterThanAndEndDateLessThan(List<Long> priceListKeys,String status,OffsetDateTime startDate,OffsetDateTime endDate);
	
	boolean existsByPriceListNameAndOrganizationCode(String priceListName,String orgCode);
	

}
