package com.luv2code.imageuploader.utils;

/**
 * Created by lzugaj on Sunday, January 2020
 */

public abstract class MessageError {

	public static final String PLEASE_FILL_ALL_REQUIRED_FIELDS = "Please fill all required fields.";

	public static final String PACKAGE_TRACKER_VALIDATION = "You have reached a limit for number of uploaded images or total images size or you tried to upload image with extension that is not supported for your Package!";

	private MessageError() {
		// Default constructor
	}
}
