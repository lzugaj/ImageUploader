package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_profile")
	private Long id;

	@Column(name = "user_description")
	private String userDescription;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "position_name")
	private String positionName;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "start_year")
	private Integer startYear;

	@Column(name = "end_year")
	private Integer endYear;

	@Column(name = "is_working_here")
	private Boolean isWorkingHere;

	@Column(name = "school_name")
	private String schoolName;

	@Column(name = "concentration_name")
	private String concentrationName;

	@Column(name = "secondary_concentration_name")
	private String secondaryConcentrationName;

	@Column(name = "degree_type_name")
	private String degreeTypeName;

	@Column(name = "graduation_year")
	private Integer graduationYear;

	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "profile_image")
	private byte[] profileImage;

	@OneToOne(mappedBy = "userProfile")
	private User user;

}
