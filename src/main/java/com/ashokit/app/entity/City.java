package com.ashokit.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class City {

	@Id
	@Column(name = "CITY_ID", insertable = true, nullable = false, unique = true, updatable = false)
	/*@GeneratedValue(generator = "city_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "city_seq",initialValue = 1210212,allocationSize = 3,sequenceName = "city_seq_id")*/
	private Integer cityId;
	@Column(name = "CITY_NAME", insertable = true, nullable = false, unique = true, updatable = false)
	private String cityName;
	@Column(name = "STATE_FK_ID", insertable = true, nullable = false, updatable = false)
	private Integer stateId;
	
	
	
}
