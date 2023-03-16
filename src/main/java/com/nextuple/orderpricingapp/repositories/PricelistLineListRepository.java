package com.nextuple.orderpricingapp.repositories;

import com.nextuple.orderpricingapp.entities.PricelistLineList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricelistLineListRepository extends JpaRepository<PricelistLineList, Long>{
	
  List<PricelistLineList> findByItemItemKey(long itemKey);
  
  boolean existsByItemItemKey(long itemKey);
  
  boolean existsByPriceListPriceListKey(long itemKey);
}
