package com.ys.zy.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
//下级返点率
public class SubReturnRateFragment extends BaseFragment implements View.OnClickListener {
    private EditText et;
    private Button btn;

    public static SubReturnRateFragment newInstance() {
        return new SubReturnRateFragment();
    }

    @Override
    protected void init() {
        et = getView(R.id.et_);
        btn = getView(R.id.btn_);
        setBtnClickable(false,btn);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    setBtnClickable(false, btn);
                } else {
                    setBtnClickable(true, btn);
                }
            }
        });
        btn.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sub_return_rate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_:
                show("生成邀请码");
                break;
        }
    }
}
