package com.duxl.android.quicklib;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duxl.android.quicklib.databinding.ActivityTestBackPressedDispatcher2Binding;
import com.duxl.android.quicklib.databinding.FragmentTestBackPressedDispatcherBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.activity.SupportComponentResultActivity;
import com.duxl.baselib.ui.fragment.BaseFragment;
import com.duxl.baselib.ui.fragment.LazyFragment;
import com.duxl.baselib.ui.fragment.SupportComponentResultFragment;
import com.duxl.baselib.ui.fragment.SupportComponentResultLazyFragment;
import com.duxl.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试返回分发器
 */
public class TestBackPressedDispatcher2Activity extends SupportComponentResultActivity {

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
        binding.viewPager.setOffscreenPageLimit(3);
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

    public static class TestBackPressedDispatcherFragment extends SupportComponentResultLazyFragment {

        private int id;
        private int bgColor;

        public TestBackPressedDispatcherFragment(int id, int bgColor) {
            this.id = id;
            this.bgColor = bgColor;
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("test", "onResume: id=" + id);
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
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d("Test", "Fragment(" + id + ") onViewCreated");
        }

        @Override
        protected void lazyHiddenChanged(boolean visible) {
            super.lazyHiddenChanged(visible);
            Log.d("Test", "Fragment(" + id + ") lazyHiddenChanged visible=" + visible);
        }

        @Override
        protected boolean handleOnBackPressed() {
            Log.d("Test", "Fragment(" + id + ") handleOnBackPressed");
            if (binding.radioBackEnabled.isChecked()) {
                ToastUtils.show("Fragment(" + id + ")拦截了返回");
                return true;
            }
            return false;
        }

        @Override
        protected void onLazyHiddenChanged(boolean isVisible, boolean isFirstVisible) {

        }

        protected void initOnBackPressedDispatcher() {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), mOnBackPressedCallback = new OnBackPressedCallback(true) {
                //requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), mOnBackPressedCallback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    boolean isConsumed = TestBackPressedDispatcherFragment.this.handleOnBackPressed();
                    if (!isConsumed) { // 没有消耗事件
                        // 将返回事件交给上层fragment或Activity处理，需要将本Fragment返回拦截暂时禁用（设置成false再次触发返回事件才会传递到上层）
                        mOnBackPressedCallback.setEnabled(false);
                        // 继续事件传递（这里其实是再次触发返回事件）
                        requireActivity().onBackPressed();
                        // 继续启用下次返回事件的拦截（这里post方法，利用Handler的消息队列原理，
                        // 需上面的requireActivity().onBackPressed()处理完毕后，才会执行post里面的逻辑）
                        Log.e("test", "id=" + id + ", mContentView=" + mContentView);
                        mContentView.post(() -> {
                            if (mOnBackPressedCallback != null) {
                                mOnBackPressedCallback.setEnabled(true);
                            }
                        });
                    }
                }
            });
        }
    }
}
