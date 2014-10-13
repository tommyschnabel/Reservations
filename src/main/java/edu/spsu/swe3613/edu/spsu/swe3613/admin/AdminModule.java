package edu.spsu.swe3613.edu.spsu.swe3613.admin;

import com.google.inject.AbstractModule;

public class AdminModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AdminDao.class).to(SqLiteAdminDao.class);
        bind(AdminService.class).to(DefaultAdminService.class);
    }
}
