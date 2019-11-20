package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

	@Column(name = "description")
	private String varchar;

	@Lob
	@Column(name = "post_image")
	private Byte[] postImage;

	@Column(name = "date_of_post")
	private LocalDateTime dateOfPost;

	@Column(name = "number_of_likes")
	private Integer numberOfLikes;

	@Column(name = "number_of_downloads")
	private Integer numberOfDownloads;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_size_id", referencedColumnName = "id_image_size")
	private ImageSize imageSize;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_format_id", referencedColumnName = "id_image_format")
	private ImageFormat imageFormat;

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

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "post_hash_tag",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "hash_tag_id"))
	private List<HashTag> hashTags;

}
