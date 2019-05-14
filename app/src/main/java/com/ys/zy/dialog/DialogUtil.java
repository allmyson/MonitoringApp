package com.ys.zy.dialog;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.Calendar;
import java.util.List;

public class DialogUtil {
    /**
     * dialog tag
     */
    private static String mDialogTag = "dialog";


    /**
     * 描述：移除Fragment.
     *
     * @param context the context
     */
    public static void removeDialog(final Context context) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    FragmentActivity activity = (FragmentActivity) context;
                    FragmentTransaction ft = activity
                            .getSupportFragmentManager().beginTransaction();
                    // 指定一个系统转场动画
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    Fragment prev = activity.getSupportFragmentManager()
                            .findFragmentByTag(mDialogTag);
                    if (prev != null) {
                        ft.remove(prev);
                    }
//					ft.addToBackStack(null);
                    ft.commit();
                } catch (Exception e) {
                    // 可能有Activity已经被销毁的异常
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从父亲布局中移除自己
     *
     * @param v
     */
    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(v);
            }
        }
    }


    /**
     * 显示日期选择框
     *
     * @param context
     * @param listener
     */
    public static void showDateDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();
    }

    public static void showDateDialogT(Context context, long minTime, long maxTime, DatePickerDialog
            .OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, monthOfYear, dayOfMonth);
        if (minTime != 0)
            datePickerDialog.getDatePicker().setMinDate(minTime);
        if (maxTime != 0)
            datePickerDialog.getDatePicker().setMaxDate(maxTime);
        datePickerDialog.show();
    }

    /**
     * 显示时分
     *
     * @param context
     * @param listener
     */
    public static void showDateDialogSF(Context context, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int dayOfMonth = calendar.get(Calendar.MONTH) + 1;

        TimePickerDialog datePickerDialog = new TimePickerDialog(context, listener, hour, dayOfMonth, true);
        datePickerDialog.show();
    }

    /**
     * 弹出确认框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static ShareFragment showShare(Context context, ShareFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        ShareFragment newFragment = ShareFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 弹出确认框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static TipFragment showTip(Context context, String content, TipFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        TipFragment newFragment = TipFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        newFragment.setContent(content);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    public static TipFragment showTip(Context context, String title,String content,String sureText, TipFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        TipFragment newFragment = TipFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        newFragment.setTitle(title);
        newFragment.setSureText(sureText);
        newFragment.setContent(content);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 弹出确认框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static TZTipFragment showTZTip(Context context,String game,String gameNo,String money, String content, TZTipFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        TZTipFragment newFragment = TZTipFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        newFragment.setGame(game);
        newFragment.setGameNo(gameNo);
        newFragment.setMoney(money);
        newFragment.setContent(content);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }
    /**
     * 弹出退出确认框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static ExitFragment showExit(Context context, ExitFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        ExitFragment newFragment = ExitFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }
    /**
     * 弹出游戏框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static GameFragment showGameFragment(Context context, GameFragment.ClickListener clickListener, DialogInterface.OnCancelListener onCancelListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        GameFragment newFragment = GameFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        newFragment.setOnCancelListener(onCancelListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 选择银行卡提现列表
     * @param context
     * @param clickListener
     * @return
     */
    public static BankFragment showBankDialog(Context context,BankFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        BankFragment newFragment = BankFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }


    /**
     * 玩法说明
     * @param context
     * @param
     * @return
     */
    public static SmFragment showSmDialog(Context context,String content,DialogInterface.OnCancelListener onCancelListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        SmFragment newFragment = SmFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setContent(content);
        newFragment.setOnCancelListener(onCancelListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }


    /**
     * 玩法
     * @param context
     * @param
     * @return
     */
    public static PlayFragment showPlayDialog(Context context, List<String> list, PlayFragment.ClickListener clickListener,DialogInterface.OnCancelListener onCancelListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        PlayFragment newFragment = PlayFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setList(list);
        newFragment.setClickListener(clickListener);
        newFragment.setOnCancelListener(onCancelListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 弹出确认框
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static CzFragment showCZ(Context context, CzFragment.ClickListener clickListener) {
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        CzFragment newFragment = CzFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setClickListener(clickListener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        newFragment.show(ft, mDialogTag);//Can not perform this action after onSaveInstanceState
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        return newFragment;
    }
}
