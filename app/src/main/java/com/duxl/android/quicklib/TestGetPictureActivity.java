package com.duxl.android.quicklib;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.duxl.android.quicklib.databinding.ActivityTestGetPictureBinding;
import com.duxl.baselib.ui.activity.SupportComponentResultActivity;
import com.duxl.baselib.utils.AlbumUtils;
import com.duxl.baselib.utils.EmptyUtils;
import com.duxl.baselib.utils.ToastUtils;
import com.zhihu.matisse.Matisse;

import java.util.List;

/**
 * rxpermissions
 * create by duxl 2020/8/19
 */
public class TestGetPictureActivity extends SupportComponentResultActivity {

    private ActivityTestGetPictureBinding mBinding;

    private final int mRequestCodeForImg = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_get_picture;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        mBinding = ActivityTestGetPictureBinding.bind(v);
        mBinding.btnPhoto.setOnClickListener(this::onClickView);
        mBinding.btnPermissions.setOnClickListener(this::onClickView);
    }

    public void onClickView(View v) {
        if (v.getId() == R.id.btn_permissions) {
            permissions();
        } else if (v.getId() == R.id.btn_photo) {
            new AlbumUtils().openImageForResult(this, 1, mRequestCodeForImg);
        }
    }

    private void permissions() {
        launchRequestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE
                }, resultMap -> {
                    for (String permission : resultMap.keySet()) {
                        boolean granted = Boolean.TRUE.equals(resultMap.get(permission));
                        if (granted) {
                            ToastUtils.show("有" + permission + "权限");
                            System.out.println("duxl.debug:: " + "有" + permission + "权限");
                        } else {
                            ToastUtils.show("获取" + permission + "权限失败");
                        }
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCodeForImg && data != null) {
            List<String> list = Matisse.obtainPathResult(data);
            if (EmptyUtils.isNotEmpty(list)) {
                Glide
                        .with(this)
                        .load(list.get(0))
                        .fitCenter()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mBinding.ivImg);
            }
        }
    }
}
