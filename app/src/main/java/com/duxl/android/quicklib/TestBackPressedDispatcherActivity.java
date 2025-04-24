package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.FragmentTestBackPressedDispatcherBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.fragment.BaseFragment;
import com.duxl.baselib.utils.ToastUtils;

/**
 * 测试返回分发器
 */
public class TestBackPressedDispatcherActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_back_pressed_dispatcher;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试返回分发器");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, new TestBackPressedDispatcherFragment())
                .commitNow();
    }

    @Override
    protected void onClickActionBack(View v) {
        //super.onClickActionBack(v);
        // 点击左上角的返回按钮，直接关闭页面
        finish();
    }

    @Override
    public void handleOnBackPressed() {
        ToastUtils.show("Activity拦截了返回");
        //finish();
    }

    public static class TestBackPressedDispatcherFragment extends BaseFragment {

        private FragmentTestBackPressedDispatcherBinding binding;

        @Override
        protected int getLayoutResId() {
            return R.layout.fragment_test_back_pressed_dispatcher;
        }

        @Override
        protected void initView(View v) {
            super.initView(v);
            binding = FragmentTestBackPressedDispatcherBinding.bind(v);
        }

        @Override
        protected boolean handleOnBackPressed() {
            if (binding.radioBackEnabled.isChecked()) {
                ToastUtils.show("Fragment拦截了返回");
                return true;
            }
            return false;
        }
    }
}
