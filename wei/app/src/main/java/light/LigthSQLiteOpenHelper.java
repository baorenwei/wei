package light;

import android.content.Context;
import com.example.base.base.BaseSQLiteOpenHelper;


/**
 * Created by Administrator on 2016/1/4.
 */
public class LigthSQLiteOpenHelper extends BaseSQLiteOpenHelper {

    public LigthSQLiteOpenHelper(Context context) {
        super(context, LightMeteDate.TABLE_NAME,LightMeteDate.VERSION);
        setSql(SQL);
    }

    public static final
    String SQL = "CREATE TABLE IF NOT EXISTS "
            + LightMeteDate.TABLE_NAME
            +"("
            + LightMeteDate._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LightMeteDate.EMAIL + " INTEGER,"
            + LightMeteDate.USERNAME + " INTEGER,"
            + LightMeteDate.DATE + " INTEGER,"
            + LightMeteDate.SEX + "  INTEGER"
            +")";
}
