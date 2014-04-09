package com.renefernandez.whenapp.persistence;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.Database;
import com.turbomanage.storm.api.DatabaseFactory;

//https://github.com/turbomanage/storm-gen

@Database(name = WhenappDatabaseHelper.DB_NAME, version = WhenappDatabaseHelper.DB_VERSION)
public class WhenappDatabaseHelper extends DatabaseHelper {

	public WhenappDatabaseHelper(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
	}

	public static final String DB_NAME = "whenappDB";
	public static final int DB_VERSION = 2;

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		return UpgradeStrategy.DROP_CREATE;
	}

}
