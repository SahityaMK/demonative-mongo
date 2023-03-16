package com.nextuple.orderpricingapp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PriceListResponse {

	private long priceListKey;

	private String priceListName;

	private String active;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private String organizationCode;

}
