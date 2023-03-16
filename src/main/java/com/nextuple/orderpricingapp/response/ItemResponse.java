package com.nextuple.orderpricingapp.response;

import com.nextuple.orderpricingapp.entities.PricelistLineList;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
public class ItemResponse {
	
	private long itemKey;
	
	private long itemId;
	
	private String itemDescription;
	
	private String category;
	
	private String type;
	
	private String status;
	
	private String organizationCode;
	
	private String unitOfMeasure;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<PricelistLineList> pricelistLineLists;
	
}
