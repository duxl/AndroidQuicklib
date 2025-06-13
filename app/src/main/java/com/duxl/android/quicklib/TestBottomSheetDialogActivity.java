package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.ActivityTestBottomSheetDialogBinding;
import com.duxl.baselib.ui.activity.BaseActivity;

public class TestBottomSheetDialogActivity extends BaseActivity {

    private ActivityTestBottomSheetDialogBinding binding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_bottom_sheet_dialog;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("TestBaseBottomSheetDialog");
        binding = ActivityTestBottomSheetDialogBinding.bind(v);
        binding.btnShowDialog.setOnClickListener(v1 -> showDialog());
    }

    private void showDialog() {
        new TestBottomSheetDialogFragment().showDialog(getSupportFragmentManager());
    }
}
