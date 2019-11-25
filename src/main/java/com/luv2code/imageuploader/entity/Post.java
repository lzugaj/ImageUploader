package com.luv2code.imageuploader.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post")
	private Long id;

	@NotNull(message = "Post description is required")
	@Size(min = 1, message = "Post description is required")
	@Column(name = "description")
	private String description;

	@Lob
	@NotEmpty(message = "Post image is required")
	@Column(name = "post_image")
	private Byte[] postImage;

	@Column(name = "date_of_post")
	private LocalDateTime dateOfPost;

	@Column(name = "number_of_likes")
	private Integer numberOfLikes;

	@Column(name = "number_of_downloads")
	private Integer numberOfDownloads;

	@OneToMany(mappedBy = "post",
			fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.REFRESH, CascadeType.PERSIST })
	private List<Comment> comments;

	@OneToMany(mappedBy = "post",
			fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.REFRESH, CascadeType.PERSIST })
	private List<DownloadImage> downloadImages;

	@NotNull(message = "Image size is required")
	@ManyToOne
	@JoinColumn(name = "image_size_id", nullable = false)
	private ImageSize imageSize;

	@NotNull(message = "Image format is required")
	@ManyToOne
	@JoinColumn(name = "image_format_id", nullable = false)
	private ImageFormat imageFormat;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@NotNull(message = "Hash tags is required")
	@Size(min = 1, message = "Post hash tags is required")
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "post_hash_tag",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "hash_tag_id"))
	private List<HashTag> hashTags;

}
