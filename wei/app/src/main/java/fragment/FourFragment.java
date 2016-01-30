package fragment;


import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

/**
 * Created by Administrator on 2016/1/8.
 */
public class FourFragment extends BaseFragmentActivity {


    @Override
    protected int initLayout() {
        return R.layout.fragment_thred_layout;
    }

    protected void initView() {

    }
    @Override
    protected void initData() {

    }

//    private static int mFragmentIndex = -1;
//
//    private RadioGroup mMainFragmentRadioGroup;
//    private RadioButton mMainFragmentMessageRadioButton;
//    private RadioButton mMainFragmentContactRadioButton;
//    private RadioButton mMainFragmentDynamicRadioButton;
//
//    private int mFragmentLength = 3;
//    private Fragment[] mFragment = null;
//
//    @Override
//    protected int initLayout() {
//        return R.layout.fragment_main_layout;
//    }
//
//
//    @Override
//    protected void initView() {
//        mMainFragmentRadioGroup = (RadioGroup) findViewById(R.id.mainFragmentRadioGroup);
//        mMainFragmentMessageRadioButton = (RadioButton) findViewById(R.id.mainFragmentMessageRadioButton);
//        mMainFragmentContactRadioButton = (RadioButton) findViewById(R.id.mainFragmentContactRadioButton);
//        mMainFragmentDynamicRadioButton = (RadioButton) findViewById(R.id.mainFragmentDynamicRadioButton);
//    }
//
//    @Override
//    protected void initData() {
//        mMainFragmentRadioGroup.setOnCheckedChangeListener(this);
//        mFragment = new Fragment[mFragmentLength];
//        showFragment(0);
//        setLeftTextView("左边");
//        setRightTextView("右边");
//        setTitleTextView("小包");
//
//    }
//
//    private void showFragment(int index) {
//        if (index != mFragmentIndex) {
//
//            FragmentTransaction ft = getSupportFragmentManager()
//                    .beginTransaction();
//            if (mFragmentIndex != -1) {
//                ft.detach(mFragment[mFragmentIndex]);
//            }
//            if (mFragment[index] == null) {
//                createFragment(index);
//                ft.add(R.id.mainFragmentFrameLayout, mFragment[index]);
//            } else {
//                ft.attach(mFragment[index]);
//            }
//            ft.commit();
//            mFragmentIndex = index;
//        }
//    }
//
//    private void createFragment(int index) {
//        if (mFragment[index] != null){
//            return;
//        }
//        switch (index) {
//            case 0:
//                mFragment[index] = new FirstFragment();
//                break;
//            case 1:
//                mFragment[index] = new SecondFragment();
//                break;
//            case 2:
//                mFragment[index] = new ThredFragment();
//                break;
//        }
//    }
//
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            mFragmentIndex = -1;
//        }
//        return super.onKeyUp(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        super.onCheckedChanged(group, checkedId);
//        if (checkedId == mMainFragmentMessageRadioButton.getId()) {
//            showFragment(0);
//        }else if(checkedId == mMainFragmentContactRadioButton.getId()){
//            showFragment(1);
//        }else if(checkedId == mMainFragmentDynamicRadioButton.getId()){
//            showFragment(2);
//            LogUtils.showLogI("点了");
//        }
//}


}
