package com.ys.monitor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoActivity extends BaseActivity {
    public static final String KEY_DELETE_POSITION = "delete_position";
    private ViewPager pager;
    private SamplePagerAdapter adapter;
    private List<String> list;
    private int position;

    RelativeLayout photo_relativeLayout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public void initView() {
        setTransparent();
        list = getIntent().getStringArrayListExtra("list");
        position = getIntent().getIntExtra("position", 0);
        photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
        photo_relativeLayout.setBackgroundColor(0x70000000);

        Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
        photo_bt_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityUtil.finish(PhotoActivity.this);
            }
        });
        Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
        photo_bt_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(KEY_DELETE_POSITION, position);
                setResult(RESULT_OK, intent);
                ActivityUtil.finish(PhotoActivity.this);
            }
        });
        Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
        photo_bt_enter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ActivityUtil.finish(PhotoActivity.this);
            }
        });

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);

        adapter = new SamplePagerAdapter(mContext, list);// 构造adapter
        pager.setAdapter(adapter);// 设置适配器
        Intent intent = getIntent();
        pager.setCurrentItem(position);
    }

    @Override
    public void getData() {

    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数
            position = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    @Override
    public void onClick(View v) {

    }


    private class SamplePagerAdapter extends PagerAdapter {
        private List<String> imageUrl;
        private Context context;

        public SamplePagerAdapter(Context context, List<String> imageUrl) {
            this.context = context;
            this.imageUrl = imageUrl;
        }

        @Override
        public int getCount() {
            if (imageUrl != null && imageUrl.size() > 0) {
                return imageUrl.size();
            } else {
                return 0;
            }
            // return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(context).load(imageUrl.get(position)).into(photoView);
            // photoView.set

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    finish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public static void intentToPhotoActivity(Activity context, int requestCode,ArrayList<String> list, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putStringArrayListExtra("list", list);
        intent.putExtra("position", position);
        context.startActivityForResult(intent,requestCode);
    }
}
