package com.nextuple.orderpricingapp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ItemPriceResponse {
	
	private long itemId;
	
	private OffsetDateTime pricingDate;
	
	private double unitPrice;
	
	private double listPrice;
	
	private String priceListName;
	

}
