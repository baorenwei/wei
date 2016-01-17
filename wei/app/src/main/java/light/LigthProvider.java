package light;


import android.database.sqlite.SQLiteOpenHelper;

import com.example.base.base.BaseContentProvider;


/**
 * Created by Administrator on 2016/1/4.
 */
public class LigthProvider extends BaseContentProvider {

    @Override
    protected SQLiteOpenHelper onCreateSQLiteOpenHelper() {
        return new LigthSQLiteOpenHelper(getContext());
    }
}
