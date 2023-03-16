package com.nextuple.orderpricingapp.entities;

import com.nextuple.orderpricingapp.validator.ValidLongNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor  // default constructor
@Data     		    // Getters & Setters, to string, equal and hash code
@Entity			    // create an entity
@Table(name="item",uniqueConstraints = { @UniqueConstraint(columnNames = { "item_id", "organization_code" }) })
public class Item {
	
	@Id             //annotation for primary key
	@Column(name="item_key")
//	@NotNull(message = "itemKey is required field.")
//	@ValidLongNumber(message = "itemKey is required field.")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long itemKey;
	
	@Column(name="item_id")
	@NotNull(message = "itemId is required field.")
	@ValidLongNumber(message = "itemId is required field.")
	long itemId;
	
	@Column(name="item_description")
	@NotBlank(message = "itemDescription is required field.")
	String itemDescription;
	
	@Column(name="category")
	@NotBlank(message = "category is required field.")
	String category;
	
	@Column(name="type")
	@NotBlank(message = "type is required field.")
	String type;
	
	@Column(name="status")
	@NotBlank(message = "status is required field.")
	String status;
	
	@Column(name="organization_code")
	@NotBlank(message = "organizationCode is required field.")
	String organizationCode;
	
	@Column(name="unit_of_measure")
	@NotBlank(message = "unitOfMeasure is required field.")
	String unitOfMeasure;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<PricelistLineList> pricelistLineLists;	
	

}
