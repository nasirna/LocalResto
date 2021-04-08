package com.food.localresto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.food.localresto.entities.BackupOrderItemObject;
import com.food.localresto.entities.BackupOrderObject;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.HistoryObject;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.entities.RestaurantObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SqliteDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static SqliteDatabase sInstance;
    private Context context;

    // Database Info
    private	static final int DATABASE_VERSION =	1;
    public static final String DATABASE_NAME = "LocalResto";

    // Table Names
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_MENUCAT = "menu_category";
    public static final String TABLE_MENUITEM = "menu_item";
    public static final String TABLE_MENUORDER = "menu_order";
    public static final String TABLE_ORDERITEM = "order_menu_item";
    public static final String TABLE_USER = "user";
    public static final String TABLE_RESTO = "restaurant";
    public static final String TABLE_FAV = "favorite";

    // Products Table Columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IMAGE = "image";

    // menu_category Table Columns
    private static final String MENUCAT_ID = "tb_menu_category_id";
    private static final String MENUCAT_MENUID = "menu_id";
    private static final String MENUCAT_MENUJEN = "menu_jenis";
    private static final String MENUCAT_MENUNAME = "menu_name";
    private static final String MENUCAT_MENUIMAGE = "menu_image";
    private static final String MENUCAT_MENUCREATE = "created_at";
    private static final String MENUCAT_MENUUPDATE = "updated_at";
/*
    CREATE TABLE "menu_category" (
            "tb_menu_category_id"	INTEGER NOT NULL,
            "menu_id"	INTEGER NOT NULL,
            "menu_jenis"	varchar(20) NOT NULL,
	"menu_name"	varchar(100) NOT NULL,
	"menu_image"	BLOB,
            "created_at"	INTEGER,
            "updated_at"	INTEGER,
    PRIMARY KEY("tb_menu_category_id","menu_name")
)*/
/*
    public static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE "
            + EMPLOYEE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + EMPLOYEE_SALARY + " DOUBLE, "
            + EMPLOYEE_DOB + " DATE, " + EMPLOYEE_DEPARTMENT_ID + " INT, "
            + "FOREIGN KEY(" + EMPLOYEE_DEPARTMENT_ID + ") REFERENCES "
            + DEPARTMENT_TABLE + "(id) " + ")";*/

    private static final String db_create_menu_category = "CREATE TABLE "
            + TABLE_MENUCAT + "("
            + MENUCAT_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + MENUCAT_MENUID+ " INTEGER NOT NULL, "
            + MENUCAT_MENUJEN+ " VARCHAR(20) NOT NULL, "
            + MENUCAT_MENUNAME+ " VARCHAR(100) NOT NULL, "
            + MENUCAT_MENUIMAGE+ " BLOB, "
            + MENUCAT_MENUCREATE+ " INTEGER, "
            + MENUCAT_MENUUPDATE+ " INTEGER); ";
           // + " PRIMARY KEY("+MENUCAT_ID+","+MENUCAT_MENUNAME+"));";

   /* db.execSQL("CREATE TRIGGER fk_menucat_id " +
            " BEFORE INSERT "+
            " ON "+employeeTable+

            " FOR EACH ROW BEGIN"+
            " SELECT CASE WHEN ((SELECT "+colDeptID+" FROM "+deptTable+" _
    WHERE "+colDeptID+"=new."+colDept+" ) IS NULL)"+
            " THEN RAISE (ABORT,'Foreign Key Violation') END;"+
            "  END;");*/


    // menu_item Table Columns
    private static final String ITEM_ID = "tb_menu_item_id";
    private static final String ITEM_KODE = "kodeitem";
    private static final String ITEM_MENUID = "menu_id";
    private static final String ITEM_MENUJEN = "menu_jenis";
    private static final String ITEM_MENU = "menu_name";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_DESC = "description";
    private static final String ITEM_PICT = "item_picture";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_QUAN = "item_quantity";
    private static final String ITEM_OPT = "item_options";
    private static final String ITEM_NOTE = "item_notes";
    private static final String ITEM_DEAL = "hot_deal";
    private static final String ITEM_ACTIVATED = "activated";
    private static final String ITEM_PRICE2 = "item_price2";
    private static final String ITEM_PRICE3 = "item_price3";
    private static final String ITEM_DISC = "disc";
    private static final String ITEM_ID_REF_FK = "tb_menu_category_id";
    private static final String ITEM_MENUCREATE = "created_at";
    private static final String ITEM_MENUUPDATE = "updated_at";
    /*
    CREATE TABLE "menu_item" (
	"tb_menu_item_id"	INTEGER NOT NULL,
	"kodeitem"	varchar(20) NOT NULL,
	"menu_id"	INTEGER NOT NULL,
	"menu_jenis"	varchar(20),
	"menu_name"	varchar(100),
	"item_name"	varchar(100) NOT NULL,
	"description"	TEXT,
	"item_picture"	TEXT,
	"item_price"	float,
	"item_quantity"	int(8),
	"item_options"	varchar(200) DEFAULT NULL,
	"item_notes"	varchar(400) DEFAULT NULL,
	"hot_deal"	tinyint(1) DEFAULT 0,
	"tb_menu_category_id"	INTEGER NOT NULL,
	"created_at"	INTEGER,
	"updated_at"	INTEGER,
	CONSTRAINT "fk_menu_item" FOREIGN KEY("tb_menu_category_id","menu_name") REFERENCES "menu_category"("tb_menu_category_id","menu_name") ON DELETE RESTRICT ON UPDATE CASCADE,
	PRIMARY KEY("tb_menu_item_id" AUTOINCREMENT)
)
     */

    private static final String db_create_menu_item = "CREATE TABLE "
            + TABLE_MENUITEM + "("
            + ITEM_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + ITEM_KODE+ " VARCHAR(20) NOT NULL, "
            + ITEM_MENUID+ " INTEGER NOT NULL, "
            + ITEM_MENUJEN+ " VARCHAR(20) NOT NULL, "
            + ITEM_MENU+ " VARCHAR(100), "
            + ITEM_NAME+ " VARCHAR(100) NOT NULL, "
            + ITEM_DESC+ " TEXT, "
            + ITEM_PICT+ " TEXT, "
            + ITEM_PRICE+ " FLOAT DEFAULT 0, "
            + ITEM_PRICE2+ " FLOAT DEFAULT 0, "
            + ITEM_PRICE3+ " FLOAT DEFAULT 0, "
            + ITEM_QUAN+ " INTEGER, "
            + ITEM_OPT+ " varchar(200) DEFAULT NULL, "
            + ITEM_NOTE+ " varchar(400) DEFAULT NULL, "
            + ITEM_DEAL+ " tinyint(1) DEFAULT 0, "
            + ITEM_ACTIVATED+ " varchar(1) DEFAULT 'Y', "
            + ITEM_DISC+ " varchar(50) DEFAULT 'NONE', "
            //+ ITEM_ID_REF+ " INTEGER NOT NULL, "
            + ITEM_ID_REF_FK + " INTEGER REFERENCES " + TABLE_MENUCAT + ","  // Define a foreign key
            + ITEM_MENUCREATE+ " INTEGER, "
            + ITEM_MENUUPDATE+ " INTEGER); ";
            //+ "CONSTRAINT " + "fk_menu_item" + " FOREIGN KEY("+ITEM_ID_REF+","+ITEM_MENU+") "
            //+ "REFERENCES "+TABLE_MENUCAT+"("+MENUCAT_ID+","+MENUCAT_MENUNAME+") "
            //+ "ON DELETE RESTRICT ON UPDATE CASCADE); ";
           // + " PRIMARY KEY("+ITEM_ID+" AUTOINCREMENT"+"));";

