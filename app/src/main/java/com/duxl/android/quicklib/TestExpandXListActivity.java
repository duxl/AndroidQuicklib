package com.duxl.android.quicklib;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.databinding.ActivityTestExpandxListBinding;
import com.duxl.android.quicklib.databinding.AdapterTreeItemBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.adapter.BaseExpandableAdapter;
import com.duxl.baselib.utils.AnimUtils;
import com.duxl.baselib.utils.EmptyUtils;
import com.duxl.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/**
 * 任意多级可展开列表
 */
public class TestExpandXListActivity extends BaseActivity {

    protected ActivityTestExpandxListBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_expandx_list;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("可折叠的列表（任意多级）");
        mBinding = ActivityTestExpandxListBinding.bind(v);
        TreeAdapter adapter = new TreeAdapter(0);
        mBinding.recyclerview.setAdapter(adapter);

        List<TreeItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 添加父级
            TreeItem parent = new TreeItem("父级" + i);
            list.add(parent);
            // 添加子级
            for (int j = 0; j < 3; j++) {
                TreeItem child = new TreeItem("子级" + i + "-" + j);
                parent.children.add(child);
            }
        }
        for (int k = 0; k < 5; k++) {
            // 添加孙子级
            // 在第二层数据的第一个添加第三层数据
            TreeItem grandson1 = new TreeItem("孙级1-0-" + k);
            list.get(1).children.get(0).children.add(grandson1);
            // 在第二层数据的第二个添加第三层数据
            TreeItem grandson2 = new TreeItem("孙级1-1-" + k);
            list.get(1).children.get(1).children.add(grandson2);
        }

        adapter.setNewInstance(list);
    }


    public static class TreeItem implements BaseExpandableAdapter.GroupItemEntity<TreeItem> {

        public String text;
        public boolean isExpand;
        public List<TreeItem> children = new ArrayList<>();

        public TreeItem(String text) {
            this.text = text;
        }

        @Override
        public boolean isExpand() {
            return isExpand;
        }

        @Override
        public List<TreeItem> getChildren() {
            return children;
        }
    }

    public class TreeAdapter extends BaseExpandableAdapter<TreeItem, TreeItem> {

        private final String UNIT_INDENT = "\u3000"; // 中文全角、 一个中文宽度的空格缩进
        private int level;

        /**
         * @param level 树的层级，顶层为0，下一级为1，以此类推
         */
        public TreeAdapter(int level) {
            super(R.layout.adapter_tree_item, R.layout.adapter_tree_item);
            this.level = level;
        }

        @Override
        protected void bindGroup(@NonNull View viewGroup, TreeItem dataGroup, int positionGroup) {
            AdapterTreeItemBinding binding = AdapterTreeItemBinding.bind(viewGroup.findViewById(R.id.root));
            bindItem(binding, this, dataGroup, positionGroup, level);
        }

        @Override
        protected void onBindChildren(RecyclerView recyclerChildren, TreeItem dataGroup, int positionGroup) {
            super.onBindChildren(recyclerChildren, dataGroup, positionGroup);
            recyclerChildren.setHasFixedSize(false);
        }

        @Override
        protected void bindChild(@NonNull RecyclerView recyclerChildren, @NonNull View childView, TreeItem dataGroup, int positionGroup, TreeItem dataChild, int positionChild) {
            AdapterTreeItemBinding binding = AdapterTreeItemBinding.bind(childView.findViewById(R.id.root));
            if (EmptyUtils.isEmpty(dataChild.getChildren())) {
                // 子项目不再有下级数据了，直接显示
                binding.llInfo.setVisibility(View.VISIBLE);
                bindItem(binding, recyclerChildren.getAdapter(), dataChild, positionChild, level + 1);
                binding.recyclerviewChildren.setAdapter(null);
            } else {
                // 子项还有下级数据，通过折叠列表来显示子项以及子项的下级数据
                binding.llInfo.setVisibility(View.GONE);
                TreeAdapter nextAdapter = new TreeAdapter(level + 1);
                List<TreeItem> list = new ArrayList<>();
                list.add(dataChild);
                nextAdapter.setNewInstance(list);
                binding.recyclerviewChildren.setAdapter(nextAdapter);
            }
        }

        private void bindItem(AdapterTreeItemBinding binding, RecyclerView.Adapter adapter, TreeItem item, int position, int level) {
            //binding.ivTriangle.clearAnimation();
            binding.ivTriangle.setRotation(item.isExpand ? 0 : -90);
            binding.ivTriangle.setVisibility(EmptyUtils.isNotEmpty(item.children) ? View.VISIBLE : View.INVISIBLE);

            String indentSpace = ""; // 缩进
            for (int i = 0; i < level; i++) {
                indentSpace += UNIT_INDENT;
            }

            binding.tvIndent.setText(indentSpace);
            binding.tvText.setText(item.text);


            binding.getRoot().setOnClickListener(v -> {
                // 点击列表的处理逻辑，需要根据自己的需求处理，下面代码仅用于示例展示
                // 下面的示例逻辑是：点击item如果有子级就展开，没有就toast打印
                if (EmptyUtils.isNotEmpty(item.getChildren())) {
                    item.isExpand = !item.isExpand;
                    adapter.notifyItemChanged(position);
                } else {
                    ToastUtils.show("层级=" + (level + 1) + ": " + item.text);
                }
            });
        }
    }
}
