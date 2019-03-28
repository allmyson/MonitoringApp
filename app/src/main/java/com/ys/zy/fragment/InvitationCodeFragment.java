package com.ys.zy.fragment;

import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.adapter.InvitationCodeAdapter;
import com.ys.zy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class InvitationCodeFragment extends BaseFragment {
    private ListView lv;
    private InvitationCodeAdapter adapter;
    private List<Object> list;
    public static InvitationCodeFragment newInstance(){
        return new InvitationCodeFragment();
    }
    @Override
    protected void init() {
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new InvitationCodeAdapter(mContext,list,R.layout.item_invitation_code);
        lv.setAdapter(adapter);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_nvitation_code;
    }
}
