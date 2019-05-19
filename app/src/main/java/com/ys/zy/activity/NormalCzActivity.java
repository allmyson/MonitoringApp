package com.ys.zy.activity;

import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.SpinnerAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.bean.CzBean;
import com.ys.zy.bean.PatformBankBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.ClipboardUtils;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;


public class NormalCzActivity extends BaseActivity {
    private Spinner spinner;
    private TextView khNameTV, bankNumTV;
    private EditText moneyET;//充值金额
    private EditText fkNameET;//付款人姓名
    private Button sureBtn;
    private String accountId;
    private TextView fzNameTV, fzNumTV;
    private ArrayAdapter<String> arrayAdapter;
    private String userId;
    private PatformBankBean currentBean;
    private List<String> list;
    private TextView smTV, contentTV;

    @Override
    public int getLayoutId() {
        return R.layout.activity_normal_cz;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        setBarColor("#ededed");
        titleView.setText("银行转账");
        smTV = getView(R.id.tv_sm);
        contentTV = getView(R.id.tv_content);
        setSM();
        spinner = getView(R.id.spinner_bank);
        khNameTV = getView(R.id.tv_khName);
        bankNumTV = getView(R.id.tv_bankNum);
        moneyET = getView(R.id.et_money);
        fkNameET = getView(R.id.et_fkhm);
        sureBtn = getView(R.id.btn_sure);
        sureBtn.setOnClickListener(this);
        fzNameTV = getView(R.id.tv_fzName);
        fzNumTV = getView(R.id.tv_fzNum);
        fzNameTV.setOnClickListener(this);
        fzNumTV.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PatformBankBean.DataBean dataBean = currentBean.data.get(position);
                if (dataBean != null) {
                    accountId = dataBean.accountId;
                    bankNumTV.setText(StringUtil.valueOf(dataBean.bankNumber));
                    khNameTV.setText(StringUtil.valueOf(dataBean.bankUser));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //适配器
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //设置样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        userId = UserSP.getUserId(mContext);
    }


    @Override
    public void getData() {
        HttpUtil.getPatformBankList(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                PatformBankBean patformBankBean = new Gson().fromJson(response.get(), PatformBankBean.class);
                if (patformBankBean != null) {
                    if (YS.SUCCESE.equals(patformBankBean.code) && patformBankBean.data != null && patformBankBean.data.size() > 0) {
                        currentBean = patformBankBean;
                        for (PatformBankBean.DataBean dataBean : patformBankBean.data) {
                            list.add(dataBean.bankName);
                        }
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                if (isCanCommit()) {
                    cz();
                }
                break;
            case R.id.tv_fzName:
                ClipboardUtils.copyText(mContext, khNameTV.getText().toString());
                show("已将内容复制到剪切板");
                break;
            case R.id.tv_fzNum:
                ClipboardUtils.copyText(mContext, bankNumTV.getText().toString());
                show("已将内容复制到剪切板");
                break;
        }
    }

    private boolean isCanCommit() {
        String skhm = khNameTV.getText().toString().trim();
        String skzh = bankNumTV.getText().toString().trim();
        String money = moneyET.getText().toString().trim();
//        String fkhm = fkNameET.getText().toString().trim();
        if (StringUtil.isBlank(accountId) || StringUtil.isBlank(skhm) || StringUtil.isBlank(skzh)) {
            show("收款银行不能为空");
            return false;
        } else if (StringUtil.isBlank(money)) {
            show("充值金额不能为空");
            return false;
        }
//        else if (StringUtil.isBlank(fkhm)) {
//            show("转账户名不能为空");
//            return false;
//        }
        return true;
    }

    private void cz() {
        String money = StringUtil.StringToDoubleStr(moneyET.getText().toString().trim());
        HttpUtil.cz(mContext, userId, money, accountId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        show("充值已提交");
                        finish();
                    } else {
                        show(StringUtil.valueOf(baseBean.msg));
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private void setSM() {
        String sm = String.format("(最低<font color=\"#dd2230\">%s</font>" + YS.UNIT + ",最高<font color=\"#dd2230\">%s</font>" + YS.UNIT + ")", 100, 49999);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }

        String content = "1.请使用网银/支付宝/微信/各银行APP/云闪付等转账\n" +
                "2.转账前核对收款卡号和户名\n" +
                "3.转账后再提交订单,否则无法及时入款,切勿重复提交订单,若有问题请及时沟通客服";
        contentTV.setText(content);
    }
}
