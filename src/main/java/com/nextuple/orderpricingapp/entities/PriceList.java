package com.nextuple.orderpricingapp.entities;

import com.nextuple.orderpricingapp.validator.ValidOffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor  // default constructor
@Data     		    // Getters & Setters, to string, equal and hash code
@Entity			    // create an entity
@Table(name="pricelist",uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndOrgCode",columnNames = { "pricelist_name", "organization_code" }) })
public class PriceList {
	
	@Id             //annotation for primary key
	@Column(name="pricelist_key")
//	@NotNull(message = "priceListKey is required field.")
//	@ValidLongNumber(message = "priceListKey is required field.")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long priceListKey;
	
	@Column(name="pricelist_name")
	@NotBlank(message = "priceListName is required field.")
	String priceListName;
	
	@Column(name="active")
	@NotBlank(message = "active is required field.")
	String active;
	
	@Column(name = "start_date")
//	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	@NotNull(message = "startDate is required field.")
	@ValidOffsetDateTime(message = "startDate cannot be empty")
	OffsetDateTime startDate;
	
	@Column(name = "end_date")
	@NotNull(message = "endDate is required field.")
	OffsetDateTime endDate;
	
	@Column(name="organization_code")
	@NotBlank(message = "organizationCode is required field.")
	String organizationCode;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<PricelistLineList> pricelistLineLists;

}
