package com.duxl.android.quicklib.other;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.duxl.android.quicklib.R;
import com.duxl.android.quicklib.databinding.ActivityTestTextExpandBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.SpanUtils;
import com.duxl.baselib.utils.TextExpandUtil;

/**
 * 测试文本折叠工具，例如文本超过3行就只显示3行，点击后展开更多
 */
public class TestTextExpandActivity extends BaseActivity {

    //private final String fullText = "The Android Studio build system is based on Gradle, and the Android Gradle plugin (AGP) adds several features that are specific to building Android apps. The following table lists which version of AGP is required for each version of Android Studio.";
    private final String fullText = "Android Studio 和 AGP 需要满足最低版本要求才能支持特定 API 级别。如果使用的 Android Studio 或 AGP 版本低于项目的 targetSdk 或 compileSdk 所要求的版本，可能会导致意外问题。我们建议您使用最新的预览版 Android Studio 和 AGP 来处理以预览版 Android OS 为目标平台的项目。您可以安装 Android Studio 的预览版以及稳定版。";
    private boolean isText1Expand = false;
    private boolean isText2Expand = false;
    private ActivityTestTextExpandBinding binding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_text_expand;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("文本太长折叠显示更多");
        binding = ActivityTestTextExpandBinding.bind(v);
        test01();
        test02();
    }

    private void test01() {
        binding.tvText01.setText(fullText);
        binding.btnExpand.setOnClickListener(btnExpand -> {
            if (!isText1Expand) {
                binding.btnExpand.setText("展开");
                TextExpandUtil.collapse(binding.tvText01, "", 3, expand -> isText1Expand = expand);
            } else {
                binding.btnExpand.setText("折叠");
                isText1Expand = false;
                binding.tvText01.setText(fullText);
            }
        });
    }

    private void test02() {
        binding.tvText02.setText(fullText);
        binding.tvText02.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder moreText = new SpanUtils()
                .append(" 更多")
                .setForegroundColor(Color.BLUE)
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        binding.tvText02.setText(fullText);
                    }
                })
                .create();
        TextExpandUtil.collapse(binding.tvText02, moreText, 3, expand -> isText2Expand = expand);
    }
}
