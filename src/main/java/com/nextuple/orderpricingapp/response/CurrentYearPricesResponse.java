package com.nextuple.orderpricingapp.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrentYearPricesResponse {
	
	private long itemId;
	
	private String itemDescription;
	
	private List<ItemPriceBySeasonResponse> prices;

}
