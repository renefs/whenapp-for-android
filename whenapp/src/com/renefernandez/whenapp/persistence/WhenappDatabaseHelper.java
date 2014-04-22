package com.renefernandez.whenapp.persistence;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.Database;
import com.turbomanage.storm.api.DatabaseFactory;

/**
 * Clase de ayuda para la gestión de la base de datos. Se utiliza el ORM
 * Storm-Gen: //https://github.com/turbomanage/storm-gen
 * 
 * Para utilizarse, debe añadirse un preprocesador a la configuración del
 * proyecto, como se especifica en la página de Github del proyecto.
 * 
 * @author rene
 * 
 */
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
