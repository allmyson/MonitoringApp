package com.ys.zy.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BankData;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.dialog.BankFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class TXActivity extends BaseActivity implements View.OnFocusChangeListener, TextWatcher {
    private ImageView bankIV;
    private TextView bankTV;
    private EditText moneyET, jymmET;
    private TextView yueTV;
    private TextView allTxTV;
    private Button txBtn;
    private LinearLayout bankLL;
    private String money = "0.00";
    private String userId;
    private String bankName;
    private String bankCard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tx;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("提现");
        jymmET = getView(R.id.et_jymm);
        jymmET.setOnFocusChangeListener(this);
        jymmET.addTextChangedListener(this);
        bankIV = getView(R.id.iv_bank);
        bankTV = getView(R.id.tv_bank);
        bankLL = getView(R.id.ll_bank);
        bankLL.setOnClickListener(this);
        moneyET = getView(R.id.et_money);
        moneyET.setSelection(moneyET.getText().length());
        yueTV = getView(R.id.tv_totalMoney);
        yueTV.setText("当前余额" + money + YS.UNIT + "，");
        allTxTV = getView(R.id.tv_allTx);
        allTxTV.setOnClickListener(this);
        txBtn = getView(R.id.btn_tx);
        txBtn.setOnClickListener(this);
        moneyET.setOnFocusChangeListener(this);
        moneyET.addTextChangedListener(this);
        userId = UserSP.getUserId(mContext);
        setBtnClickable(false, txBtn);
    }

    @Override
    public void getData() {
        HttpUtil.getBankList(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BankData bankData = new Gson().fromJson(response.get(), BankData.class);
                if (bankData != null) {
                    if (YS.SUCCESE.equals(bankData.code) && bankData.data != null && bankData.data.size() > 0) {
                        BankData.DataBean bankBean = bankData.data.get(0);
                        bankName = bankBean.bankName;
                        bankCard = bankBean.cardNumber;
                        bankTV.setText(bankBean.bankName + "（" + bankBean.cardNumber.substring(bankBean.cardNumber.length() - 4, bankBean.cardNumber.length()) + "）");
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });


        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                User user = new Gson().fromJson(response.get(), User.class);
                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
                    money = StringUtil.StringToDoubleStr(user.data.balance);
                    yueTV.setText("当前余额" + money + YS.UNIT + "，");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bank:
                DialogUtil.showBankDialog(mContext, new BankFragment.ClickListener() {
                    @Override
                    public void click(BankData.DataBean bankBean) {
                        bankName = bankBean.bankName;
                        bankCard = bankBean.cardNumber;
                        bankIV.setImageResource(R.mipmap.ic_bank_default);
                        bankTV.setText(bankBean.bankName + "（" + bankBean.cardNumber.substring(bankBean.cardNumber.length() - 4, bankBean.cardNumber.length()) + "）");
                        DialogUtil.removeDialog(mContext);
                    }
                });
                break;
            case R.id.tv_allTx:
                moneyET.setText(money);
                moneyET.setSelection(moneyET.getText().length());
                break;
            case R.id.btn_tx:
                KeyBoardUtils.closeKeybord(moneyET, mContext);
                if (isCanTX()) {
                    tx();
                }
                break;
        }
    }

    private void tx() {
        HttpUtil.tx(mContext, userId, moneyET.getText().toString().trim(), jymmET.getText().toString().trim(), bankCard, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        finish();
                    }
                    show(baseBean.msg);
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private boolean isCanTX() {
        String txMoney = moneyET.getText().toString().trim();
        String jymm = jymmET.getText().toString().trim();
        if (StringUtil.isBlank(bankCard)) {
            show("请选择提现银行卡");
            return false;
        } else if (StringUtil.isBlank(txMoney)) {
            show("请输入提现金额");
            return false;
        } else if (jymm.length() < 8 || jymm.length() > 16 || !StringUtil.isLetterDigit(jymm)) {
            show("交易密码由8-16个字母或数字组成");
            return false;
        } else if (StringUtil.StringToDouble(txMoney) > StringUtil.StringToDouble(money)) {
            show("余额不足");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_money:
                setFocusChange(hasFocus, getView(R.id.view_));
                break;
            case R.id.et_jymm:
                setFocusChange(hasFocus, getView(R.id.view_jymm));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || StringUtil.isBlank(moneyET.getText().toString().trim()) || StringUtil.isBlank(jymmET.getText().toString().trim())) {
            setBtnClickable(false, txBtn);
        } else {
            setBtnClickable(true, txBtn);
        }
    }
}
