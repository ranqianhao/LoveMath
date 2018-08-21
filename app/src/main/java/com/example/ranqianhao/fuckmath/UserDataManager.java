package com.example.ranqianhao.fuckmath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class UserDataManager {
    private static final String TABLE_NAME ="userDb" ;
    private static final String ID ="userId";
    private static final String DB_NAME = "userData";
    private static final String USER_NAME = "userName";
    private static final String USER_PSW ="userPsw" ;
    private static Context mContext;
    private static final int DB_VERSION=2;

//    private static final String DB_CREATE = "create table bookStore ("
//            + "id integer primary key autoincrement,"
//            + "book_name text, "
//            + "author text, "
//            + "price real)";//数据库里的表

    private static final String DB_CREATE = "create table userDb ("
            + "ID integer primary key autoincrement, "
            + "USER_NAME varchar, "
            + "USER_PSW varchar)";


 //    private static final String DB_CREATE = new StringBuilder().append("CREATE TABLE").append(TABLE_NAME).append("(").append(ID).append("integer primary key,").append(USER_NAME).append("varchar,").append(USER_PSW).append("varchar").append(");").toString();
    // private static final String DB_CREATE= String.format("CREATE TABLE%s(%sInteger primary key,%svarchar,%svarchar);", TABLE_NAME, ID, USER_NAME, USER_PSW);
    private static SQLiteDatabase mSQLiteDatabase = null;
    private static DataBaseManagementHelper mDatabaseHelper = null;


    //创建book表
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //创建数据库
        @Override
        public void onCreate(SQLiteDatabase db) {
           Log.i(TAG, "db.getVersion()=" + db.getVersion());
           db.execSQL(new StringBuilder().append("DROP TABLE IF EXISTS").append(TABLE_NAME).append(";").toString());
            db.execSQL(DB_CREATE);
            Log.i(TAG, "db.execSQL(DB_CREATE)");
            Log.e(TAG, DB_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            onCreate(db);
        }
    }

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG,"UserDataManager construction!");
    }

    //打开数据库  
    public static void openDataBase() throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    //关闭数据库  
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }


    //注册新用户  Userdata是同一package下另一个class Userdata。。getUserName是其方法
    public static long insertUserData(Userdata userdata) {
        String userName=userdata.getUserName();
        String userPsw=userdata.getUserPsw();
        ContentValues values = new ContentValues();
        values.put(USER_NAME,userName);
        values.put(USER_PSW, userPsw);
        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);

        }

    //更新用户信息，如修改密码
    public boolean updateUserData(Userdata userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPsw = userData.getUserPsw();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PSW, userPsw);
        return mSQLiteDatabase.update(TABLE_NAME, values,null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + ”=” + id, null) > 0;
         }

         //cursor相当于记录中的指针
    public Cursor fetchUserData(int id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor fetchAllUserDatas() {
        return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
                null);
         }

         //根据id删除用户
         public boolean deletUserData(int id){
            return mSQLiteDatabase.delete(TABLE_NAME,ID +"="+id,null)>0;
         }

         //根据用户名注销
    public boolean deletUserDataWithUserName(String userName){
            return mSQLiteDatabase.delete(TABLE_NAME,USER_NAME+"="+userName,null)>0;
    }
         //删除所有用户
    public boolean deletAllUsers(){
            return mSQLiteDatabase.delete(TABLE_NAME,null,null)>0;
    }

    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }
    //根据id更新
    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public static int findUserByName(String userName){
        Log.i(TAG,"findUserByName , userName="+userName);
        int result=0;
      //  Cursor mCursor=mSQLiteDatabase.rawQuery("SELECT * FROM userDb WHERE userName=?",USER_NAME);
         Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME + "=" + userName, null, null, null, null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return result;
    }



    //用密码和用户名查询用户，可以用来验证登录
public int findUserByNameAndPsw(String userName,String psw){
            Log.i(TAG,"findUserByNameAndPsw");
            int result = 0;
            Cursor mcursor = mSQLiteDatabase.query(TABLE_NAME,null,USER_NAME+"="+userName+"and"+USER_PSW+"="+psw,null,null,null,null);
            if (mcursor != null){
                result = mcursor.getCount();
                mcursor.close();
                Log.i(TAG, "findUserByNameAndPsw,result "+result);
            }
            return result;
        }
    }

