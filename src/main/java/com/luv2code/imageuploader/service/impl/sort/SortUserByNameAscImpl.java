package com.luv2code.imageuploader.service.impl.sort;

import java.util.Comparator;

import com.luv2code.imageuploader.aop.TrackExecutionTime;
import com.luv2code.imageuploader.entity.User;

/**
 * Created by lzugaj on Wednesday, January 2020
 */

public class SortUserByNameAscImpl implements Comparator<User> {

	@TrackExecutionTime
	@Override
	public int compare(User firstUser, User secondUser) {
		if (firstUser.getLastName().equals(secondUser.getLastName())) {
			return firstUser.getFirstName().compareTo(secondUser.getFirstName());
		}

		return firstUser.getLastName().compareTo(secondUser.getLastName());
	}
}
