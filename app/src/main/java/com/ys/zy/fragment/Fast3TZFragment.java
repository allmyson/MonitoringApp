package com.ys.zy.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.Fast3SumAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.Fast3Bean;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.dialog.TipFragment;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

import static com.ys.zy.activity.Fast3Activity.TYPE_1FK3;
import static com.ys.zy.activity.Fast3Activity.TYPE_5FK3;
import static com.ys.zy.activity.Fast3Activity.TYPE_JSK3;

public class Fast3TZFragment extends BaseFragment implements View.OnClickListener {
    private GridView gv;
    private List<Fast3Bean> list;
    private Fast3SumAdapter fast3SumAdapter;
    private EditText priceET;
    private TextView clearTV;
    private LinearLayout priceLL;
    private TextView zhuAndPriceTV;
    private TextView tzTV;
    private int type;
    private String gameName;
    private String gameNo = "0214983";

    public static Fast3TZFragment newInstance(int type) {
        Fast3TZFragment fast3TZFragment = new Fast3TZFragment();
        fast3TZFragment.setType(type);
        return fast3TZFragment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void init() {
        switch (type) {
            case TYPE_JSK3:
                gameName = "江苏快3";
                break;
            case TYPE_1FK3:
                gameName = "1分快3";
                break;
            case TYPE_5FK3:
                gameName = "5分快3";
                break;
        }
        zhuAndPriceTV = getView(R.id.tv_zhuAndPrice);
        tzTV = getView(R.id.tv_tz);
        tzTV.setOnClickListener(this);
        gv = getView(R.id.gv_);
        list = new ArrayList<>();
        list.addAll(Fast3Bean.getList());
        fast3SumAdapter = new Fast3SumAdapter(mContext, list, R.layout.item_fast3);
        gv.setAdapter(fast3SumAdapter);
        fast3SumAdapter.setChooseListener(new Fast3SumAdapter.ChooseListener() {
            @Override
            public void chooseResult(List<Fast3Bean> list) {
                zhuAndPriceTV.setText("共" + list.size() + "注," + list.size() * StringUtil.StringToDoubleTwo(priceET.getText().toString()) + "元");
                if (list.size() > 0) {
                    priceLL.setVisibility(View.VISIBLE);
                } else {
                    priceLL.setVisibility(View.GONE);
                }
            }
        });
        priceET = getView(R.id.et_price);
        priceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (priceET.getText().toString().matches("^0")) {//判断当前的输入第一个数是不是为0
                    priceET.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                zhuAndPriceTV.setText("共" + fast3SumAdapter.getTZList().size() + "注," + StringUtil.StringToDoubleStr(fast3SumAdapter.getTZList().size() * StringUtil.StringToDoubleTwo(priceET.getText().toString())) + YS.UNIT);
            }
        });
        clearTV = getView(R.id.tv_clear);
        clearTV.setOnClickListener(this);
        priceLL = getView(R.id.ll_price);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fast3_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                fast3SumAdapter.clear();
                break;
            case R.id.tv_tz:
                if (fast3SumAdapter.getTZList().size() == 0) {
                    DialogUtil.showTip(mContext, "请至少选择一注号码投注", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                        }
                    });
                } else if (StringUtil.isBlank(priceET.getText().toString())) {
                    DialogUtil.showTip(mContext, "请填写您要投注的金额", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                        }
                    });
                } else {
                    DialogUtil.showTZTip(mContext, gameName, gameNo, StringUtil.StringToDoubleStr(fast3SumAdapter.getTZList().size() * StringUtil.StringToDoubleTwo(priceET.getText().toString())), fast3SumAdapter.getTZResult(), new TZTipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                            show("投注成功");
                        }
                    });
                }
                break;
        }
    }

}
