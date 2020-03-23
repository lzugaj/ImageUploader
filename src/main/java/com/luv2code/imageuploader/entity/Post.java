package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

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
public class Post implements Comparable<Post>, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post")
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "hash_tag")
	private String hashTag;

	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "post_image")
	private byte[] postImage;

	@Column(name = "date_of_post")
	private LocalDateTime dateOfPost;

	@Column(name = "number_of_likes")
	private Integer numberOfLikes;

	@Column(name = "number_of_downloads")
	private Integer numberOfDownloads;

	@Column(name = "image_file_size")
	private Long imageFileSize;

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

	@Override
	public int compareTo(Post post) {
		return dateOfPost.compareTo(post.dateOfPost);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Object clone = null;
		try {
			clone = super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
}
