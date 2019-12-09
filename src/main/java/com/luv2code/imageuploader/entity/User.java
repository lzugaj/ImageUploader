package com.luv2code.imageuploader.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String password;

    @Column(name = "uploaded_images_size_with_current_package")
    private Long uploadedImageSizeWithCurrentPackage;

    @Column(name = "uploaded_images_with_current_package")
    private int uploadedImagesWithCurrentPackage;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id_user_profile")
    private UserProfile userProfile;

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST })
    private List<Post> posts;

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST })
    private List<Comment> comments;

    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST })
    private List<DownloadImage> downloadImages;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package userPackage;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}
