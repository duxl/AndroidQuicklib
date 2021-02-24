package com.duxl.android.quicklib;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.duxl.baselib.ui.fragment.BaseFragment;
import com.duxl.baselib.utils.WindowSoftInputCompat;

/**
 * 键盘遮挡输入框问题
 * create by duxl 2021/2/24
 */
public class TestInputFragmentActivity extends TestInputActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_input_fragment;
    }

    @Override
    protected void initView(View v) {
        setTitle("内嵌Fragment");
        WindowSoftInputCompat.assistActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, new MyFragment(super.msg));
        transaction.commit();
    }

    public static class MyFragment extends BaseFragment {

        public String msg;

        public MyFragment(String msg) {
            this.msg = msg;
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.activity_test_input;
        }

        @Override
        protected void initView(View v) {
            ((TextView) v.findViewById(R.id.tv_top)).setText(msg);
        }
    }

}
