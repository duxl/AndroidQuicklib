package com.duxl.android.quicklib;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.duxl.android.quicklib.databinding.DialogTestBaseBottomSheetBinding;
import com.duxl.baselib.ui.dialog.BaseBottomSheetDialogFragment;

public class TestBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {

    private DialogTestBaseBottomSheetBinding binding;

    @Override
    public int getResId() {
        return R.layout.dialog_test_base_bottom_sheet;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavigationBarContrastEnforced(false);
        setNavigationBarColor(Color.parseColor("#FF0000"));
        binding = DialogTestBaseBottomSheetBinding.bind(view);
        binding.btnClose.setOnClickListener(v -> dismissDialog());
    }
}
