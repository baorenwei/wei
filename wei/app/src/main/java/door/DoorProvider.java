package door;


import android.database.sqlite.SQLiteOpenHelper;

import com.example.base.base.BaseContentProvider;

import light.LigthSQLiteOpenHelper;


/**
 * Created by Administrator on 2016/1/4.
 */
public class DoorProvider extends BaseContentProvider {

    @Override
    protected SQLiteOpenHelper onCreateSQLiteOpenHelper() {
        return new DoorSQLiteOpenHelper(getContext());
    }
}
