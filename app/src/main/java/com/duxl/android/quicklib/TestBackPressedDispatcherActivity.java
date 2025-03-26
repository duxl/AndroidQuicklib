package com.duxl.android.quicklib;

import android.view.View;
import android.widget.RadioGroup;

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
    public void handleOnBackPressed() {
        ToastUtils.show("Activity拦截了返回");
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
            binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> mOnBackPressedCallback.setEnabled(checkedId == R.id.radio_back_enabled));
        }

        @Override
        protected boolean handleOnBackPressed() {
            ToastUtils.show("Fragment拦截了返回");
            return false;
        }
    }
}
