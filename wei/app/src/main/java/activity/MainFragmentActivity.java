package activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MainFragmentActivity extends BaseFragmentActivity {

    private static int mFragmentIndex = -1;
    private Fragment[] fragments = null;
    private int mFragmentLength;

    public List<Integer> mListFragment = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.fragment_main_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    private void showFragment(int index) {
        if (index != mFragmentIndex) {

            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            if (mFragmentIndex != -1) {
                ft.detach(fragments[mFragmentIndex]);
            }
            if (fragments[index] == null) {
                createFragment(index);
                ft.add(R.id.mainFragmentFrameLayout, fragments[index]);
            } else {
                ft.attach(fragments[index]);
            }
            ft.commit();
            mFragmentIndex = index;
        }
    }

    private void createFragment(int index) {

        mListFragment.add(index);
        if (fragments[index] != null)
            return;
        switch (index){

        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mFragmentIndex = -1;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mListFragment.size() != 0){
            return;
        }else{

        }
    }

}
