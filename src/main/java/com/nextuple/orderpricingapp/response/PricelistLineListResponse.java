package com.nextuple.orderpricingapp.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricelistLineListResponse {
	
	
	private long pricelistLineKey;
	
	
    private long itemKey;
	
    private long priceListKey;
		
	private double unitPrice;
	
	private double listPrice;
	
	private String organizationCode;
	

}
