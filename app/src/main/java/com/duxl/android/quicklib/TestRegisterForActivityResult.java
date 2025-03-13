package com.duxl.android.quicklib;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.duxl.android.quicklib.databinding.ActivityTestRegisterForResultBinding;
import com.duxl.baselib.ui.activity.SupportComponentResultActivity;

import java.io.File;
import java.util.Map;

public class TestRegisterForActivityResult extends SupportComponentResultActivity {

    private ActivityTestRegisterForResultBinding binding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_register_for_result;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试registerForActivityResult");
        binding = ActivityTestRegisterForResultBinding.bind(v);

        // 普通Activity返回值
        binding.btnSimpleActivityResult.setOnClickListener(btn -> {
            launchStartActivityForResult(new Intent(this, ResultDataActivity.class), activityResult -> {
                if (activityResult.getResultCode() == Activity.RESULT_OK) {
                    binding.tvResult.setText(activityResult.getData().getStringExtra("data"));
                }
            });
        });


        // 单权限获取
        binding.btnRequestPermission.setOnClickListener(btn -> {
            launchRequestPermission(Manifest.permission.CAMERA, data -> {
                binding.tvResult.setText("单权限获取\n是否获得CAMERA权限：" + data);
            });
        });

        // 多权限获取
        binding.btnRequestPermissions.setOnClickListener(btn -> {
            launchRequestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_CALL_LOG
            }, data -> {
                binding.tvResult.setText("多权限获取\n");
                for (Map.Entry<String, Boolean> entry : data.entrySet()) {
                    binding.tvResult.append(entry.getKey());
                    binding.tvResult.append("-> 是否有权限 = ");
                    binding.tvResult.append(entry.getValue() + "");
                    binding.tvResult.append("\n");
                }
            });
        });

        // 拍照(指定uri保存)
        binding.btnTakePicture.setOnClickListener(btn -> {
            File file = new File(getExternalCacheDir(), "test.jpg");
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            launchOpenCamera(fileUri, isOk -> {
                if (isOk) {
                    binding.ivImg.setImageURI(fileUri);
                }
            });
        });

        // 拍照(返回bitmap)
        binding.btnTakePictureBitmap.setOnClickListener(btn -> {
            launchOpenCameraBitmap(bitmap -> {
                if (bitmap != null) {
                    binding.ivImg.setImageBitmap(bitmap);
                }
            });
        });

        // 录视频
        binding.btnRecordVideo.setOnClickListener(btn -> {
            File file = new File(getExternalCacheDir(), "test.3gp");
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            launchRecordVideo(fileUri, bitmap -> {
                binding.tvResult.setText("录视频\n视频uri：" + fileUri);
            });
        });

        // 支持单个文件，返回单个文件
        binding.btnOpenFile.setOnClickListener(btn -> {
            launchOpenFile("image/*", data -> {
                binding.tvResult.setText("支持单个文件，返回单个文件\n文件地址：" + data);
                binding.ivImg.setImageURI(data);
            });
        });

        // 支持多个文件，返回单个文件
        binding.btnOpenDocument.setOnClickListener(btn -> {
            launchOpenDocument(new String[]{"image/*", "text/plain", "application/pdf", "video/*"}, data -> {
                binding.tvResult.setText("支持多个文件，返回单个文件\n文件地址：" + data);
            });
        });

        // 支持多个文件，返回多个文件
        binding.btnOpenDocuments.setOnClickListener(btn -> {
            launchOpenDocuments(new String[]{"image/*", "text/plain", "application/pdf", "video/*"}, list -> {
                binding.tvResult.setText("支持多个文件，返回多个文件\n文件地址：\n");
                for (Uri uri : list) {
                    binding.tvResult.append(uri + "\n");
                }
            });
        });
    }
}
