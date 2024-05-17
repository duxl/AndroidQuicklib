package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.databinding.ActivityTestStickyHeaderBinding;
import com.duxl.android.quicklib.databinding.AdapterStickyHeaderItemBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.decoration.StickyHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试黏性悬停header
 */
public class TestStickyHeaderActivity extends BaseActivity {

    protected ActivityTestStickyHeaderBinding mBinding;

    protected List<Item> mListData = getListData();
    protected Adapter mAdapter;
    protected StickyHeaderDecoration mStickyHeaderDecoration;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_sticky_header;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("黏性悬停分组-List");
        mBinding = ActivityTestStickyHeaderBinding.bind(v);
        mBinding.recyclerview.setAdapter(mAdapter = new Adapter(mListData));
        mBinding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_push) {
                mStickyHeaderDecoration.setStickyMode(StickyHeaderDecoration.StickyMode.PUSH);
            } else {
                mStickyHeaderDecoration.setStickyMode(StickyHeaderDecoration.StickyMode.COVER);
            }
        });

        mBinding.recyclerview.addItemDecoration(mStickyHeaderDecoration = new StickyHeaderDecoration(mBinding.recyclerview) {
            @Override
            public View getHeaderView(int position) {
                // 这里使用Adapter创建相同的header(目的是得到相同的header视图)
                View header = mAdapter.createHeaderView(mBinding.recyclerview);
                mAdapter.bindHeader(header, mListData.get(position));
                return header;
            }

            @Override
            public boolean isHeader(int position) {
                return mListData.get(position).isHeader;
            }
        });
    }

    protected List<Item> getListData() {
        List<Item> listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String title = "Group:" + i;
            listData.add(new Item(true, null, title));
            for (int j = 0; j < 10; j++) {
                listData.add(new Item(false, "Item:" + ((i * 10) + j), title));
            }
        }
        return listData;
    }

    public static class Item {
        public boolean isHeader;
        public String header;
        public String context;

        public Item(boolean isHeader, String context, String header) {
            this.isHeader = isHeader;
            this.context = context;
            this.header = header;
        }
    }

    protected static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Item> mListData;

        public Adapter(List<Item> listData) {
            this.mListData = listData;
        }

        @Override
        public int getItemViewType(int position) {
            return mListData.get(position).isHeader ? 0 : 1;
        }

        public View createHeaderView(ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sticky_header_item, parent, false);
        }

        public View createContentView(ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sticky_content_item, parent, false);
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            if (viewType == 0) {
                v = createHeaderView(parent);

            } else {
                v = createContentView(parent);
            }
            return new RecyclerView.ViewHolder(v) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Item item = mListData.get(position);
            if (item.isHeader) {
                bindHeader(holder.itemView, item);
            } else {
                int[] colors = new int[2];
                colors[0] = Color.parseColor("#33CCCCCC");
                colors[1] = Color.parseColor("#33000000");
                holder.itemView.setBackgroundColor(colors[position % colors.length]);

                TextView tv = holder.itemView.findViewById(R.id.tv_text);
                tv.setText(item.context);
                holder.itemView.setOnClickListener(v -> ToastUtils.show("点击了" + item.context));
            }
        }

        public void bindHeader(View header, Item item) {
            TextView tv = header.findViewById(R.id.tv_text);
            tv.setText(item.header);
            header.setOnClickListener(v -> ToastUtils.show("点击了分组:" + item.header));
            header.findViewById(R.id.v_del).setOnClickListener(vDel -> ToastUtils.show("点击了删除:" + item.header));
        }

        @Override
        public int getItemCount() {
            return mListData == null ? 0 : mListData.size();
        }
    }
}
