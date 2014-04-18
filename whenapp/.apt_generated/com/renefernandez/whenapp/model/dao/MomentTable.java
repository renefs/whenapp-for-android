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
import com.turbomanage.storm.types.BlobConverter;
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
		IMAGE,
		LATITUDE,
		LONGITUDE,
		THUMBNAIL,
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
				"IMAGE BLOB," +
				"LATITUDE REAL," +
				"LONGITUDE REAL," +
				"THUMBNAIL BLOB," +
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
		colIdx = c.getColumnIndex("IMAGE"); values[2] = (colIdx < 0) ? defaultValues[2] : BlobConverter.GET.toString(getBlobOrNull(c, colIdx));
		colIdx = c.getColumnIndex("LATITUDE"); values[3] = (colIdx < 0) ? defaultValues[3] : DoubleConverter.GET.toString(getDoubleOrNull(c, colIdx));
		colIdx = c.getColumnIndex("LONGITUDE"); values[4] = (colIdx < 0) ? defaultValues[4] : DoubleConverter.GET.toString(getDoubleOrNull(c, colIdx));
		colIdx = c.getColumnIndex("THUMBNAIL"); values[5] = (colIdx < 0) ? defaultValues[5] : BlobConverter.GET.toString(getBlobOrNull(c, colIdx));
		colIdx = c.getColumnIndex("TITLE"); values[6] = (colIdx < 0) ? defaultValues[6] : StringConverter.GET.toString(getStringOrNull(c, colIdx));
		return values;
	}

	@Override
	public void bindRowValues(InsertHelper insHelper, String[] rowValues) {
		if (rowValues[0] == null) insHelper.bindNull(1); else insHelper.bind(1, DateConverter.GET.fromString(rowValues[0]));
		if (rowValues[1] == null) insHelper.bindNull(2); else insHelper.bind(2, LongConverter.GET.fromString(rowValues[1]));
		if (rowValues[2] == null) insHelper.bindNull(3); else insHelper.bind(3, BlobConverter.GET.fromString(rowValues[2]));
		if (rowValues[3] == null) insHelper.bindNull(4); else insHelper.bind(4, DoubleConverter.GET.fromString(rowValues[3]));
		if (rowValues[4] == null) insHelper.bindNull(5); else insHelper.bind(5, DoubleConverter.GET.fromString(rowValues[4]));
		if (rowValues[5] == null) insHelper.bindNull(6); else insHelper.bind(6, BlobConverter.GET.fromString(rowValues[5]));
		if (rowValues[6] == null) insHelper.bindNull(7); else insHelper.bind(7, StringConverter.GET.fromString(rowValues[6]));
	}

	@Override
	public String[] getDefaultValues() {
		String[] values = new String[getColumns().length];
		Moment defaultObj = new Moment();
		values[0] = DateConverter.GET.toString(DateConverter.GET.toSql(defaultObj.getDate()));
		values[1] = LongConverter.GET.toString(LongConverter.GET.toSql(defaultObj.getId()));
		values[2] = BlobConverter.GET.toString(BlobConverter.GET.toSql(defaultObj.getImage()));
		values[3] = DoubleConverter.GET.toString(DoubleConverter.GET.toSql(defaultObj.getLatitude()));
		values[4] = DoubleConverter.GET.toString(DoubleConverter.GET.toSql(defaultObj.getLongitude()));
		values[5] = BlobConverter.GET.toString(BlobConverter.GET.toSql(defaultObj.getThumbnail()));
		values[6] = StringConverter.GET.toString(StringConverter.GET.toSql(defaultObj.getTitle()));
		return values;
	}

	@Override
	public Moment newInstance(Cursor c) {
		Moment obj = new Moment();
		obj.setDate(DateConverter.GET.fromSql(getLongOrNull(c, 0)));
		obj.setId(c.getLong(1));
		obj.setImage(c.getBlob(2));
		obj.setLatitude(DoubleConverter.GET.fromSql(getDoubleOrNull(c, 3)));
		obj.setLongitude(DoubleConverter.GET.fromSql(getDoubleOrNull(c, 4)));
		obj.setThumbnail(c.getBlob(5));
		obj.setTitle(c.getString(6));
		return obj;
	}

	@Override
	public ContentValues getEditableValues(Moment obj) {
		ContentValues cv = new ContentValues();
		cv.put("DATE", DateConverter.GET.toSql(obj.getDate()));
		cv.put("_id", obj.getId());
		cv.put("IMAGE", obj.getImage());
		cv.put("LATITUDE", DoubleConverter.GET.toSql(obj.getLatitude()));
		cv.put("LONGITUDE", DoubleConverter.GET.toSql(obj.getLongitude()));
		cv.put("THUMBNAIL", obj.getThumbnail());
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
		if (obj.getImage() != defaultObj.getImage())
			filter = filter.eq(Columns.IMAGE, BlobConverter.GET.toSql(obj.getImage()));
		if (obj.getLatitude() != defaultObj.getLatitude())
			filter = filter.eq(Columns.LATITUDE, DoubleConverter.GET.toSql(obj.getLatitude()));
		if (obj.getLongitude() != defaultObj.getLongitude())
			filter = filter.eq(Columns.LONGITUDE, DoubleConverter.GET.toSql(obj.getLongitude()));
		if (obj.getThumbnail() != defaultObj.getThumbnail())
			filter = filter.eq(Columns.THUMBNAIL, BlobConverter.GET.toSql(obj.getThumbnail()));
		if (obj.getTitle() != defaultObj.getTitle())
			filter = filter.eq(Columns.TITLE, StringConverter.GET.toSql(obj.getTitle()));
		return filter;
	}

}