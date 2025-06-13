package com.duxl.android.quicklib;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.duxl.android.quicklib.databinding.DialogTestBaseBottomSheetBinding;
import com.duxl.baselib.ui.dialog.BaseBottomSheetDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TestBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {

    private DialogTestBaseBottomSheetBinding binding;

    @Override
    public int getResId() {
        return R.layout.dialog_test_base_bottom_sheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(BottomSheetDialogFragment.STYLE_NORMAL, DialogFragment.STYLE_NO_FRAME);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavigationBarColor(Color.parseColor("#CCCCCC"));
        binding = DialogTestBaseBottomSheetBinding.bind(view);
        binding.btnClose.setOnClickListener(v -> dismissDialog());
    }
}
