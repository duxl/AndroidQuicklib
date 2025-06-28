package com.duxl.android.quicklib;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duxl.android.quicklib.databinding.ActivityTestBackPressedDispatcher2Binding;
import com.duxl.android.quicklib.databinding.FragmentTestBackPressedDispatcherBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.fragment.BaseFragment;
import com.duxl.baselib.ui.fragment.LazyFragment;
import com.duxl.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试返回分发器
 */
public class TestBackPressedDispatcher2Activity extends BaseActivity {

    private ActivityTestBackPressedDispatcher2Binding binding;
    List<TestBackPressedDispatcherFragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_back_pressed_dispatcher2;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        binding = ActivityTestBackPressedDispatcher2Binding.bind(v);
        setTitle("测试返回分发器 ViewPager");

        fragments.add(new TestBackPressedDispatcherFragment(0, Color.YELLOW));
        fragments.add(new TestBackPressedDispatcherFragment(1, Color.RED));
        fragments.add(new TestBackPressedDispatcherFragment(2, Color.GRAY));

        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {


            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
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

    public static class TestBackPressedDispatcherFragment extends LazyFragment {

        private int id;
        private int bgColor;

        public TestBackPressedDispatcherFragment(int id, int bgColor) {
            this.id = id;
            this.bgColor = bgColor;
        }

        private FragmentTestBackPressedDispatcherBinding binding;

        @Override
        protected int getLayoutResId() {
            return R.layout.fragment_test_back_pressed_dispatcher;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void initView(View v) {
            super.initView(v);
            binding = FragmentTestBackPressedDispatcherBinding.bind(v);
            binding.getRoot().setBackgroundColor(bgColor);
            binding.tvText.setText("Fragment(" + id + ")");
        }

        @Override
        protected boolean handleOnBackPressed() {
            if (binding.radioBackEnabled.isChecked()) {
                ToastUtils.show("Fragment(" + id + ")拦截了返回");
                return true;
            }
            return false;
        }

        @Override
        protected void onLazyHiddenChanged(boolean isVisible, boolean isFirstVisible) {

        }
    }
}
