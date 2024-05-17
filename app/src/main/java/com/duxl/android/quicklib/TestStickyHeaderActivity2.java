package com.duxl.android.quicklib;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试黏性悬停header
 */
public class TestStickyHeaderActivity2 extends TestStickyHeaderActivity {
    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("黏性悬停分组-Grid");
        setGridLayoutManager();
    }

    private void setGridLayoutManager() {
        int spanCount = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mListData.get(position).isHeader) {
                    // 如果是分组，占3/3列宽度
                    return spanCount;
                }
                // 每个item占1/3宽度
                return 1;
            }
        });
        mBinding.recyclerview.setLayoutManager(gridLayoutManager);
    }

    @Override
    public List<Item> getListData() {
        List<Item> listData = new ArrayList<>();
        int adapterPosition = 0;
        for (int i = 1; i <= 10; i++) {
            int childCount = 10 + i % 3; // 这里有意将子view的个数不同
            String title = "Group:" + i + " --> ChildCount:" + childCount;
            listData.add(new Item(true, null, title));
            adapterPosition++;
            for (int j = 1; j <= childCount; j++) {
                listData.add(new Item(false, "p" + adapterPosition + "-g" + i + "-c" + j, title));
                adapterPosition++;
            }
        }
        return listData;
    }
}
