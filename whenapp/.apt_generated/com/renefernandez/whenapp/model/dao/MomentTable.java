package com.renefernandez.whenapp.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import com.turbomanage.storm.query.FilterBuilder;
import com.turbomanage.storm.TableHelper;
import java.util.Map;
import java.util.HashMap;
import com.renefernandez.whenapp.model.Moment;
import com.turbomanage.storm.SQLiteDao;
import com.turbomanage.storm.types.DateConverter;
import com.turbomanage.storm.types.LongConverter;
import com.turbomanage.storm.types.DoubleConverter;
import com.turbomanage.storm.types.StringConverter;

/**
 * GENERATED CODE
 *
 * This class contains the SQL DDL for the named entity / table.
 * These methods are not included in the EntityDao class because
 * they must be executed before the Dao can be instantiated, and
 * they are instance methods vs. static so that they can be
 * overridden in a typesafe manner.
 *
 * @author David M. Chandler
 */
public class MomentTable extends TableHelper<Moment> {

	public enum Columns implements TableHelper.Column {
		DATE,
		_id,
		LATITUDE,
		LONGITUDE,
		TITLE
	}

	@Override
	public String getTableName() {
		return "Moment";
	}

	@Override
	public Column[] getColumns() {
		return Columns.values();
	}
	
	@Override
	public long getId(Moment obj) {
		return obj.getId();
	}
	
	@Override
	public void setId(Moment obj, long id) {
		obj.setId(id);
	}

	@Override
	public Column getIdCol() {
		return Columns._id;
	}

	@Override
	public String createSql() {
		return
			"CREATE TABLE IF NOT EXISTS Moment(" +
				"DATE INTEGER," +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				"LATITUDE REAL," +
				"LONGITUDE REAL," +
				"TITLE TEXT" +
			")";
	}

	@Override
	public String dropSql() {
		return "DROP TABLE IF EXISTS Moment";
	}

	@Override
	public String upgradeSql(int oldVersion, int newVersion) {
		return null;
	}

	@Override
	public String[] getRowValues(Cursor c) {
		String[] values = new String[this.getColumns().length];
		String[] defaultValues = getDefaultValues();
		int colIdx; // entity field's position in the cursor
		colIdx = c.getColumnIndex("DATE"); values[0] = (colIdx < 0) ? defaultValues[0] : DateConverter.GET.toString(getLongOrNull(c, colIdx));
		colIdx = c.getColumnIndex("_id"); values[1] = (colIdx < 0) ? defaultValues[1] : LongConverter.GET.toString(getLongOrNull(c, colIdx));
		colIdx = c.getColumnIndex("LATITUDE"); values[2] = (colIdx < 0) ? defaultValues[2] : DoubleConverter.GET.toString(getDoubleOrNull(c, colIdx));
		colIdx = c.getColumnIndex("LONGITUDE"); values[3] = (colIdx < 0) ? defaultValues[3] : DoubleConverter.GET.toString(getDoubleOrNull(c, colIdx));
		colIdx = c.getColumnIndex("TITLE"); values[4] = (colIdx < 0) ? defaultValues[4] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		return values;
	}

	@Override
	public void bindRowValues(InsertHelper insHelper, String[] rowValues) {
		if (rowValues[0] == null) insHelper.bindNull(1); else insHelper.bind(1, DateConverter.GET.fromString(rowValues[0]));
		if (rowValues[1] == null) insHelper.bindNull(2); else insHelper.bind(2, LongConverter.GET.fromString(rowValues[1]));
		if (rowValues[2] == null) insHelper.bindNull(3); else insHelper.bind(3, DoubleConverter.GET.fromString(rowValues[2]));
		if (rowValues[3] == null) insHelper.bindNull(4); else insHelper.bind(4, DoubleConverter.GET.fromString(rowValues[3]));
		if (rowValues[4] == null) insHelper.bindNull(5); else insHelper.bind(5, StringConverter.GET.fromString(rowValues[4]));
	}

	@Override
	public String[] getDefaultValues() {
		String[] values = new String[getColumns().length];
		Moment defaultObj = new Moment();
		values[0] = DateConverter.GET.toString(DateConverter.GET.toSql(defaultObj.getDate()));
		values[1] = LongConverter.GET.toString(LongConverter.GET.toSql(defaultObj.getId()));
		values[2] = DoubleConverter.GET.toString(DoubleConverter.GET.toSql(defaultObj.getLatitude()));
		values[3] = DoubleConverter.GET.toString(DoubleConverter.GET.toSql(defaultObj.getLongitude()));
		values[4] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getTitle()));
		return values;
	}

	@Override
	public Moment newInstance(Cursor c) {
		Moment obj = new Moment();
		obj.setDate(DateConverter.GET.fromSql(getLongOrNull(c, 0)));
		obj.setId(c.getLong(1));
		obj.setLatitude(DoubleConverter.GET.fromSql(getDoubleOrNull(c, 2)));
		obj.setLongitude(DoubleConverter.GET.fromSql(getDoubleOrNull(c, 3)));
		obj.setTitle(c.getString(4));
		return obj;
	}

	@Override
	public ContentValues getEditableValues(Moment obj) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", DateConverter.GET.toSql(obj.getDate()));
		cv.put("_id", obj.getId());
		cv.put("LATITUDE", DoubleConverter.GET.toSql(obj.getLatitude()));
		cv.put("LONGITUDE", DoubleConverter.GET.toSql(obj.getLongitude()));
		cv.put("TITLE", obj.getTitle());
		return cv;
	}

	@Override
	public FilterBuilder buildFilter(FilterBuilder filter, Moment obj) {
		Moment defaultObj = new Moment();
		// Include fields in query if they differ from the default object
		if (obj.getDate() != defaultObj.getDate())
			filter = filter.eq(Columns.DATE, DateConverter.GET.toSql(obj.getDate()));
		if (obj.getId() != defaultObj.getId())
			filter = filter.eq(Columns._id, LongConverter.GET.toSql(obj.getId()));
		if (obj.getLatitude() != defaultObj.getLatitude())
			filter = filter.eq(Columns.LATITUDE, DoubleConverter.GET.toSql(obj.getLatitude()));
		if (obj.getLongitude() != defaultObj.getLongitude())
			filter = filter.eq(Columns.LONGITUDE, DoubleConverter.GET.toSql(obj.getLongitude()));
		if (obj.getTitle() != defaultObj.getTitle())
			filter = filter.eq(Columns.TITLE, StringConverter.GET.toSql(obj.getTitle()));
		return filter;
	}

}