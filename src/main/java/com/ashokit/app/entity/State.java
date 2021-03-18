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
public class State {
	@Id
	@Column(name = "STATE_ID", insertable = true, nullable = false, unique = true, updatable = false)
	/*@GeneratedValue(generator = "state_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "state_seq",initialValue = 1210212,allocationSize = 3,sequenceName = "state_seq_id")*/
	private Integer stateId;
	@Column(name = "STATE_NAME", insertable = true, nullable = false, unique = true, updatable = false)
	private String stateName;
	@Column(name = "COUNTRY_FK_ID", insertable = true, nullable = false, updatable = false)
	private Integer countryId;
	
	
	
	/*@OneToMany(cascade=CascadeType.ALL,targetEntity = City.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "state_fk_id")
	private Set<City> allCity;
	
	public void setAllCity(City city) {
		if (allCity==null) {
			allCity=new HashSet<>();
		}
		allCity.add(city);
	}*/
}
