package com.duxl.android.quicklib;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.duxl.baselib.rx.SimpleObserver;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.AlbumUtils;
import com.duxl.baselib.utils.EmptyUtils;
import com.duxl.baselib.utils.ToastUtils;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * rxpermissions
 * create by duxl 2020/8/19
 */
public class TestGetPictureActivity extends BaseActivity {

    @BindView(R.id.iv_img)
    ImageView mIvImg;

    private final int mRequestCodeForImg = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_get_picture;
    }

    @OnClick({R.id.btn_photo, R.id.btn_permissions})
    public void onClickView(View v) {
        if (v.getId() == R.id.btn_permissions) {
            permissions();
        } else if (v.getId() == R.id.btn_photo) {
            new AlbumUtils().openImageForResult(this, 1, mRequestCodeForImg);
        }
    }

    private void permissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // 1、使用request
//        rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(new SimpleObserver<Boolean>() {
//                    @Override
//                    public void onNext(@NonNull Boolean aBoolean) {
//                        ToastUtils.show("权限：" + aBoolean);
//                    }
//                });


        // 2、使用requestEach
//        rxPermissions
//                .requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new SimpleObserver<Permission>() {
//                    @Override
//                    public void onNext(@NonNull Permission permission) {
//                        if(permission.granted) {
//                            ToastUtils.show("有"+permission.name+"权限");
//                        } else if(permission.shouldShowRequestPermissionRationale) {
//                            ToastUtils.show("需要重写申请权限");
//                        } else {
//                            ToastUtils.show("获取权限失败");
//                        }
//                    }
//                });

        // 3、使用requestEachCombined
        rxPermissions
                .requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new SimpleObserver<Permission>() {
                    @Override
                    public void onNext(@NonNull Permission permission) {
                        if (permission.granted) {
                            ToastUtils.show("有" + permission.name + "权限");
                            System.out.println("duxl.debug:: " + "有" + permission.name + "权限");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            ToastUtils.show("需要重写申请权限");
                        } else {
                            ToastUtils.show("获取权限失败");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCodeForImg) {
            List<String> list = Matisse.obtainPathResult(data);
            if (EmptyUtils.isNotEmpty(list)) {
                Glide
                        .with(this)
                        .load(list.get(0))
                        .fitCenter()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mIvImg);
            }
        }
    }
}
