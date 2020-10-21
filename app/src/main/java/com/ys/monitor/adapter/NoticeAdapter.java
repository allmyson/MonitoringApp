package com.ys.monitor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.bean.Msg;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

import java.util.List;

public class NoticeAdapter extends CommonAdapter<Msg> {
    public NoticeAdapter(Context context, List<Msg> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Msg item, int position) {
        helper.setText(R.id.tv_time, getTime(item.time));
        helper.setText(R.id.tv_title, StringUtil.valueOf(item.title));
        helper.setText(R.id.tv_content, StringUtil.valueOf(item.content));
//        helper.setImageResource(R.id.iv_, item.drawableId);
        if(position==0){
            helper.setImageResource(R.id.iv_, R.mipmap.notice_fire_check);
        }else if(position==1){
            helper.setImageResource(R.id.iv_, R.mipmap.notice_warning);
        }else if(position==2){
            helper.setImageResource(R.id.iv_, R.mipmap.notice_task);
        }else if(position==3){
            helper.setImageResource(R.id.iv_, R.mipmap.notice_command_center);
        }else {
            helper.setImageResource(R.id.iv_, item.drawableId);
        }
        int num = item.num;
        TextView numTV = helper.getView(R.id.tv_num);
        if (num == 0) {
            numTV.setVisibility(View.GONE);
        } else {
            numTV.setVisibility(View.VISIBLE);
        }
        if (num > 99) {
            numTV.setText("99+");
        } else {
            numTV.setText("" + num);
        }
    }

    public String getTime(long time) {
        if (time == 0) {
            return "";
        } else {
            if (DateUtil.isToday(time)) {
                return DateUtil.getLongHM(time);
//                return DateUtil.changeTimeToYMDHMS(StringUtil.valueOf(time));
            } else {
                return DateUtil.changeTimeToYMD(StringUtil.valueOf(time));

            }
        }
    }
}
