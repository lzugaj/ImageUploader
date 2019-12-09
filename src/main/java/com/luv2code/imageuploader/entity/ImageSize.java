package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_size")
public class ImageSize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_image_size")
	private Long id;

	@Column(name = "width")
	private Integer width;

	@Column(name = "height")
	private Integer height;

}
