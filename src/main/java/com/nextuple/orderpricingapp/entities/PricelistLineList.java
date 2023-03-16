package com.nextuple.orderpricingapp.entities;


import com.nextuple.orderpricingapp.validator.ValidLongNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor  // default constructor
@AllArgsConstructor
@Data     		    // Getters & Setters, to string, equal and hash code
@Entity			    // create an entity
@Table(name="pricelist_linelist")
public class PricelistLineList {

	
	@Id             //annotation for primary key
	@Column(name="pricelistline_key")
//	@NotNull(message = "pricelistLineKey is required field.")
//	@ValidLongNumber(message = "pricelistLineKey is required field.")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long pricelistLineKey;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_key",insertable = false, updatable = false)
    private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricelist_key",insertable = false, updatable = false)
    private PriceList priceList;
	
	@Column(name="pricelist_key")
	@NotNull(message = "priceListKey is required field.")
	@ValidLongNumber(message = "priceListKey is required field.")
	long priceListKey;
	
	@Column(name="item_key")
	@NotNull(message = "itemKey is required field.")
	@ValidLongNumber(message = "itemKey is required field.")
	long itemKey;
		
	@Column(name="unit_price")
	@NotNull(message = "unitPrice is required field.")
	double unitPrice;
	
	@Column(name="list_price")
	@NotNull(message = "listPrice is required field.")
	double listPrice;
	
	@Column(name="organization_code")
	@NotBlank(message = "organizationCode is required field.")
	String organizationCode;


}
