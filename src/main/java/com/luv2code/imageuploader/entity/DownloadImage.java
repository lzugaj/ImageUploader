package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "download_image")
public class DownloadImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_download_image")
	private Long id;

	@Column(name = "date_of_download")
	private LocalDateTime dateOfDownload;

	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
}
