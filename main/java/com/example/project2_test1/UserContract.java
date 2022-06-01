package com.example.project2_test1;

import android.provider.BaseColumns;

public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 4;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME="Users";
        public static final String KEY_TITLE = "Title";
        public static final String KEY_STARTH = "StartH";
        public static final String KEY_STARTM = "StartM";
        public static final String KEY_ENDH = "EndH";
        public static final String KEY_ENDM = "EndM";
        public static final String KEY_MEMO = "Memo";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_MONTH = "Month";
        public static final String KEY_DAY = "Day";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_STARTH + TEXT_TYPE + COMMA_SEP +
                KEY_STARTM + TEXT_TYPE + COMMA_SEP +
                KEY_ENDH + TEXT_TYPE + COMMA_SEP +
                KEY_ENDM + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE + COMMA_SEP +
                KEY_YEAR + TEXT_TYPE + COMMA_SEP +
                KEY_MONTH + TEXT_TYPE + COMMA_SEP +
                KEY_DAY + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
