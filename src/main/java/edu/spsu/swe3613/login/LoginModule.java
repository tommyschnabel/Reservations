package edu.spsu.swe3613.login;

import com.google.inject.AbstractModule;

public class LoginModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LoginService.class).to(DefaultLoginService.class);
		bind(LoginDao.class).to(SqLiteLoginDao.class);
	}
}
