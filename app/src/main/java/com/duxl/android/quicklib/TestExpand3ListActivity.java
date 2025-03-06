package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.baselib.ui.adapter.BaseExpandableAdapter;
import com.duxl.baselib.utils.AnimUtils;
import com.duxl.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 可折叠的列表（三级）
 */
public class TestExpand3ListActivity extends TestExpandListActivity {

    private BaseExpandableAdapter<AreaItem, AreaItem> provinceAdapter;

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("可折叠的列表（三级）");
        // 动画导致列表展开和则跌抖动，这里取消动画
        AnimUtils.setRecyclerAnimEnable(mRecyclerView, false);
    }

    protected void setAdapter() {
        // 注意：省份下的城市也是一个可折叠的列表，所以布局传的是一个列表
        provinceAdapter = new BaseExpandableAdapter<AreaItem, AreaItem>(R.layout.adapter_test_area_item, R.layout.adapter_test_area_list_item) {
            @Override
            protected void convert(@NonNull ExpandViewHolder holder, AreaItem dataGroup) {
                super.convert(holder, dataGroup);
                // 动画导致列表展开和则跌抖动不自然，这里取消动画
                AnimUtils.setLayoutAnimateChangesEnable(((ViewGroup) holder.getRoot()), false);
            }

            // 绑定省份
            @Override
            protected void bindGroup(@NonNull View viewGroup, AreaItem province, int positionGroup) {
                TextView tvProvince = viewGroup.findViewById(R.id.tv_area);
                tvProvince.setBackgroundColor(Color.parseColor("#AAAAAA"));
                tvProvince.setText(province.areaName);
            }

            @Override
            protected void onBindChildren(RecyclerView recyclerChildren, AreaItem province, int positionProvince) {
                //super.onBindChildren(recyclerChildren, province, positionGroup);
                // 动画导致列表展开和则跌抖动，这里取消动画
                AnimUtils.setRecyclerAnimEnable(recyclerChildren, false);
                // 省份下的item也是一个列表，所以需要设置setHasFixedSize(false)
                recyclerChildren.setHasFixedSize(false);
                recyclerChildren.setNestedScrollingEnabled(false);
                recyclerChildren.setLayoutManager(getChildrenLayoutManger(recyclerChildren, province, positionProvince));
                // 绑定城市（也是一个可折叠的）
                BaseExpandableAdapter<AreaItem, AreaItem> cityListAdapter = new BaseExpandableAdapter<AreaItem, AreaItem>(R.layout.adapter_test_area_item, R.layout.adapter_test_area_item) {
                    @Override
                    protected void bindGroup(@NonNull View viewGroup, AreaItem city, int positionGroup) {
                        TextView tvCity = viewGroup.findViewById(R.id.tv_area);
                        tvCity.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        tvCity.setText("--------" + city.areaName);
                    }

                    @Override
                    protected void bindChild(@NonNull RecyclerView recyclerChildren, @NonNull View childView, AreaItem city, int positionGroup, AreaItem area, int positionChild) {
                        // 绑定地区
                        TextView tvArea = childView.findViewById(R.id.tv_area);
                        tvArea.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvArea.setText("------------------------" + area.areaName);
                    }
                };
                cityListAdapter.setNewInstance(province.getChildren());
                recyclerChildren.setAdapter(cityListAdapter);
                expandChildren(recyclerChildren, province);

                // 点击城市展开下面的地区列表
                cityListAdapter.setOnItemClickListener((adapter, view, position) -> {
                    AreaItem clickCity = cityListAdapter.getItem(position);
                    clickCity._isExpand = !clickCity._isExpand;
                    //cityListAdapter.notifyItemChanged(position);
                    // 三级展开对应的二级其实也变化了，所以这里notify父级
                    //provinceAdapter.notifyItemChanged(positionProvince);
                    cityListAdapter.notifyItemExpandChanged(position);
                });

                // 设置地区点击事件
                cityListAdapter.setOnChildItemClickListener((adapterChildren, childView, positionArea, positionCity) -> {
                    AreaItem areaItem = provinceAdapter.getItem(positionProvince).getChildren().get(positionCity).getChildren().get(positionArea);
                    ToastUtils.show(areaItem.areaName);
                });
            }

            // 绑定城市
            @Override
            protected void bindChild(@NonNull RecyclerView recyclerChildren, @NonNull View childView, AreaItem province, int positionGroup, AreaItem city, int positionChild) {
                // todo 这里的回调是普通列表，城市的下面还有可折叠的区域列表，所以只能在onBindChildren方法中对省份下的城市item项绑定折叠适配器
            }
        };
        provinceAdapter.setNewInstance(getAreaList());
        provinceAdapter.setOnItemClickListener((adapter1, view, position) -> {
            // 点击省份展开下面的城市列表
            AreaItem province = provinceAdapter.getItem(position);
            province._isExpand = !province._isExpand;
            //provinceAdapter.notifyItemChanged(position);
            provinceAdapter.notifyItemExpandChanged(position);
        });
        mRecyclerView.setAdapter(provinceAdapter);

    }

    private List<AreaItem> getAreaList() {
        List<AreaItem> list = new ArrayList<>();
        AreaItem chongqing = new AreaItem("重庆市");
        list.add(chongqing);

        AreaItem chongqing_yuzhong = new AreaItem("渝中区");
        chongqing_yuzhong.children.add(new AreaItem("解放碑"));
        chongqing_yuzhong.children.add(new AreaItem("两路口"));
        chongqing_yuzhong.children.add(new AreaItem("上清寺"));
        chongqing_yuzhong.children.add(new AreaItem("石油路"));
        chongqing.children.add(chongqing_yuzhong);

        AreaItem chongqing_jiangbei = new AreaItem("江北区");
        chongqing_jiangbei.children.add(new AreaItem("红旗河沟"));
        chongqing_jiangbei.children.add(new AreaItem("观音桥"));
        chongqing_jiangbei.children.add(new AreaItem("五里店"));
        chongqing_jiangbei.children.add(new AreaItem("红土地"));
        chongqing.children.add(chongqing_jiangbei);

        AreaItem chongqing_nanan = new AreaItem("南岸区");
        chongqing_nanan.children.add(new AreaItem("弹子石"));
        chongqing_nanan.children.add(new AreaItem("南坪"));
        chongqing_nanan.children.add(new AreaItem("四公里"));
        chongqing_nanan.children.add(new AreaItem("二塘路"));
        chongqing.children.add(chongqing_nanan);


        AreaItem sichuan = new AreaItem("四川省");
        list.add(sichuan);

        AreaItem sichuan_chengdu = new AreaItem("成都市");
        sichuan_chengdu.children.add(new AreaItem("金牛区"));
        sichuan_chengdu.children.add(new AreaItem("武侯区"));
        sichuan_chengdu.children.add(new AreaItem("青羊区"));
        sichuan_chengdu.children.add(new AreaItem("锦江区"));
        sichuan.children.add(sichuan_chengdu);

        AreaItem sichuan_mianyang = new AreaItem("绵阳市");
        sichuan_mianyang.children.add(new AreaItem("涪城区"));
        sichuan_mianyang.children.add(new AreaItem("游仙区"));
        sichuan_mianyang.children.add(new AreaItem("三台县"));
        sichuan_mianyang.children.add(new AreaItem("盐亭县"));
        sichuan.children.add(sichuan_mianyang);

        return list;
    }

    public static class AreaItem implements BaseExpandableAdapter.GroupItemEntity<AreaItem> {

        public boolean _isExpand;
        public String areaName;
        public List<AreaItem> children = new ArrayList<>();

        public AreaItem(String areaName) {
            this.areaName = areaName;
        }

        @Override
        public boolean isExpand() {
            return _isExpand;
        }

        @Override
        public List<AreaItem> getChildren() {
            return children;
        }
    }
}

