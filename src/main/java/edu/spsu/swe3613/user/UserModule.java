package edu.spsu.swe3613.user;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(DefaultUserService.class);
		bind(UserDao.class).to(SqLiteUserDao.class);
	}
}