/*
    private static final String db_create_trig_menu_item = "CREATE TRIGGER fk_menuitem_id " +
            " BEFORE INSERT "+
            " ON "+TABLE_MENUCAT+
            " FOR EACH ROW BEGIN"+
            " SELECT CASE WHEN ((SELECT "+colDeptID+" FROM "+deptTable+" _
    WHERE "+colDeptID+"=new."+colDept+" ) IS NULL)"+
            " THEN RAISE (ABORT,'Foreign Key Violation') END;"+
            "  END;";*/

    // menu_order Table Columns
    private static final String MENUORDER_ID = "tb_order_id";
    private static final String MENUORDER_ORDERID = "order_id";
    private static final String MENUORDER_USERID = "user_id";
    private static final String MENUORDER_QUANT = "order_quantity";
    private static final String MENUORDER_PRICE = "order_price";
    private static final String MENUORDER_DATE = "order_date";
    private static final String MENUORDER_STATUS = "status";
    private static final String MENUORDER_METHOD = "payment_method";
    private static final String MENUORDER_TABEL = "tabel";
    private static final String MENUORDER_PAJAK = "pajak";
    private static final String MENUORDER_DISC = "disc";
    private static final String MENUORDER_NETTOT = "nettot";
    private static final String MENUORDER_TOTBAYAR = "totbayar";
    private static final String MENUORDER_MENUCREATE = "created_at";
    private static final String MENUORDER_MENUUPDATE = "updated_at";
    /*
CREATE TABLE "menu_order" (
	"tb_order_id"	INTEGER NOT NULL,
	"order_id"	varchar(50) NOT NULL,
	"user_id"	int(4) NOT NULL,
	"order_quantity"	int(4),
	"order_price"	float,
	"order_date"	datetime DEFAULT '0000-00-00 00:00:00',
	"status"	varchar(70),
	"payment_method"	varchar(50),
	"tabel"	int(4),
	PRIMARY KEY("tb_order_id" AUTOINCREMENT)
)
     */
    private static final String db_create_menu_order = "CREATE TABLE "
            + TABLE_MENUORDER + "("
            + MENUORDER_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + MENUORDER_ORDERID+ " VARCHAR(50) NOT NULL, "
            + MENUORDER_USERID+ " INTEGER NOT NULL, "
            + MENUORDER_QUANT+ " INTEGER, "
            + MENUORDER_PRICE+ " FLOAT, "
            + MENUORDER_DATE+ " DATETIME DEFAULT '0000-00-00 00:00:00', "
            + MENUORDER_STATUS+ " VARCHAR(70), "
            + MENUORDER_METHOD+ " VARCHAR(50), "
            + MENUORDER_TABEL+ " INTEGER, "
            + MENUORDER_PAJAK+ " INTEGER, "
            + MENUORDER_DISC+ " INTEGER, "
            + MENUORDER_NETTOT+ " FLOAT, "
            + MENUORDER_TOTBAYAR+ " FLOAT, "
            + MENUORDER_MENUCREATE+ " INTEGER, "
            + MENUORDER_MENUUPDATE+ " INTEGER); ";
            //+ " PRIMARY KEY("+MENUORDER_ID+" AUTOINCREMENT));";


    // order_menu_item Table Columns
    private static final String ORDER_ID = "tb_order_menu_item_id";
    private static final String ORDER_ORDERID = "order_id";
    private static final String ORDER_KODE = "kodeitem";
    private static final String ORDER_QUANT = "quantity";
    private static final String ORDER_PRICE = "price";
    private static final String ORDER_SUBTOT = "subtotal";
    private static final String ORDER_OPT = "options";
    private static final String ORDER_NOTE = "notes";
    private static final String ORDER_MENUCREATE = "created_at";
    private static final String ORDER_MENUUPDATE = "updated_at";
/*
CREATE TABLE "order_menu_item" (
	"tb_order_menu_item_id"	INTEGER NOT NULL,
	"order_id"	varchar(50) NOT NULL,
	"kodeitem"	varchar(20) NOT NULL,
	"quantity"	int(4) NOT NULL,
	"price"	float NOT NULL,
	"subtotal"	float NOT NULL,
	"options"	varchar(100) NOT NULL,
	"notes"	varchar(200) NOT NULL,
	"created_at"	timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
	"updated_at"	timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
	PRIMARY KEY("tb_order_menu_item_id" AUTOINCREMENT)
)
 */

    private static final String db_create_order_menu_item = "CREATE TABLE "
            + TABLE_ORDERITEM + "("
            + ORDER_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + ORDER_ORDERID+ " VARCHAR(50) NOT NULL, "
            + ORDER_KODE+ " INTEGER NOT NULL, "
            + ORDER_QUANT+ " INTEGER, "
            + ORDER_PRICE+ " FLOAT, "
            + ORDER_SUBTOT+ " FLOAT, "
            + ORDER_OPT+ " VARCHAR(70), "
            + ORDER_NOTE+ " VARCHAR(50), "
            + ORDER_MENUCREATE+ " INTEGER, "
            + ORDER_MENUUPDATE+ " INTEGER); ";
            //+ " PRIMARY KEY("+ORDER_ID+" AUTOINCREMENT"+");";


    // restaurant Table Columns
    private static final String RESTO_ID = "tb_restaurant_id";
    private static final String RESTO_KODE = "kode";
    private static final String RESTO_NAME = "name";
    private static final String RESTO_DESC = "description";
    private static final String RESTO_ADDRESS = "address";
    private static final String RESTO_CITY = "city";
    private static final String RESTO_PROP = "Propinsi";
    private static final String RESTO_PHONE = "phone";
    private static final String RESTO_EMAIL = "email";
    private static final String RESTO_IMEI = "imei";
    private static final String RESTO_TIME = "opening_time";
    private static final String RESTO_MENUCREATE = "created_at";
    private static final String RESTO_MENUUPDATE = "updated_at";
/*
CREATE TABLE "restaurant" (
	"tb_restaurant_id"	INTEGER NOT NULL,
	"kode"	INTEGER NOT NULL,
	"name"	varchar(100) NOT NULL,
	"description"	TEXT NOT NULL,
	"address"	varchar(200) NOT NULL,
	"city"	varchar(50) NOT NULL,
	"Propinsi"	varchar(50) NOT NULL,
	"phone"	varchar(40) NOT NULL,
	"email"	varchar(70) NOT NULL,
	"imei"	INTEGER,
	"opening_time"	varchar(50) NOT NULL,
	"created_at"	INTEGER,
	"updated_at"	INTEGER,
	PRIMARY KEY("tb_restaurant_id" AUTOINCREMENT)
)
 */

    private static final String db_create_restaurant = "CREATE TABLE "
            + TABLE_RESTO + "("
            + RESTO_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + RESTO_KODE+ " VARCHAR(50) NOT NULL, "
            + RESTO_NAME+ " VARCHAR(100) NOT NULL, "
            + RESTO_DESC+ " VARCHAR(100) , "
            + RESTO_ADDRESS+ " INTEGER, "
            + RESTO_CITY+ " DATETIME DEFAULT '0000-00-00 00:00:00', "
            + RESTO_PROP+ " VARCHAR(70), "
            + RESTO_PHONE+ " VARCHAR(50), "
            + RESTO_EMAIL+ " VARCHAR(70), "
            + RESTO_IMEI+ " VARCHAR(50), "
            + RESTO_TIME+ " VARCHAR(70), "
            + RESTO_MENUCREATE+ " INTEGER, "
            + RESTO_MENUUPDATE+ " INTEGER); ";
           // + " PRIMARY KEY("+RESTO_ID+" AUTOINCREMENT"+");";

    // user Table Columns
    private static final String USER_ID = "tb_user_id";
    private static final String USER_NAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASS = "password";
    private static final String USER_ROLE = "role";
    private static final String USER_ADDRESS = "address";
    private static final String USER_PHONE = "phone_number";
    private static final String USER_MENUCREATE = "created_at";
    private static final String USER_MENUUPDATE = "updated_at";
    /*
CREATE TABLE "user" (
	"tb_user_id"	INTEGER NOT NULL,
	"username"	varchar(100) NOT NULL,
	"email"	varchar(100) NOT NULL,
	"password"	varchar(50) NOT NULL,
	"role"	INTEGER NOT NULL,
	"address"	varchar(100) NOT NULL,
	"phone_number"	varchar(50) NOT NULL,
	"created_at"	INTEGER,
	"updated_at"	INTEGER,
	PRIMARY KEY("tb_user_id" AUTOINCREMENT)
)
     */

    private static final String db_create_user = "CREATE TABLE "
            + TABLE_USER + "("
            + USER_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME+ " VARCHAR(50) NOT NULL, "
            + USER_EMAIL+ " VARCHAR(50) NOT NULL, "
            + USER_PASS+ " VARCHAR(50) NOT NULL, "
            + USER_ROLE+ " INTEGER, "
            + USER_ADDRESS+ " VARCHAR(70), "
            + USER_PHONE+ " VARCHAR(50), "
            + USER_MENUCREATE+ " INTEGER, "
            + USER_MENUUPDATE+ " INTEGER );";
            //+ " PRIMARY KEY("+USER_ID+" AUTOINCREMENT );";

