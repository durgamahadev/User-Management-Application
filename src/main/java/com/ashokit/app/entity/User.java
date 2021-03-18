package com.ashokit.app.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor

@ApiModel(description = "It's a poja that contain user data")
@Entity
@Table(name = "UserManagement_User")
public class User {
	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_seq_id", initialValue = 12422414, allocationSize = 3)
	@GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", length = 10, nullable = false, unique = true, updatable = false)
	@JsonIgnore
	private Long userId;
	@Column(name = "first_name", length = 30, nullable = false, unique = false, updatable = true)
	private String firstName;
	@Column(name = "last_name", length = 30, nullable = false, unique = false, updatable = true)
	private String lastName;
	@Column(name = "email", length = 40, nullable = false, unique = true, updatable = false)
	private String email;
	@Column(name = "password", length = 30, nullable = false, unique = false, updatable = true)
	@JsonIgnore
	private String password;
	@Column(name = "phone_number", length = 10, nullable = true, unique = false, updatable = true)
	private Long phoneNumber;
	@Column(name = "dob", nullable = false, unique = false, updatable = true)
	private Date dob;
	@Column(name = "gender", length = 10, nullable = false, unique = false, updatable = true)
	private String gender;
	@Column(name = "country", length = 5, nullable = false, unique = false, updatable = true)
	private Integer country;
	@Column(name = "state", length = 5, nullable = false, unique = false, updatable = true)
	private Integer state;
	@Column(name = "city", length = 5, nullable = false, unique = false, updatable = true)
	private Integer city;
	@JsonIgnore
	@Column(name = "user_unlock", length = 8, nullable = false, unique = false, updatable = true)
	private String status;
	@JsonIgnore
	@Column(name = "created_date", length = 8, nullable = true, unique = false, updatable = true)
	private Date createdDate;
	@JsonIgnore
	@Column(name = "updated_date", length = 8, nullable = true, unique = false, updatable = true)
	private Date updatedDate;

	public void setDob(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy	");
		try {
			this.dob = dateFormat.parse(dateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		User user = null;
		if (obj instanceof User) {
			user = (User) obj;
		}

		if (user.getUserId().equals(this.getUserId())) {
			if (user.getFirstName().equals(this.getFirstName())) {
				if (user.getLastName().equals(this.getLastName())) {
					if (user.getEmail().equals(this.getEmail())) {
						if (user.getPassword().equals(this.getPassword())) {
							if (user.getPhoneNumber().equals(this.getPhoneNumber())) {
								if (user.getDob().equals(this.getDob())) {
									if (user.getGender().equals(this.getGender())) {
										if (user.getCountry().equals(this.getCountry())) {
											if (user.getState().equals(this.getState())) {
												if (user.getCity().equals(this.getCity())) {
													if (user.getStatus().equals(this.getStatus())) {
														if (user.getCreatedDate().equals(this.getCreatedDate())) {
															if (user.getUpdatedDate().equals(this.getUpdatedDate())) {
																return true;
															}

														}

													}

												}

											}

										}

									}

								}

							}

						}
					}
				}
			}
		}

		return false;

	}
}
