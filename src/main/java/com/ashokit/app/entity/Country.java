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
public class Country {
	@Id
	@Column(name = "COUNTRY_ID", insertable = true, nullable = false, unique = true, updatable = false)
	/*@GeneratedValue(generator = "country_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "country_seq",initialValue = 1210212,allocationSize = 3,sequenceName = "country_seq_id")*/
	private Integer countryId;
	@Column(name = "COUNTRY_NAME", insertable = true, nullable = false, unique = true, updatable = false)
	private String countryName;

	/*@OneToMany(cascade = CascadeType.ALL, targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_fk_id")
	private Set<State> allState;

	public void setAllState(State state) {
		if (allState == null) {
			allState = new HashSet<>();
		}
		allState.add(state);
	}*/

}
