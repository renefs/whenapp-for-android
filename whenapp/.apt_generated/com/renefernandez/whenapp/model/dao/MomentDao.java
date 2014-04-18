package com.renefernandez.whenapp.model.dao;

import android.content.Context;
import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.TableHelper;
import com.renefernandez.whenapp.model.Moment;
import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.types.DateConverter;
import com.turbomanage.storm.types.LongConverter;
import com.turbomanage.storm.types.BlobConverter;
import com.turbomanage.storm.types.DoubleConverter;
import com.turbomanage.storm.types.StringConverter;

/**
 * GENERATED CODE
 *
 * @author David M. Chandler
 */
public class MomentDao extends SQLiteDao<Moment>{

    @Override
	public DatabaseHelper getDbHelper(Context ctx) {
		return com.renefernandez.whenapp.persistence.WhenappDBFactory.getDatabaseHelper(ctx);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public TableHelper getTableHelper() {
		return new com.renefernandez.whenapp.model.dao.MomentTable();
	}

	/**
	 * @see SQLiteDao#SQLiteDao(Context)
	 */
	public MomentDao(Context ctx) {
		super(ctx);
	}

}