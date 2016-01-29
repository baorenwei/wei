package RongFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.administrator.bao.R;

/**
 * Created by Administrator on 2016/1/27.
 */
public class SubConverstaionActivity extends FragmentActivity {

//    private ListView mListView;
//    private MainListAdapter mMainListAdapter;
//    AlertDialog.Builder dialog;
//    String[] selects = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        mListView = (ListView) findViewById(R.id.list);
//        initDate();
//    }
//
//    private void initDate() {
//
//        String[] titleNameArray = this.getResources().getStringArray(
//                R.array.subconversationlist_list);
//        mMainListAdapter = new MainListAdapter(this, titleNameArray);
//        mListView.setAdapter(mMainListAdapter);
//        mMainListAdapter.notifyDataSetChanged();
//
//        mListView.setOnItemClickListener(this);
//
//        dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("选择打开哪个");
//        selects = new String[]{"activity 静态集成", "activity 动态集成"};
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        switch (position) {
//            case 0:
//                //1.通过 Api 调用
//                showDialog1();
//                break;
//            case 1:
//                //2.通过 Uri 调用
//                showDialog2();
//                break;
//
//        }
//
//    }
//
//    /**
//     * 通过 Api 调用
//     */
//    private void showDialog1() {
//
//        dialog.setItems(selects, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                if (RongIM.getInstance() != null)
//                    RongIM.getInstance().startSubConversationList(StartSubConverstaionActivity.this, Conversation.ConversationType.PRIVATE);
//            }
//        });
//        dialog.show();
//    }
//    /**
//     * 通过 Uri 调用
//     */
//    private void showDialog2() {
//
//        dialog.setItems(selects, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                        .appendPath("subconversationlist")
//                        .appendQueryParameter("type", String.valueOf(Conversation.ConversationType.PRIVATE).toLowerCase())
//                        .build();
//
//                startActivity(new Intent(Intent.ACTION_VIEW, uri));
//            }
//        });
//        dialog.show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rong_subconversationlist);
    }
}
