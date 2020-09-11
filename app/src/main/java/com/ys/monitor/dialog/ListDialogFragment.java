package com.ys.monitor.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.bean.KVBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename ListDialogFragment
 * @description -------------------------------------------------------
 * @date 2017/6/28 14:03
 */
public class ListDialogFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private ListView lv;
    private View mContentView;
    private ListAdapter listAdapter;
    private List<KVBean> beanList;
    private ItemClickListener itemClickListener;

    public static ListDialogFragment newInstance(int style, int theme) {
        ListDialogFragment pFragment = new ListDialogFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);

        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.dialog_list, null, false);
        lv = (ListView) mContentView.findViewById(R.id.lv_list);
        if (beanList == null) {
            beanList = new ArrayList<>();
        }
        listAdapter = new ListAdapter(getActivity(), beanList, R.layout.item_list_dialog);

        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (itemClickListener != null) {
                    itemClickListener.clickResult(listAdapter.getItem(position));
                }
                DialogUtil.removeDialog(getActivity());
            }
        });

//        //去掉背景
//        getDialog().getWindow().setBackgroundDrawable(new
//                ColorDrawable(Color.TRANSPARENT));
        return mContentView;
    }

    public interface ItemClickListener {
        void clickResult(KVBean listBean);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setBeanList(List<KVBean> beanList) {
        this.beanList = beanList;
    }
}
