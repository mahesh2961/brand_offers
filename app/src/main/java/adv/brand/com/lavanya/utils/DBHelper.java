package adv.brand.com.lavanya.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import adv.brand.com.lavanya.model.OfferModel;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Brands.db";

	private static  final int DB_VERSION=1;

	public static final String OFFERS_TABLE = "Offers";
	public static final String COL_ID = "Id";
	public static final String COL_TITLE = "Title";
	public static final String COL_DESCP = "Descp";
	public static final String COL_CATEGORY = "Catgry";
	public static final String COL_IMG_URL="ImgUrl";
	public static final String COL_REDRCT_URL="RedrctUrl";

	public static final String FILTER_TABLE = "filters";


	private static DBHelper instance;

	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}


	public static DBHelper getInstance(Context context)
	{
		if (instance==null)
		{
			instance=new DBHelper(context);
		}

		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	// TODO Auto-generated method stub
		String ReportTable = "CREATE TABLE IF NOT EXISTS " + OFFERS_TABLE + " ("
				+ COL_ID + " text not null, "
				+ COL_TITLE + " text, "
				+ COL_DESCP + " text, "
				+ COL_CATEGORY + " text, "
				+ COL_IMG_URL + " text,"
				+ COL_REDRCT_URL+" text );";


		String filterTable = "CREATE TABLE IF NOT EXISTS " + FILTER_TABLE+ " ("
				+ COL_TITLE+" text );";

		db.execSQL(ReportTable);
		db.execSQL(filterTable);


	}


	private SQLiteDatabase getDb()
	{
	return 	instance.getWritableDatabase();
	}


	public void insertOffers(List<OfferModel> offerModels)
	{
		if (offerModels!=null && offerModels.size()>0) {
			SQLiteDatabase db=getDb();
			db.delete(OFFERS_TABLE,null,null);
			db.beginTransaction();
			try {
                ContentValues values = new ContentValues();
                for (OfferModel offer : offerModels) {


                    values.put(COL_ID, offer.getId());
                    values.put(COL_IMG_URL, offer.getImgUrl());
                    values.put(COL_DESCP, offer.getDesc());
                    values.put(COL_TITLE, offer.getTitle());
                    values.put(COL_REDRCT_URL, offer.getRedirect());
                    values.put(COL_CATEGORY, offer.getCategory());
                    db.insert(OFFERS_TABLE, null, values);
					Log.i("kk","Inserted value with Title::"+offer.getTitle());

                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
		}


	}

	public void insertFilters(List<String> selectedFilters)
	{
		if (selectedFilters!=null && selectedFilters.size()>0) {
			SQLiteDatabase db=getDb();
			db.delete(FILTER_TABLE,null,null);
			db.beginTransaction();
			try {
				ContentValues values = new ContentValues();
				for (String filterStr : selectedFilters) {

					values.put(COL_TITLE, filterStr);
					Log.i("kk","Inserted filter with Title::"+filterStr);
					db.insert(FILTER_TABLE, null, values);

				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}


	}

	public void emptyTable(String tableName)
	{
		try {
			SQLiteDatabase db=getDb();
			db.delete(FILTER_TABLE,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public List<OfferModel> getFilterOffers()
	{
		return getOffersByCategories(getFilter());
	}

	public List<String> getFilter()
	{
		List<String> selectedFilters=null;
		Cursor cursor=null;
		try {
			SQLiteDatabase db = getDb();
			String query="Select * from "+FILTER_TABLE;
			cursor =db.rawQuery(query,null);
			if (cursor.getCount() > 0)
			{
				selectedFilters= new ArrayList<>();
				cursor.moveToFirst();
				do {
					selectedFilters.add(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
				} while (cursor.moveToNext());
			}
		}
		catch (Exception e)
		{

		}
		finally {
			if (cursor!=null)
			{
				cursor.close();
				cursor=null;
			}
		}

		return selectedFilters;
	}

	public List<OfferModel> getOffersByCategories(List<String> categories)
	{
		List<OfferModel> offers=null;
		Cursor cursor=null;
		try {
			SQLiteDatabase db=getDb();
			String query="Select * from "+OFFERS_TABLE+" where"+(makeLikeQuery(COL_CATEGORY,categories));
			Log.d("kk", "getOffersByCategories: Query::"+query);
			cursor =db.rawQuery(query,null);
			if (cursor.getCount() > 0)
			{
				offers= new ArrayList<>();
				cursor.moveToFirst();
				do {
					OfferModel model=new OfferModel();
					model.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
					model.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
					model.setDesc(cursor.getString(cursor.getColumnIndex(COL_DESCP)));
					model.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
					model.setImgUrl(cursor.getString(cursor.getColumnIndex(COL_IMG_URL)));
					model.setRedirect(cursor.getString(cursor.getColumnIndex(COL_REDRCT_URL)));
					offers.add(model);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (cursor!=null)
			{
				cursor.close();
				cursor=null;
			}
		}
      return offers;
	}

	private String makeLikeQuery(String coloumn,List<String> values)
	{
      String result="";
		for (int i = 0; i < values.size(); i++) {
			if(i!=(values.size()-1)) {
				result = result + " " + coloumn + " like '%" + values.get(i) + "%' OR";
			}
			else
				result = result + " " + coloumn + " like '%" + values.get(i) + "%'";

		}

		return result;
	}

	public long getRowCount(String tableName) {
		SQLiteDatabase db = this.getReadableDatabase();
		long cnt  = DatabaseUtils.queryNumEntries(db, tableName);
		return cnt;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/*
		 * db.execSQL("DROP TABLE IF EXISTS contacts"); onCreate(db);
		 */
	}







}