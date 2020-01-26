package com.luv2code.imageuploader.service.impl.sort;

import com.luv2code.imageuploader.entity.User;

import java.util.Comparator;

/**
 * Created by lzugaj on Wednesday, January 2020
 */

public class SortUserByNameDescImpl implements Comparator<User> {

	@Override
	public int compare(User firstUser, User secondUser) {
		if (firstUser.getLastName().equals(secondUser.getLastName())) {
			return firstUser.getFirstName().compareTo(secondUser.getFirstName()) * (-1);
		}

		return firstUser.getLastName().compareTo(secondUser.getLastName()) * (-1);
	}
}
