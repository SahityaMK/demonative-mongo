package com.nextuple.orderpricingapp.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class GetItemPricesRequest {

	@JsonProperty("itemId")
	@NotNull(message = "itemId is required field.")
	long itemId;
	
	@JsonProperty("organizationCode")
	@NotNull(message = "organizationCode is required field.")
	String organizationCode;
	
	@JsonProperty("pricingDate")
	OffsetDateTime pricingDate;
	
}
