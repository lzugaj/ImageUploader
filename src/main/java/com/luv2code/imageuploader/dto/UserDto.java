package com.luv2code.imageuploader.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.imageuploader.validation.FieldMatch;
import com.luv2code.imageuploader.validation.ValidEmail;

/**
 * Created by lzugaj on Monday, November 2019
 */

@FieldMatch.List({
        @FieldMatch(
                first = "password",
                second = "matchingPassword",
                message = "Password fields must match"
        )})
public class UserDto {

    @NotNull(message = "Username field is required")
    @Size(min = 1, message = "Username field is required")
    private String userName;

    @NotNull(message = "Password field is required")
    @Size(min = 1, message = "Password field is required")
    private String password;

    @NotNull(message = "Password field is required")
    @Size(min = 1, message = "Password field is required")
    private String matchingPassword;

    @NotNull(message = "First name field is required")
    @Size(min = 1, message = "First name field is required")
    private String firstName;

    @NotNull(message = "Last name field is required")
    @Size(min = 1, message = "Last name field is required")
    private String lastName;

    @ValidEmail
    @NotNull(message = "Email field is required")
    @Size(min = 1, message = "Email field is required")
    private String email;

    public UserDto() {
        // Default constructor
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
