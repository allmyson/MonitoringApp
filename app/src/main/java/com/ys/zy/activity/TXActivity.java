package com.ys.zy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BankBean;
import com.ys.zy.dialog.BankFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.util.YS;

public class TXActivity extends BaseActivity {
    private ImageView bankIV;
    private TextView bankTV;
    private EditText moneyET;
    private TextView yueTV;
    private TextView allTxTV;
    private Button txBtn;
    private LinearLayout bankLL;
    private double money = 2000.98;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tx;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("提现");
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
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bank:
                DialogUtil.showBankDialog(mContext, new BankFragment.ClickListener() {
                    @Override
                    public void click(BankBean bankBean) {
                        bankIV.setImageResource(bankBean.drawableId);
                        bankTV.setText(bankBean.name + "（" + bankBean.number.substring(bankBean.number.length() - 4, bankBean.number.length()) + "）");
                        DialogUtil.removeDialog(mContext);
                    }
                });
                break;
            case R.id.tv_allTx:
                moneyET.setText("￥" + money);
                moneyET.setSelection(moneyET.getText().length());
                break;
            case R.id.btn_tx:
                break;
        }
    }
}
