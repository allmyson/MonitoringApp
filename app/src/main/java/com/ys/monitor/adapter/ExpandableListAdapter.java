package com.ys.monitor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.bean.KVBean;
import com.ys.monitor.bean.UserList;
import com.ys.monitor.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    private Map<String, List<UserList.DataBean.RowsBean>> map;
    private List<KVBean> groupList;
    private List<List<UserList.DataBean.RowsBean>> childList;

    public ExpandableListAdapter(Context context,
                                 Map<String, List<UserList.DataBean.RowsBean>> map) {
        this.context = context;
        this.map = map;
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
                if (StringUtil.isBlank(key)) {
                    return;
                }
                List<UserList.DataBean.RowsBean> value = map.get(key);//
                KVBean kvBean = new KVBean();
                kvBean.id = key;
                if (value != null && value.size() > 0) {
                    for (UserList.DataBean.RowsBean rowsBean : value) {
                        if (key.equals(rowsBean.deptNo)) {
                            kvBean.name = rowsBean.deptName;
                            break;
                        }
                    }
                }
                groupList.add(kvBean);
                childList.add(value);
            }
        }
    }

    public void refresh(Map<String, List<UserList.DataBean.RowsBean>> map) {
        this.map = map;
        groupList.clear();
        childList.clear();
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
                if (StringUtil.isBlank(key)) {
                    return;
                }
                List<UserList.DataBean.RowsBean> value = map.get(key);//
                KVBean kvBean = new KVBean();
                kvBean.id = key;
                if (value != null && value.size() > 0) {
                    for (UserList.DataBean.RowsBean rowsBean : value) {
                        if (key.equals(rowsBean.deptNo)) {
                            kvBean.name = rowsBean.deptName;
                            break;
                        }
                    }
                }
                groupList.add(kvBean);
                childList.add(value);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取一级标签总数
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 获取一级标签下二级标签的总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    /**
     * 获取一级标签内容
     */
    @Override
    public KVBean getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    /**
     * 获取一级标签下二级标签的内容
     */
    @Override
    public UserList.DataBean.RowsBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    /**
     * 获取一级标签的ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取二级标签的ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 对一级标签进行设置
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        convertView = (LinearLayout) LinearLayout.inflate(context,
                R.layout.item_group_layout, null);

        ImageView group_icon = (ImageView) convertView
                .findViewById(R.id.img_icon);
        TextView group_title = (TextView) convertView
                .findViewById(R.id.group_title);
        if (isExpanded) {
            group_icon.setImageResource(R.mipmap.addressbook_close);
        } else {
            group_icon.setImageResource(R.mipmap.addressbook_open);

        }
        group_title.setText(StringUtil.valueOf(groupList.get(groupPosition).name));
        return convertView;
    }

    /**
     * 对一级标签下的二级标签进行设置
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = (LinearLayout) LinearLayout.inflate(context,
                R.layout.item_child_layout, null);
        TextView child_text = (TextView) convertView
                .findViewById(R.id.child_text);
        TextView phoneTV = (TextView) convertView
                .findViewById(R.id.tv_phone);
        ImageView msgIV = (ImageView) convertView.findViewById(R.id.iv_msg);
        ImageView phoneIV = (ImageView) convertView.findViewById(R.id.iv_phone);
        child_text.setText(childList.get(groupPosition).get(childPosition).trueName);
        msgIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final String phone =
                StringUtil.valueOf(childList.get(groupPosition).get(childPosition).mobilePhoneNumber);
        phoneTV.setText(phone);
        phoneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionApi.diallPhone(context, phone);
            }
        });
        return convertView;
    }

    /**
     * 当选择子节点的时候，调用该方法
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
