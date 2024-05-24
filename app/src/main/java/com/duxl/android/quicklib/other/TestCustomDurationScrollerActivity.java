package com.duxl.android.quicklib.other;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.duxl.android.quicklib.R;
import com.duxl.android.quicklib.databinding.ActivityTestCustomDurationScrollerBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.adapter.ViewPagerFragmentAdapter;
import com.duxl.baselib.widget.CustomDurationScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 修改ViewPager切换动画时长
 */
public class TestCustomDurationScrollerActivity extends BaseActivity {

    private ActivityTestCustomDurationScrollerBinding binding;
    private CustomDurationScroller mCustomDurationScroller;
    private List<MyFragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_custom_duration_scroller;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        binding = ActivityTestCustomDurationScrollerBinding.bind(v);
        setupAdapter();

        try {
            // 修改ViewPager切换Scroller
            mCustomDurationScroller = new CustomDurationScroller(this, new LinearInterpolator());
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(binding.viewPager, mCustomDurationScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnApply.setOnClickListener(view -> {
            int duration = Integer.parseInt(binding.etDuration.getText().toString());
            mCustomDurationScroller.setFixDuration(duration);
        });

        binding.btnPre.setOnClickListener(view -> {
            int current = binding.viewPager.getCurrentItem();
            current--;
            if (current < 0) {
                current = 0;
            }
            binding.viewPager.setCurrentItem(current, true);
        });

        binding.btnNext.setOnClickListener(view -> {
            int current = binding.viewPager.getCurrentItem();
            current++;
            if (current > fragments.size() - 1) {
                current = fragments.size() - 1;
            }
            binding.viewPager.setCurrentItem(current, true);
        });

    }

    private void setupAdapter() {
        fragments.add(new MyFragment("first-PageA", Color.WHITE));
        fragments.add(new MyFragment("second-PageC", Color.CYAN));
        fragments.add(new MyFragment("third-PageD", Color.GREEN));
        fragments.add(new MyFragment("four-PageE", Color.RED));
        fragments.add(new MyFragment("end-PageF", Color.GRAY));
        binding.viewPager.setAdapter(new ViewPagerFragmentAdapter<>(getSupportFragmentManager(), fragments));
    }


    public static class MyFragment extends Fragment {
        private String text;
        private int bg;

        public MyFragment(String text, int bg) {
            this.text = text;
            this.bg = bg;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(requireContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(30);
            textView.setTextColor(Color.BLUE);
            textView.setBackgroundColor(bg);
            textView.setText(text);
            return textView;
        }
    }
}
