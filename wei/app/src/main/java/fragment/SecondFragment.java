package fragment;


import android.net.Uri;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragment;
import com.example.base.base.BaseFragmentActivity;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SecondFragment extends BaseFragmentActivity {


    @Override
    protected int initLayout() {
        return R.layout.fragment_second_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        enterFragment();
    }
    /**
     * 加载 会话列表 ConversationListFragment
     */
    private void enterFragment() {

        ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
    }
}
