package com.luv2code.imageuploader.utils;

/**
 * Created by lzugaj on Saturday, January 2020
 */

public abstract class Utils {

	public static final String FREE = "FREE";

	public static final String PRO = "PRO";

	public static final String GOLD = "GOLD";

	public static final Long ID_FREE = 1L;

	public static final Long ID_PRO = 2L;

	public static final Long ID_GOLD = 3L;

	public static final String[] FREE_EXTENSION_LIST = {"jpg", "png"};

	public static final String[] PRO_EXTENSION_LIST = {"jpg", "png", "gif", "jpeg"};

	public static final String[] GOLD_EXTENSION_LIST = {"jpg", "png", "gif", "jpeg", "exif", "tiff", "bmp", "bpg"};

	public static final String ADMIN = "admin";

	private Utils() {
		// Default constructor
	}
}