/*
 public static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE "
            + EMPLOYEE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + NAME_COLUMN + " TEXT, " + EMPLOYEE_SALARY + " DOUBLE, "
            + EMPLOYEE_DOB + " DATE, " + EMPLOYEE_DEPARTMENT_ID + " INT, "
            + "FOREIGN KEY(" + EMPLOYEE_DEPARTMENT_ID + ") REFERENCES "
            + DEPARTMENT_TABLE + "(id) " + ")";

    public static final String CREATE_DEPARTMENT_TABLE = "CREATE TABLE "
            + DEPARTMENT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
            + NAME_COLUMN + ")";

 */
    ////
    public static synchronized SqliteDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SqliteDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        // Enable foreign key constraints
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

    /*@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }*/

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        // Perintah SQL untuk membuat tabel database baru
              private static final String db_create_bus = "create table"
                  + TABLE_BUS + "("
                  + COLUMN_ID_BUS +" integer primary key autoincrement, "
                  + COLUMN_NAMA_BUS+ " varchar(50) not null, "
                  + COLUMN_TIPE_BUS+ " varchar(50) not null, "
                  + COLUMN_HARGA_BUS+ " varchar(50) not null);";

              private static final String db_create_client = "create table"
                  + TABLE_CLIENT + "("
                  + COLUMN_ID_CLIENT +" integer primary key autoincrement, "
                  + COLUMN_NAMA_CLIENT+ " varchar(50) not null, "
                  + COLUMN_TELP_CLIENT+ " varchar(50) not null, "
                  + COLUMN_ALAMAT_CLIENT+ " varchar(50) not null);";

              private static final String db_create_trs = "create table"
                  + TABLE_TRS_TICKET_HDR + "("
                  + COLUMN_ID_TRS +" integer primary key autoincrement, "
                  + COLUMN_TGL_TRS+ " varchar(50) not null, "
                  + COLUMN_JM_TRS+ " varchar(50) not null, "
                  + COLUMN_ID_BUS_TRS+ " varchar(50) not null), "
                  + COLUMN_ID_CL_TRS+ "varchar(50) not null), "
                  + COLUMN_QTY+ " varchar(50) not null, "
                  + COLUMN_HARGA_TICKET+ " varchar(50) not null), "
                  + COLUMN_SUBTOTAL+ "varchar(50) not null),;";

                  db.execSQL(db_create_bus);
                db.execSQL(db_create_client);
                db.execSQL(db_create_trs);
         */
        /*

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS +
                "(" +
                    KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                    KEY_POST_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                    KEY_POST_TEXT + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                    KEY_USER_ID + " INTEGER PRIMARY KEY," +
                    KEY_USER_NAME + " TEXT," +
                    KEY_USER_PROFILE_PICTURE_URL + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);

         */
        String	CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " INTEGER," + COLUMN_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        db.execSQL(db_create_menu_category);
        db.execSQL(db_create_menu_item);
        db.execSQL(db_create_menu_order);
        db.execSQL(db_create_order_menu_item);
        db.execSQL(db_create_restaurant);
        db.execSQL(db_create_user);

        //db.execSQL(db_create_trig_menu_item);

        String	INIT_DATA_USER = "INSERT INTO " + TABLE_USER + " (" +
                USER_NAME + ", " +
                USER_EMAIL + ", " +
                USER_PASS + ", " +
                USER_ROLE + ", " +
                USER_ADDRESS + ", " +
                USER_PHONE +
                ") VALUES('admin','admin@test.com','sapi', 0,'Jakarta','0812345678')";

        db.execSQL(INIT_DATA_USER);

        String	INIT_DATA_RESTO = "INSERT INTO " + TABLE_RESTO + "("
                + RESTO_KODE+ ", "
                + RESTO_NAME+ ", "
                + RESTO_DESC+ ", "
                + RESTO_ADDRESS+ ", "
                + RESTO_CITY+ ", "
                + RESTO_PROP+ ", "
                + RESTO_PHONE+ ", "
                + RESTO_EMAIL+ ", "
                + RESTO_IMEI+ ", "
                + RESTO_TIME+
                ") VALUES('9999','Default CAfe `n Resto','Good taste in town', 'jl. Kebayoran Lama 44','Jakarta','Jakarta','021-8656765','default_cafe@gmail.com','12345','10:00-21:00')";

        db.execSQL(INIT_DATA_RESTO);

    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simplest implementation is to drop all old tables and recreate them
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENUCAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENUITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENUORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTO);
        //db.execSQL("DROP TRIGGER IF EXISTS fk_menuitem_id");
        onCreate(db);
    }
   /*
    public List<Product> listProducts(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int quantity = Integer.parseInt(cursor.getString(2));
                byte[] image = cursor.getBlob(3);
                storeProducts.add(new Product(id, name, quantity, image));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_IMAGE, product.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_IMAGE, product.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(product.getId())});
    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productName = cursor.getString(1);
            int productQuantity = Integer.parseInt(cursor.getString(2));
            byte[] productImage = cursor.getBlob(3);
            mProduct = new Product(id, productName, productQuantity, productImage);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }*/

    public void updateProduct(MenuCategoryObject product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("productid", product.getMenu_id());
        values.put("menu_jenis", product.getMenu_jenis());
        values.put("menu_name", product.getMenu_name());
        //values.put("quantity", product.getQuantity());
        //SQLiteDatabase db = this.getWritableDatabase();

        db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(product.getMenu_id()) });

    }

    public boolean deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("menu_category", "menu_id = " + id, null) > 0;
        //db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public void addProduct(MenuCategoryObject product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put("tb_menu_category_id", product.getKode());
        values.put("menu_id", product.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
        //values.put("menu_id", product.getMenu_id()+String.valueOf(product.getKode()));

        //values.put("kodeusaha", product.getKodeusaha());
        values.put("menu_jenis", product.getMenu_jenis());
        values.put("menu_name", product.getMenu_name());
        //values.put("quantity", product.getQuantity());
        //SQLiteDatabase db = this.getWritableDatabase();
        //db.insert(TABLE_PRODUCTS, null, values);
        db.insert("menu_category", null, values);
    }

    public List<RestaurantObject> namaResto(String restoID){
        List<RestaurantObject> restoObjects = new ArrayList<RestaurantObject>();
        String query = "select * from restaurant a where a.kode ='" + restoID+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String kode = cursor.getString(cursor.getColumnIndexOrThrow("kode"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
                String profinsi = cursor.getString(cursor.getColumnIndexOrThrow("Propinsi"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String opening_time = cursor.getString(cursor.getColumnIndexOrThrow("opening_time"));
                restoObjects.add(new RestaurantObject(id,kode,name, description,address,city,profinsi,email,phone,opening_time));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return restoObjects;
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public long addOrUpdateMenuCat(MenuCategoryObject menucat) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long menucatId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_USER_NAME, user.userName);
            //values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePictureUrl);
            values.clear();
            values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
            values.put("menu_jenis", menucat.getMenu_jenis());
            values.put("menu_name", menucat.getMenu_name());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            //int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            //int rows = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
            int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                //String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                //        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        "tb_menu_category_id", "menu_category", "menu_name");

                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(menucat.getMenu_name())});
                try {
                    if (cursor.moveToFirst()) {
                        menucatId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                //values.clear();
                //values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
                //values.put("menu_jenis", menucat.getMenu_jenis());
                //values.put("menu_name", menucat.getMenu_name());

                menucatId = db.insertOrThrow("menu_category", null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return menucatId;
    }


    public long addMenuCat(MenuCategoryObject menucat) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long menucatId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_USER_NAME, user.userName);
            //values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePictureUrl);
            values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
            values.put("menu_jenis", menucat.getMenu_jenis());
            values.put("menu_name", menucat.getMenu_name());


            /*String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                    "tb_menu_category_id", "menu_category", "menu_name");
            Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(menucat.getMenu_name())});
            try {
                if (cursor.moveToFirst()) {
                    menucatId = cursor.getInt(0);
                    db.setTransactionSuccessful();
                }else{
                    menucatId = db.insertOrThrow("menu_category", null, values);
                    db.setTransactionSuccessful();

                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }*/

            menucatId = db.insertOrThrow("menu_category", null, values);
            db.setTransactionSuccessful();

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            //int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            //int rows = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
            //int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            // Check if update succeeded
            //if (rows == 1) {
                // Get the primary key of the user we just updated
                //String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                //        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
            //} else {
                // user with this userName did not already exist, so insert new user
                //values.clear();
                //values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
                //values.put("menu_jenis", menucat.getMenu_jenis());
                //values.put("menu_name", menucat.getMenu_name());

             //   menucatId = db.insertOrThrow("menu_category", null, values);
             //   db.setTransactionSuccessful();
            //}
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return menucatId;
    }

    public long UpdateMenuCat(MenuCategoryObject menucat) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long menucatId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_USER_NAME, user.userName);
            //values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePictureUrl);

            values.put("menu_id", menucat.getMenu_id()+String.valueOf(menucat.getId()));
            values.put("menu_jenis", menucat.getMenu_jenis());
            values.put("menu_name", menucat.getMenu_name());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            //int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            //int rows = db.update(TABLE_MENUCAT, values, MENUCAT_MENUNAME + "= ?", new String[]{menucat.getMenu_name()});

            //int rows = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });

            String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s <>" + menucat.getId()+" AND UPPER(%s) = ?",
                    "tb_menu_category_id", "menu_category", "tb_menu_category_id", "menu_name");

            Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(menucat.getMenu_name().toUpperCase())});
            try {
                if (cursor.moveToFirst()) {
                    //menucatId = cursor.getInt(0);
                    //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });
                    //db.setTransactionSuccessful();
                    Toast.makeText(context , "Kategori Menu sudah ada!", Toast.LENGTH_LONG).show();
                }else{
                    //menucatId = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
                    menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });

                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }

            // Check if update succeeded
            //if (rows == 1) {
                // Get the primary key of the user we just updated
                //String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                //        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
            //} else {
                // user with this userName did not already exist, so insert new user
                //values.clear();
                //values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
                //values.put("menu_jenis", menucat.getMenu_jenis());
                //values.put("menu_name", menucat.getMenu_name());

                //menucatId = db.insertOrThrow("menu_category", null, values);
                //db.setTransactionSuccessful();
            //}
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return menucatId;
    }



    public boolean MenuItemUpdate(String s, int i1, String s1) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_MENUITEM+" SET menu_name = "+"'"+s+"', "+  "MENU_ID =" + i1+" WHERE menu_name = "+"'"+s1+"'");
        return true;
    }

    public long QountMenuItembyMenu(String CatName) {
        SQLiteDatabase db = getWritableDatabase();

        long jmlCat = -1;

        String usersSelectQuery = String.format("SELECT COUNT(*) FROM %s WHERE UPPER(%s) = ?",
                "menu_item", "menu_name");

        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(CatName)});
        try {
            if (cursor.moveToFirst()) {
                jmlCat = cursor.getInt(0);
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });
                //db.setTransactionSuccessful();

            }else{
                //menucatId = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });

                //db.setTransactionSuccessful();
                jmlCat = 0;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return jmlCat;
    }

    public long QountMenuItembyItem(String CatName) {
        SQLiteDatabase db = getWritableDatabase();

        long jmlCat = -1;

        String usersSelectQuery = String.format("SELECT COUNT(*) FROM %s WHERE UPPER(%s) = ?",
                "menu_item", "item_name");

        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(CatName)});
        try {
            if (cursor.moveToFirst()) {
                jmlCat = cursor.getInt(0);
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });
                //db.setTransactionSuccessful();

            }else{
                //menucatId = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });

                //db.setTransactionSuccessful();
                jmlCat = 0;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return jmlCat;
    }


    public long QountMenuCat(String CatName) {
        SQLiteDatabase db = getWritableDatabase();

        long jmlCat = -1;

        String usersSelectQuery = String.format("SELECT COUNT(*) FROM %s WHERE UPPER(%s) = ?",
                "menu_category", "menu_name");

        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(CatName)});
        try {
            if (cursor.moveToFirst()) {
                jmlCat = cursor.getInt(0);
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });
                //db.setTransactionSuccessful();

            }else{
                //menucatId = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
                //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });

                //db.setTransactionSuccessful();
                jmlCat = 0;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return jmlCat;
    }

    public List<MenuItemObject> listMenuItemAllbyGroup(){
        SQLiteDatabase db = getWritableDatabase();

        List<MenuItemObject> menuitemObjects = new ArrayList<MenuItemObject>();
        String query = "select a.tb_menu_item_id,a.kodeitem,a.menu_id, b.menu_jenis,a.menu_name,a.item_name,\n" +
                "a.description,a.item_picture,a.item_price,a.item_quantity,\n" +
                "a.item_options,a.item_notes,a.hot_deal\n" +
                "from menu_item a \n " +
                "INNER JOIN menu_category b ON a.menu_name = b.menu_name \n" +
                //"WHERE substr(a.kodeitem,1,4) = 'R002'" +" \n " +
                "ORDER BY b.menu_jenis,a.menu_name,a.tb_menu_item_id";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                //byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                //String item_options = cursor.getString(cursor.getColumnIndexOrThrow("item_options"));
                menuitemObjects.add(new MenuItemObject(id, kodeitem, menuid, menujenis, menu, name,description,item_picture,item_price));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return menuitemObjects;
    }

    //= '" + namaTabel+"'";
    public List<MenuItemObject> listMenuItembyGroupName(String menuNm){
        SQLiteDatabase db = getWritableDatabase();

        List<MenuItemObject> menuitemObjects = new ArrayList<MenuItemObject>();
        String query = "select a.tb_menu_item_id,a.kodeitem,a.menu_id, b.menu_jenis,a.menu_name,a.item_name,\n" +
                "a.description,a.item_picture,a.item_price,a.item_quantity,\n" +
                "a.item_options,a.item_notes,a.hot_deal\n" +
                "from menu_item a \n " +
                "INNER JOIN menu_category b ON a.menu_name = b.menu_name \n" +
                //where a.kode ='" + restoID+"'"
                "WHERE a.menu_name ='" + menuNm+"'" +" \n " +
                //"WHERE substr(a.kodeitem,1,4) = 'R002'" +" \n " +
                "ORDER BY b.menu_jenis,a.menu_name,a.tb_menu_item_id";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                //byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                //String item_options = cursor.getString(cursor.getColumnIndexOrThrow("item_options"));
                menuitemObjects.add(new MenuItemObject(id, kodeitem, menuid, menujenis, menu, name,description,item_picture,item_price));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return menuitemObjects;
    }

    public List<MenuItemObject> listMenuItembyGroupId(int idna){
        SQLiteDatabase db = getWritableDatabase();

        List<MenuItemObject> menuitemObjects = new ArrayList<MenuItemObject>();
        String query = "select a.tb_menu_item_id,a.kodeitem,a.menu_id, b.menu_jenis,a.menu_name,a.item_name,\n" +
                "a.description,a.item_picture,a.item_price,a.item_price2,a.item_price3,a.activated,a.disc,a.item_quantity,\n" +
                "a.item_options,a.item_notes,a.hot_deal\n" +
                "from menu_item a \n " +
                "INNER JOIN menu_category b ON a.menu_name = b.menu_name \n" +
                //where a.kode ='" + restoID+"'"
                "WHERE a.tb_menu_item_id =" + idna +" \n " +
                //"WHERE substr(a.kodeitem,1,4) = 'R002'" +" \n " +
                "ORDER BY b.menu_jenis,a.menu_name,a.tb_menu_item_id";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                //byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                float item_price2 = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price2"));
                float item_price3 = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price3"));
                String item_activate = cursor.getString(cursor.getColumnIndexOrThrow("activated"));
                String item_disc = cursor.getString(cursor.getColumnIndexOrThrow("disc"));
                //String item_options = cursor.getString(cursor.getColumnIndexOrThrow("item_options"));
                menuitemObjects.add(new MenuItemObject(id, kodeitem, menuid, menujenis, menu, name,description,item_picture,item_price,item_price2,item_price3,item_activate,item_disc));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return menuitemObjects;
    }

    public List<MenuCategoryObject> listMenuCategory(){
        SQLiteDatabase db = getWritableDatabase();

        List<MenuCategoryObject> menucategoryObjects = new ArrayList<MenuCategoryObject>();
        String query = "SELECT * FROM menu_category ORDER BY menu_name";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                int kode = cursor.getInt(1);
                int menuid = cursor.getInt(2);
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                //String kodeusaha = cursor.getString(cursor.getColumnIndexOrThrow("kodeusaha"));
                String jenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                menucategoryObjects.add(new MenuCategoryObject(id, kode, menuid, jenis, name,""));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return menucategoryObjects;
    }

    public List<HistoryObject> listHistoryObject(){
        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select * from menu_order";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                //float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                float nettot = cursor.getFloat(cursor.getColumnIndexOrThrow("nettot"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                int pajak = cursor.getInt(cursor.getColumnIndexOrThrow("pajak"));
                int disc = cursor.getInt(cursor.getColumnIndexOrThrow("disc"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,nettot,status,payment,tabel,pajak,disc,1,0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }


    public List<HistoryObject> listHistoryObjectGroupByTabel(){
        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select tb_order_id,substr(order_id,1,16) order_id, order_date, sum(order_price) order_price, order_quantity, sum(nettot) nettot, tabel, status, payment_method, pajak, disc, count(tabel) jml, totbayar  \n" +
                "from menu_order \n" +
                "group by substr(order_id,1,16)";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                //String datenya = Helper.dateFormatting(cursor.getString(cursor.getColumnIndexOrThrow("order_date")));

                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                //float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                float nettot = cursor.getFloat(cursor.getColumnIndexOrThrow("nettot"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                int pajak = cursor.getInt(cursor.getColumnIndexOrThrow("pajak"));
                int disc = cursor.getInt(cursor.getColumnIndexOrThrow("disc"));
                int jml = cursor.getInt(cursor.getColumnIndexOrThrow("jml"));
                //historyObjects.add(new HistoryObject(id, datenya, quantity,nettot,status,payment,tabel,pajak,disc,jml));
                float totbayar = cursor.getFloat(cursor.getColumnIndexOrThrow("totbayar"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,nettot,status,payment,tabel,pajak,disc,jml,totbayar));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }

    public boolean deleteOrder(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("menu_order", "order_id = '" + id+"'", null) > 0;
    }

    public boolean deleteOrderItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("order_menu_item", "order_id = '" + id+"'", null) > 0;
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public long addOrderItem(Map<String,String> menuitem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("order_id", menuitem.get("ORDER_ID"));
        values.put("user_id", menuitem.get("USER_ID"));
        values.put("order_quantity", menuitem.get("QUANTITY"));
        values.put("order_price", menuitem.get("PRICE"));
        //values.put("order_date", getDateTime());
        values.put("order_date", menuitem.get("DATE"));

        if (menuitem.get("TABLE").equals("0")) {
            values.put("status", "Paid");
        } else {
            //values.put("status", "Table "+menuitem.get("TABLE")+": Ordered");
            values.put("status", "Ordered");
        }


        values.put("payment_method",menuitem.get("PAYMENT"));
        values.put("tabel", menuitem.get("TABLE"));
        values.put("pajak", menuitem.get("PAJAK"));
        values.put("disc", menuitem.get("DISC"));
        values.put("nettot", menuitem.get("NETTOT"));
        values.put("nettot", menuitem.get("NETTOT"));
        values.put("totbayar", 0);

        //mendapatkan nilai id increment
        long order_id = db.insert("menu_order", null,values);

        JSONArray storesArray = null;
        try {
            storesArray = new JSONArray(menuitem.get("ORDER_LIST").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < storesArray.length(); i++){

            JSONObject store = null;
            try {
                store = storesArray.getJSONObject(i);
/*
                getDbConnection().execSQL("INSERT INTO order_menu_item ( order_id, menu_item_id, quantity, price, subtotal, options, notes) " +
                                "VALUES ( ? ,?, ?, ?, ?, ?, ? )",
                        new Object [] {
                                order_id,
                                store.getString("id"),
                                store.getString("quantity"),
                                store.getString("price"),
                                store.getString("subtotal"),
                                store.getString("options"),
                                store.getString("note")
                        }
                );*/

                ContentValues values2 = new ContentValues();
                //values2.put("order_id", order_id);
                values2.put("order_id", menuitem.get("ORDER_ID"));
                //values2.put("menu_item_id", store.getString("id"));
                values2.put("kodeitem", store.getString("kodeitem"));
                //values2.put("kodeitem", "");
                values2.put("quantity", store.getString("quantity"));
                values2.put("price", Float.parseFloat(store.getString("price")));
                values2.put("subtotal", (Float.parseFloat(store.getString("quantity")))*(Float.parseFloat(store.getString("price"))));
                values2.put("options","xxxxx");
                values2.put("notes", store.getString("note"));

                db.insert("order_menu_item", null, values2);

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
        return 0;
    }

    public List<MenuCategoryObject> listProducts(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<MenuCategoryObject> storeProducts = new ArrayList<MenuCategoryObject>();
        String query = "select * from menu_category";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                int kode = Integer.parseInt(cursor.getString(1));
                int menuid = cursor.getInt(1);
                String jenis = cursor.getString(2);
                String name = cursor.getString(3);
                //int quantity = Integer.parseInt(cursor.getString(2));
                storeProducts.add(new MenuCategoryObject(id, 0, menuid, jenis, name,""));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }


    public int getIdIncrement(String namaTabel){
        SQLiteDatabase db = this.getWritableDatabase();

        int id = 0;
        String query = "SELECT seq from SQLITE_SEQUENCE where name = '" + namaTabel+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
        id = id+1;
        return id;
    }

    public long GetId(String namaTabel){
        SQLiteDatabase db = getWritableDatabase();
        long id = -1;
        String query = "SELECT seq from SQLITE_SEQUENCE where name = '" + namaTabel+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
        id = id+1;
        return id;
    }


    public List<MenuItemObject> listMenuItemAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<MenuItemObject> menuitemObjects = new ArrayList<MenuItemObject>();
        String query = "select a.tb_menu_item_id,a.kodeitem,a.menu_id,b.menu_jenis,a.menu_name,a.item_name,\n" +
                "a.description,a.item_picture,a.item_price,a.item_quantity,\n" +
                "a.item_options,a.item_notes,a.hot_deal\n" +
                "from menu_item a \n" +
                "INNER JOIN menu_category b ON a.menu_id = b.menu_id \n" +
                "ORDER BY a.tb_menu_item_id";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                //String item_options = cursor.getString(cursor.getColumnIndexOrThrow("item_options"));
                menuitemObjects.add(new MenuItemObject(id, kodeitem, menuid, menujenis, menu, name,description,item_picture,item_price));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return menuitemObjects;
    }

    public void addMenuItem(MenuItemObject menuitem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("kodeitem", menuitem.getKodeitem());
        values.put("kodeitem", menuitem.getMenu_id()+String.valueOf(getIdIncrement("menu_item")));
        values.put("menu_id", menuitem.getMenu_id());
        values.put("menu_jenis", menuitem.getMenu_jenis());
        values.put("menu_name", menuitem.getMenu_name());
        values.put("item_name", menuitem.getItem_name());
        values.put("description", menuitem.getDescription());
        //values.put("item_picture", menuitem.getImage());
        values.put("item_picture", menuitem.getMenu_id()+String.valueOf(getIdIncrement("menu_item"))+".png");
        values.put("item_price", menuitem.getItem_price());
        //values.put("quantity", product.getQuantity());
        //SQLiteDatabase db = this.getWritableDatabase();
        //db.insert(TABLE_PRODUCTS, null, values);
        db.insert("menu_item", null, values);
    }

    public void updateMenuItem44(MenuItemObject menuitem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("productid", product.getMenu_id());
        values.put("menu_id", menuitem.getMenu_id());
        values.put("kodeitem", menuitem.getKodeitem());
        values.put("menu_jenis", menuitem.getMenu_jenis());
        values.put("menu_name", menuitem.getMenu_name());
        values.put("item_name", menuitem.getItem_name());
        values.put("description", menuitem.getDescription());
        values.put("item_picture", menuitem.getImage());
        values.put("item_price", menuitem.getItem_price());
        //values.put("quantity", product.getQuantity());
        //SQLiteDatabase db = this.getWritableDatabase();

        db.update("menu_item", values, "tb_menu_item_id" + " = ?"  , new String[] { String.valueOf(menuitem.getMenu_item_id()) });

    }


    public long updateMenuItem(MenuItemObject menuitem){
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long menucatId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("menu_id", menuitem.getMenu_id());
            values.put("kodeitem", menuitem.getKodeitem());
            values.put("menu_jenis", menuitem.getMenu_jenis());
            values.put("menu_name", menuitem.getMenu_name());
            values.put("item_name", menuitem.getItem_name());
            values.put("description", menuitem.getDescription());
            values.put("item_picture", menuitem.getImage());
            values.put("item_price", menuitem.getItem_price());
            values.put("item_price2", menuitem.getItem_price2());
            values.put("item_price3", menuitem.getItem_price3());
            values.put("activated", menuitem.getItem_activate());
            values.put("disc", menuitem.getItem_disc());

            String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s <>" + menuitem.getMenu_item_id()+" AND UPPER(%s) = ?",
                    "tb_menu_item_id", "menu_item", "tb_menu_item_id", "item_name");

            Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(menuitem.getItem_name().toUpperCase())});
            try {
                if (cursor.moveToFirst()) {
                    //menucatId = cursor.getInt(0);
                    //menucatId = db.update("menu_category", values, "tb_menu_category_id" + " = ?"  , new String[] { String.valueOf(menucat.getId()) });
                    //db.setTransactionSuccessful();
                    Toast.makeText(context , "Item Menu sudah ada!", Toast.LENGTH_LONG).show();
                }else{
                    //menucatId = db.update("menu_category", values, "menu_id" + " = ?"  , new String[] { String.valueOf(menucat.getMenu_id()) });
                    menucatId = db.update("menu_item", values, "tb_menu_item_id" + " = ?"  , new String[] { String.valueOf(menuitem.getMenu_item_id()) });

                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }

            // Check if update succeeded
            //if (rows == 1) {
            // Get the primary key of the user we just updated
            //String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
            //        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
            //} else {
            // user with this userName did not already exist, so insert new user
            //values.clear();
            //values.put("menu_id", menucat.getMenu_id()+String.valueOf(getIdIncrement("menu_category")));
            //values.put("menu_jenis", menucat.getMenu_jenis());
            //values.put("menu_name", menucat.getMenu_name());

            //menucatId = db.insertOrThrow("menu_category", null, values);
            //db.setTransactionSuccessful();
            //}
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return menucatId;
    }

    public boolean deleteMenuItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("menu_item", "tb_menu_item_id = " + id, null) > 0;
    }

    public List<LoginObject> signin(String nama, String passw){
        SQLiteDatabase db = this.getWritableDatabase();

        List<LoginObject> loginObjects = new ArrayList<LoginObject>();
        String query = "select * from user a where a.username = '" + nama+"' and a.password = '" + passw+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                //String phone = "";
                String loggedIn = "1";
                loginObjects.add(new LoginObject(id, username,password,role,email,address,phone,loggedIn));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return loginObjects;
    }

    public List<LoginObject> listUser(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<LoginObject> loginObjects = new ArrayList<LoginObject>();
        String query = "select * from user a where a.username <>'admin'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                //String phone = "";
                String loggedIn = "1";
                loginObjects.add(new LoginObject(id, username,password,role,email,address,phone,loggedIn));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return loginObjects;
    }

    public void addUser(LoginObject user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("kodeitem", menuitem.getKodeitem());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("address", user.getAddress());
        values.put("phone_number", user.getPhone());
        values.put("role", "1");
        db.insert("user", null, values);
    }

    public boolean deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("user", "tb_user_id = " + id, null) > 0;
    }

    public List<CartObject> listOrderHist1(String idNya){
        SQLiteDatabase db = this.getWritableDatabase();

        List<CartObject> cartObjects = new ArrayList<CartObject>();
        String query = "SELECT a.order_id id,a.kodeitem,a.quantity,a.price,a.notes,'' extra, b.item_name \n" +
                "FROM order_menu_item a\n" +
                //"INNER JOIN menu_item b ON a.kodeitem = b.kodeitem where a.order_id = '" + idNya+"'" +
                "INNER JOIN menu_item b ON a.kodeitem = b.kodeitem where a.order_id like '" + idNya+"%'" +
                "ORDER BY a.order_id";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                //int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                //String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                //String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                //String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                //String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                //byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                //float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String extra = cursor.getString(cursor.getColumnIndexOrThrow("extra"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                String item_name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                cartObjects.add(new CartObject(id, kodeitem, item_name,quantity,price,extra,notes));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cartObjects;
    }

    public List<CartObject> listOrderHist2(String idNya){
        SQLiteDatabase db = this.getWritableDatabase();

        List<CartObject> cartObjects = new ArrayList<CartObject>();
        String query = "SELECT a.order_id id,a.kodeitem,a.quantity,a.price,a.notes,'' extra, b.item_name \n" +
                "FROM order_menu_item a\n" +
                "INNER JOIN menu_item b ON a.kodeitem = b.kodeitem where a.order_id = '" + idNya+"'" +
                //"INNER JOIN menu_item b ON a.kodeitem = b.kodeitem where a.order_id like '" + idNya+"%'" +
                "ORDER BY a.order_id";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                //int menu_id, String item_name, String description, String item_picture, float item_price, String item_options
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                //int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
                //String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
                //String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                //String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                //String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
                //byte[] imagemenus = cursor.getBlob(6);
                //byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
                //float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));
                String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String extra = cursor.getString(cursor.getColumnIndexOrThrow("extra"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                String item_name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                cartObjects.add(new CartObject(id, kodeitem, item_name,quantity,price,extra,notes));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cartObjects;
    }

    //public void updateResto(RestaurantObject resto){
    public long updateResto(RestaurantObject resto){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("productid", product.getMenu_id());
        values.put("name", resto.getName());
        values.put("description", resto.getDescription());
        values.put("address", resto.getAddress());
        values.put("city", resto.getCity());
        values.put("Propinsi", resto.getProfinsi());
        values.put("email", resto.getEmail());
        values.put("phone", resto.getPhone());
        values.put("opening_time", resto.getOpening_time());

        //SQLiteDatabase db = this.getWritableDatabase();

        db.update("restaurant", values, "tb_restaurant_id" + " = ?"  , new String[] { String.valueOf(resto.getId()) });

        return 0;
    }

    //public void updateUser(LoginObject user){
    public long updateUser(LoginObject user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("productid", product.getMenu_id());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("address", user.getAddress());
        values.put("phone_number", user.getPhone());

        //SQLiteDatabase db = this.getWritableDatabase();

        db.update("user", values, "tb_user_id" + " = ?"  , new String[] { String.valueOf(user.getId()) });

        return 0;
    }

    public List<HistoryObject> listHistorybyAvailTableId(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select * from menu_order a where a.tabel >0 and a.status <>'Paid'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,order_price,status,payment,tabel));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }

    public String CheckHistorybyTableOrder(int tableID){
        SQLiteDatabase db = this.getWritableDatabase();
        String orderId = "0";

        /*db.beginTransaction();
        String usersSelectQuery = String.format("SELECT DISTINCT SUBSTR(%s,1,16)  FROM %s WHERE status <>'Paid' AND %s = ?",
                "order_id", "menu_order", "tabel");
        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(tableID)});
        try {
            if (cursor.moveToFirst()) {
                orderId = cursor.getString(0)+String.valueOf(getIdIncrement("menu_order"));
                db.setTransactionSuccessful();
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }*/

        String query = "select substr(order_id,1,16) from menu_order a where a.status <>'Paid' and a.tabel = " + tableID;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //orderId = cursor.getString(0);
                orderId = cursor.getString(0)+String.valueOf(getIdIncrement("menu_order"));
            }while (cursor.moveToNext());
        }
        cursor.close();
            //

        return orderId;
    }

    public List<HistoryObject> listHistorybyForPaid(int tableID){
        SQLiteDatabase db = this.getWritableDatabase();

        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select * from menu_order a where a.status <>'Paid' and a.tabel = " + tableID;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,order_price,status,payment,tabel));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }

    public List<HistoryObject> listHistorybyIDForPaid(String IdNa){
        SQLiteDatabase db = this.getWritableDatabase();

        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select * from menu_order a where a.status ='Paid' and a.order_id  = '" + IdNa+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,order_price,status,payment,tabel));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }

    /*public List<HistoryObject> UpdateHistoryForPaidbyTable(int tableID){
        SQLiteDatabase db = this.getWritableDatabase();

        List<HistoryObject> historyObjects = new ArrayList<HistoryObject>();
        String query = "select * from menu_order a where a.status <>'Paid' and a.tabel = " + tableID;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //String id = cursor.getString(0);
                String id = cursor.getString(1);
                //String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("path"));
                //float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
                float order_price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                //String path = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));
                int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
                historyObjects.add(new HistoryObject(id, datenya, quantity,order_price,status,payment,tabel));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return historyObjects;
    }*/

    public void UpdateHistoryForPaidbyId(HistoryObject product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", "Paid");
        values.put("totbayar", product.getTotbayar());
        db.update("menu_order", values, "order_id" + " = ?"  , new String[] { String.valueOf(product.getOrder_id()) });

    }

    public void UpdateHistoryForPaidbyTable(HistoryObject product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", "Paid");
        values.put("totbayar", product.getTotbayar());

        //String xx = String.valueOf(product.getOrder_id()).substring(0,16);
        db.update("menu_order", values, "order_id" + " LIKE ? "  , new String[] { String.valueOf(product.getOrder_id()) });

    }


    ///backup restore

    /**
     * open database
     */
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close database
     */
    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /************************* method work with database *******************/

    /**
     * get all row of table with sql command then return cursor
     * cursor move to frist to redy for get data
     */
    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }

    /**
     * insert contentvaluse to table
     *
     * @param values value of data want insert
     * @return index row insert
     */
    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    /**
     * update values to table
     *
     * @return index row update
     */
    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    /**
     * delete id row of table
     */
    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }




    /**
     * delete id row of table
     */
    public boolean delFav(String where) {
        return delete(TABLE_FAV, where);
    }
    public boolean delUser(String where) {
        return delete(TABLE_USER, where);
    }
    public boolean delRestaurant(String where) {
        return delete(TABLE_RESTO, where);
    }
    public boolean delOrderItem(String where) {
        return delete(TABLE_ORDERITEM, where);
    }
    public boolean delMenuOrder(String where) {
        return delete(TABLE_MENUORDER, where);
    }
    public boolean delMenuItem(String where) {
        return delete(TABLE_MENUITEM, where);
    }
    public boolean delMenuCat(String where) {
        return delete(TABLE_MENUCAT, where);
    }
    public boolean delProduct(String where) {
        return delete(TABLE_PRODUCTS, where);
    }

    //public long insertFav(FavoriteObject note) {
    //    return insert(TABLE_NOTE, noteToValues(note));
    //}
    public long insertUser(LoginObject user) {
        return insert(TABLE_USER, userToValues(user));
    }

    public long insertResto(RestaurantObject resto) {
        return insert(TABLE_RESTO, restoToValues(resto));
    }
    public long insertOrderItem(BackupOrderItemObject orderitem) {
        return insert(TABLE_ORDERITEM, orderitemToValues(orderitem));
    }
    public long insertMenuOrder(BackupOrderObject menuorder) {
        return insert(TABLE_MENUORDER, menuorderToValues(menuorder));
    }
    public long insertMenuItem(MenuItemObject menuitem) {
        return insert(TABLE_MENUITEM, menuitemToValues(menuitem));
    }
    public long insertMenuCat(MenuCategoryObject menucat) {
        return insert(TABLE_MENUCAT, menucatToValues(menucat));
    }
    /*public long insertProduct(Note note) {
        return insert(TABLE_NOTE, noteToValues(note));
    }*/

    private ContentValues userToValues(LoginObject user) {
        ContentValues values = new ContentValues();

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("address", user.getAddress());
        values.put("phone_number", user.getPhone());
        values.put("role", "1");

        return values;
    }

    private ContentValues menuorderToValues(BackupOrderObject orderitem) {
        ContentValues values = new ContentValues();

        values.put("order_id", orderitem.getOrder_id());
        values.put("user_id", orderitem.getUser_id());
        values.put("order_quantity", orderitem.getOrder_quantity());
        values.put("order_price", orderitem.getOrder_price());
        values.put("order_date", orderitem.getOrder_date());
        values.put("status", orderitem.getStatus());
        values.put("payment_method", orderitem.getPayment_method());
        values.put("tabel", orderitem.getTabel());
        values.put("pajak", orderitem.getPajak());
        values.put("disc", orderitem.getDisc());
        values.put("nettot", orderitem.getNettot());
        values.put("totbayar", orderitem.getTotbayar());

        return values;
    }

    private ContentValues orderitemToValues(BackupOrderItemObject orderitem) {

        ContentValues values = new ContentValues();
        //values.put("id", orderitem.getId());
        values.put("order_id", orderitem.getOrder_id());
        values.put("kodeitem", orderitem.getKodeitem());
        values.put("quantity", orderitem.getQuantity());
        values.put("price", orderitem.getPrice());
        values.put("options",orderitem.getOptions());
        values.put("notes", orderitem.getNotes());

        return values;
    }

    private ContentValues restoToValues(RestaurantObject resto) {
        ContentValues values = new ContentValues();
        //values.put("id", resto.getId());
        values.put("kode", resto.getKode());
        values.put("name", resto.getName());
        values.put("description", resto.getDescription());
        values.put("address", resto.getAddress());
        values.put("city", resto.getCity());
        values.put("Propinsi", resto.getProfinsi());
        values.put("email", resto.getEmail());
        values.put("phone", resto.getPhone());
        values.put("opening_time", resto.getOpening_time());

        return values;
    }

    private ContentValues menucatToValues(MenuCategoryObject menucat) {
        ContentValues values = new ContentValues();
        //values.put("id", resto.getId());
        //values.put("kode", menucat.getKode());
        values.put("menu_id", menucat.getMenu_id());
        values.put("menu_name", menucat.getMenu_name());
        values.put("menu_jenis", menucat.getMenu_jenis());


        return values;
    }


    private ContentValues menuitemToValues(MenuItemObject menuitem) {
        ContentValues values = new ContentValues();
        values.put("menu_id", menuitem.getMenu_id());
        values.put("kodeitem", menuitem.getKodeitem());
        values.put("menu_jenis", menuitem.getMenu_jenis());
        values.put("menu_name", menuitem.getMenu_name());
        values.put("item_name", menuitem.getItem_name());
        values.put("description", menuitem.getDescription());
        values.put("item_picture", menuitem.getImage());
        values.put("item_price", menuitem.getItem_price());

        return values;
    }


    /*public FavoriteObject cursorToFav(Cursor cursor) {
        FavoriteObject fav = new FavoriteObject();
        fav.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID_NOTE)))
                .setName(cursor.getString(cursor.getColumnIndex(KEY_TITLE_NOTE)))
                .setImagePath(cursor.getString(cursor.getColumnIndex(KEY_CONTENT_NOTE)))
                .setPrice(cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFIED_NOTE)));
        return fav;
    }*/

    public LoginObject cursorToUser(Cursor cursor) {

        int id = cursor.getInt(0);
        String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
        //String phone = "";
        String loggedIn = "1";
        LoginObject user = new LoginObject(id, username,password,role,email,address,phone,loggedIn);

        user.setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                .setEmail(email)
                .setAddress(address)
                .setPhone(phone)
                .setLoggedIn(loggedIn);
        return user;
    }

    public RestaurantObject cursorToResto(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(RESTO_ID));
        String kode = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_KODE));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_DESC));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_EMAIL));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_ADDRESS));
        String city = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_CITY));
        String profinsi = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_PROP));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_PHONE));
        String opening_time = cursor.getString(cursor.getColumnIndexOrThrow(RESTO_TIME));

        RestaurantObject resto = new RestaurantObject(id,kode,name, description,address,city,profinsi,email,phone,opening_time);
        resto.setId(id)
                .setKode(kode)
                .setName(name)
                .setDescription(description)
                .setAddress(address)
                .setCity(city)
                .setProfinsi(profinsi)
                .setEmail(email)
                .setPhone(phone)
                .setOpening_time(opening_time);
        return resto;
    }


    public MenuItemObject cursorToMenuItem(Cursor cursor) {

        int id = cursor.getInt(0);
        String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
        int menuid = cursor.getInt(cursor.getColumnIndexOrThrow("menu_id"));
        String menujenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
        String menu = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String item_picture = cursor.getString(cursor.getColumnIndexOrThrow("item_picture"));
        //byte[] imagemenus = cursor.getBlob(6);
        byte[] imagemenus = cursor.getBlob(cursor.getColumnIndexOrThrow("item_picture"));
        float item_price = cursor.getFloat(cursor.getColumnIndexOrThrow("item_price"));

        MenuItemObject menuitem = new MenuItemObject(id, kodeitem, menuid, menujenis, menu, name,description,item_picture,item_price);
        menuitem.setMenu_item_id(id)
                .setKodeitem(kodeitem)
                .setMenu_id(menuid)
                .setMenu_name(menu)
                .setMenu_jenis(menujenis)
                .setItem_name(name)
                .setDescription("")
                .setItem_price(item_price)
                .setItem_options("")
                .setImage(item_picture);
        return menuitem;
    }

    public MenuCategoryObject cursorToMenuCat(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex("tb_menu_category_id"));
        int kode = 0;
        int menuid = cursor.getInt(cursor.getColumnIndex("menu_id"));
        String jenis = cursor.getString(cursor.getColumnIndexOrThrow("menu_jenis"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("menu_name"));
        String image = cursor.getString(cursor.getColumnIndexOrThrow("menu_image"));

        MenuCategoryObject menucat = new MenuCategoryObject(id, kode, menuid, jenis, name,image);
        menucat.setId(id)
                .setKode(kode)
                .setMenu_id(menuid)
                .setMenu_name(name)
                .setMenu_jenis(jenis)
                .setMenu_image(image);
        return menucat;
    }


    public BackupOrderItemObject cursorToOrderItem(Cursor cursor) {

        int id = cursor.getInt(0);
        //String orderid = cursor.getString(1);
        String order_id = cursor.getString(cursor.getColumnIndexOrThrow("order_id"));
        String kodeitem = cursor.getString(cursor.getColumnIndexOrThrow("kodeitem"));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
        float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
        String options = cursor.getString(cursor.getColumnIndexOrThrow("options"));
        String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));

        BackupOrderItemObject orderitem = new BackupOrderItemObject();
        orderitem.setId(id)
                .setOrder_id(order_id)
                .setKodeitem(kodeitem)
                .setQuantity(quantity)
                .setPrice(price)
                .setOptions(options)
                .setNotes(notes);
        return orderitem;
    }

    public BackupOrderObject cursorToMenuOrder(Cursor cursor) {

        //String id = cursor.getString(0);
        String id = cursor.getString(cursor.getColumnIndexOrThrow("order_id"));
        String user = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
        String datenya = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("order_quantity"));
        float price = cursor.getFloat(cursor.getColumnIndexOrThrow("order_price"));
        String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
        String payment = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
        int tabel = cursor.getInt(cursor.getColumnIndexOrThrow("tabel"));
        int pajak = cursor.getInt(cursor.getColumnIndexOrThrow("pajak"));
        int disc = cursor.getInt(cursor.getColumnIndexOrThrow("disc"));
        float nettot = cursor.getFloat(cursor.getColumnIndexOrThrow("nettot"));
        float totbayar = cursor.getFloat(cursor.getColumnIndexOrThrow("totbayar"));

        BackupOrderObject orderitem = new BackupOrderObject();
        orderitem.setOrder_id(id)
                .setUser_id(user)
                .setOrder_quantity(quantity)
                .setOrder_price(price)
                .setOrder_date(datenya)
                .setStatus(status)
                .setPayment_method(payment)
                .setTabel(tabel)
                .setPajak(pajak)
                .setDisc(disc)
                .setNettot(nettot)
                .setTotbayar(totbayar);

        return orderitem;
    }
}
