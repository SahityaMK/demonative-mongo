package com.nextuple.orderpricingapp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ItemPriceBySeasonResponse {
	
	private OffsetDateTime fromDate;
	
	private OffsetDateTime toDate;
	
	private double unitPrice;
	
	private double listPrice;
	
	private String priceListName;

}
