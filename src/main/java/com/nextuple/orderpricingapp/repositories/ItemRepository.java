package com.nextuple.orderpricingapp.repositories;

import com.nextuple.orderpricingapp.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// JPA repository contains crud repository and paging and sorting reposirtory

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	List<Item> findByItemIdAndOrganizationCode(long itemId, String organizationCode);
	
	boolean existsByItemIdAndItemKey(long itemId, long itemKey);
	
	boolean existsByItemIdAndOrganizationCode(long itemId, String organizationCode);
	
	boolean deleteByItemIdAndOrganizationCode(long itemId, String orgCode);
	
}
