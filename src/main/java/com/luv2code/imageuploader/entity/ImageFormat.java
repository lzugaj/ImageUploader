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
@Table(name = "image_format")
public class ImageFormat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_image_format")
	private Long id;

	@Column(name = "extension_name")
	private String extensionName;

	@OneToMany(mappedBy = "imageFormat",
			fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.REFRESH, CascadeType.PERSIST })
	private List<Post> post;

}
