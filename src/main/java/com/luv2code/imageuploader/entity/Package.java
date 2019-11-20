package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package")
public class Package {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_package")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "upload_size")
	private Integer uploadSize;

	@Column(name = "daily_upload_limit")
	private Integer dailyUploadLimit;

	@OneToOne(mappedBy = "userPackage")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "package_image_format",
			joinColumns = @JoinColumn(name = "package_id"),
			inverseJoinColumns = @JoinColumn(name = "image_format_id"))
	private List<ImageFormat> imageFormats;

}
