package adv.brand.com.lavanya.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import adv.brand.com.lavanya.model.OfferModel;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Brands.db";

	private static  final int DB_VERSION=1;

	/*----------------------------Report Table----------------------------------------------*/
	public static final String OFFERS_TABLE = "Offers";
	public static final String COL_ID = "Id";
	public static final String COL_TITLE = "Title";
	public static final String COL_DESCP = "Descp";
	public static final String COL_CATEGORY = "Catgry";
	public static final String COL_IMG_URL="ImgUrl";
	public static final String COL_REDRCT_URL="RedrctUrl";

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
		String ReportTable = "CREATE TABLE " + OFFERS_TABLE + " ("
				+ COL_ID + " text not null, "
				+ COL_TITLE + " text, "
				+ COL_DESCP + " text, "
				+ COL_CATEGORY + " text, "
				+ COL_IMG_URL + " text,"
				+ COL_REDRCT_URL+" text );";

		db.execSQL(ReportTable);


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

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/*
		 * db.execSQL("DROP TABLE IF EXISTS contacts"); onCreate(db);
		 */
	}





}