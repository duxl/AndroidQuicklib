package com.duxl.android.quicklib.other;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.duxl.android.quicklib.R;
import com.duxl.baselib.ui.activity.BaseActivity;

import java.util.Locale;

/**
 * 自定义Context上下文，可以自由加载白天模式或黑夜模式，中文资源或英文资源等
 */
public class CustomContextActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom_context;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("自定义Context上下文");
    }

    private void showToast(Context customContext, View v) {
        Toast toast = new Toast(customContext);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示白天
     *
     * @param v
     */
    public void showDayToast(View v) {
        Configuration configuration = getResources().getConfiguration();
        configuration.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        configuration.uiMode |= Configuration.UI_MODE_NIGHT_NO;
        Context customContext = getContext().createConfigurationContext(configuration);

        View view = LayoutInflater.from(customContext).inflate(R.layout.layout_test_custom_context, null, false);
        showToast(customContext, view);
    }

    /**
     * 显示晚上
     *
     * @param v
     */
    public void showNightToast(View v) {
        Configuration configuration = getResources().getConfiguration();
        configuration.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        configuration.uiMode |= Configuration.UI_MODE_NIGHT_YES;
        Context customContext = getContext().createConfigurationContext(configuration);

        View view = LayoutInflater.from(customContext).inflate(R.layout.layout_test_custom_context, null, false);
        showToast(customContext, view);
    }

    /**
     * 显示中文
     *
     * @param v
     */
    public void showChineseToast(View v) {
        Configuration configuration = getResources().getConfiguration();
        // 默认string.xml资源就是中文，这里不需要设置
        Context customContext = getContext().createConfigurationContext(configuration);

        View view = LayoutInflater.from(customContext).inflate(R.layout.layout_test_custom_context, null, false);
        showToast(customContext, view);
    }

    /**
     * 显示英文
     *
     * @param v
     */
    public void showEnglishToast(View v) {
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = Locale.ENGLISH;
        Context customContext = getContext().createConfigurationContext(configuration);

        View view = LayoutInflater.from(customContext).inflate(R.layout.layout_test_custom_context, null, false);
        showToast(customContext, view);
    }
}
