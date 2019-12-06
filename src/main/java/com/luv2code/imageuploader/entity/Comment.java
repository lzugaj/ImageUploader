package com.luv2code.imageuploader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comment")
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "number_of_likes")
	private Integer numberOfLikes;

	@Column(name = "number_of_dislikes")
	private Integer numberOfDislikes;

	@Column(name = "date_of_post")
	private LocalDateTime dateOfPost;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
}
