package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.Bank;
import com.ys.zy.http.HttpListener;
import com.ys.zy.util.BankUtil;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;

public class AddBankCardActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    private EditText nameET, cardET, reCardET, jymmET;
    private TextView bankTV;
    private Button sureBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("添加银行卡");
        nameET = getView(R.id.et_name);
        cardET = getView(R.id.et_card);
        reCardET = getView(R.id.et_reCard);
        jymmET = getView(R.id.et_jymm);
        bankTV = getView(R.id.tv_bank);
        sureBtn = getView(R.id.btn_sure);
        sureBtn.setOnClickListener(this);
        nameET.addTextChangedListener(this);
        cardET.addTextChangedListener(this);
        reCardET.addTextChangedListener(this);
        jymmET.addTextChangedListener(this);
        nameET.setOnFocusChangeListener(this);
        cardET.setOnFocusChangeListener(this);
        reCardET.setOnFocusChangeListener(this);
        jymmET.setOnFocusChangeListener(this);
        setBtnClickable(false, sureBtn);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                if (isCanAdd()) {
                    show("添加成功");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (cardET.isFocused() && cardET.getText().length() >= 16 && cardET.getText().length() <= 19) {
            HttpUtil.getBankNameByCard(mContext, cardET.getText().toString().trim(), new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    Bank bank = new Gson().fromJson(response.get(), Bank.class);
                    if (bank != null && "ok".equals(bank.stat) && bank.validated) {
                        bankTV.setText(StringUtil.valueOf(BankUtil.bankMap.get(bank.bank)));
                    } else {
                        bankTV.setText("");
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {

                }
            });
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || StringUtil.isBlank(nameET.getText().toString()) || StringUtil.isBlank(cardET.getText().toString()) || StringUtil.isBlank(reCardET.getText().toString()) || StringUtil.isBlank(jymmET.getText().toString()) || StringUtil.isBlank(bankTV.getText().toString().trim())) {
            setBtnClickable(false, sureBtn);
        } else {
            setBtnClickable(true, sureBtn);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_name:
                setFocusChange(hasFocus, getView(R.id.view_name));
                break;
            case R.id.et_card:
                setFocusChange(hasFocus, getView(R.id.view_card));
                break;
            case R.id.et_reCard:
                setFocusChange(hasFocus, getView(R.id.view_reCard));
                break;
            case R.id.et_jymm:
                setFocusChange(hasFocus, getView(R.id.view_jymm));
                break;
        }
    }

    private boolean isCanAdd() {
        String card = cardET.getText().toString().trim();
        String reCard = reCardET.getText().toString().trim();
        String jymm = jymmET.getText().toString().trim();
        if (jymm.length() < 8 || jymm.length() > 16 || !StringUtil.isLetterDigit(jymm)) {
            show("交易密码由8-16个字母或数字组成");
            return false;
        } else if (!card.equals(reCard)) {
            show("两次输入密码不一致");
            return false;
        }
        return true;
    }
}
