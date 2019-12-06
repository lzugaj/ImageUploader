package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

	@NotNull(message = "Post description is required")
	@Size(min = 1, message = "Post description is required")
	@Column(name = "description")
	private String description;

	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@NotEmpty(message = "Post image is required")
	@Column(name = "post_image")
	private byte[] postImage;

	@Column(name = "date_of_post")
	private LocalDateTime dateOfPost;

	@Column(name = "number_of_likes")
	private Integer numberOfLikes;

	@Column(name = "number_of_downloads")
	private Integer numberOfDownloads;

	@ToString.Exclude
	@OneToMany(mappedBy = "post",
			fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.REFRESH, CascadeType.PERSIST })
	private List<Comment> comments;

	@ToString.Exclude
	@OneToMany(mappedBy = "post",
			fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.REFRESH, CascadeType.PERSIST })
	private List<DownloadImage> downloadImages;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
