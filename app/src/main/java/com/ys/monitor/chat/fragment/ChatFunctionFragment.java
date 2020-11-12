package com.ys.monitor.chat.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcw.library.imagepicker.ImagePicker;
import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.chat.entity.MessageInfo;
import com.ys.monitor.chat.util.Constants;
import com.ys.monitor.chat.util.FileUtils;
import com.ys.monitor.chat.util.PhotoUtils;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.YS;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;


/**
 * 作者：Rance on 2016/12/13 16:01
 * 邮箱：rance935@163.com
 */
public class ChatFunctionFragment extends BaseFragment {
    private static final String TAG = "ChatFunctionFragment";
    private View rootView;
    private static final int CODE_TAKE_PHOTO = 0x111;
    private static final int CODE_CROP_PHOTO = 0xa2;
    private static final int REQUEST_CODE_PICK_IMAGE = 0xa3;
    private static final int REQUEST_CODE_PICK_FILE = 0xa4;
    private static final int CODE_REQUEST_CAMERA = 0xa5;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0xa6;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE = 0xa7;
    private static final int MY_PERMISSIONS_REQUEST_CAMERACODE = 0xa8;

    private int output_X = 480;
    private int output_Y = 480;
    //    private File output;
//    private Uri imageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo" +
            ".jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() +
            "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    TextView tvCapture, tvAlbum, tvContact, tvCloud, tvFile, tvLocation;
    private LinearLayout photoLL, cameraLL, voiceLL, videoLL;
    private String currentVideoName;
    private boolean isHide = false;//隐藏语音和视频通话

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
//            rootView = inflater.inflate(R.layout.fragment_chat_function, container, false);
//            findViewByIds();
//            setItemClick();
//        }
//        return rootView;
//    }

    @Override
    protected void init() {
        findViewByIds();
        setItemClick();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_chat_function;
    }

    private void findViewByIds() {
//        tvCapture = (TextView) rootView.findViewById(R.id.chat_function_capture);
//        tvAlbum = (TextView) rootView.findViewById(R.id.chat_function_album);
//        tvContact = (TextView) rootView.findViewById(R.id.chat_function_contact);
//        tvCloud = (TextView) rootView.findViewById(R.id.chat_function_cloud);
//        tvFile = (TextView) rootView.findViewById(R.id.chat_function_file);
//        tvLocation = (TextView) rootView.findViewById(R.id.chat_function_location);
        photoLL = getView(R.id.ll_photo);
        cameraLL = getView(R.id.ll_camera);
        voiceLL = getView(R.id.ll_voice);
        videoLL = getView(R.id.ll_video);
        setGone();

    }

    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(mActivity, "您已拒绝过一次", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERACODE);
        } else {//有权限直接调用系统相机拍照
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                imageUri = FileProvider.getUriForFile(mActivity,
                        FunctionApi.getAuthority(mActivity), fileUri);//通过FileProvider创建一个content
            // 类型的Uri
            PhotoUtils.takePicture(this, imageUri, CODE_TAKE_PHOTO);
        }
    }

    public static final int CAMERA = 888;

    public void setItemClick() {
        photoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionApi.takePicture(ChatFunctionFragment.this, 1, 2, true, false, false);
            }
        });
        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentVideoName = StringUtil.getNowTimeStr(3);
                FunctionApi.startVideo(ChatFunctionFragment.this, CAMERA, currentVideoName);
            }
        });
        voiceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.clickVoice();
                }
            }
        });
        videoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.clickVideo();
                }
            }
        });
//        tvCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                autoObtainCameraPermission();
//            }
//        });
//        tvAlbum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE);
//
//                } else {
//                    choosePhoto();
//                }
//            }
//        });

//        tvFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE);
//                } else {
//                    chooseFile();
//                }
//            }
//        });

//        tvCloud.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        tvLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        tvContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showContact();
//            }
//        });
    }

    private void showContact() {
//        Intent intent = new Intent(mActivity, ContactActivity.class);
//        startActivity(intent);
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mActivity, FunctionApi.getAuthority(mActivity),
                    fileUri);
            PhotoUtils.takePicture(this, imageUri, CODE_TAKE_PHOTO);
        }
    }

    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case CODE_TAKE_PHOTO:
                if (res == Activity.RESULT_OK) {
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setFilepath(fileUri.getAbsolutePath());
                    messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                    EventBus.getDefault().post(messageInfo);
                }

                break;
            case CODE_CROP_PHOTO:
                if (res == Activity.RESULT_OK) {
                    try {
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(cropImageUri.getPath());
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;
            case REQUEST_CODE_PICK_IMAGE:
                if (res == Activity.RESULT_OK) {
                    try {

                        Uri uri = data.getData();
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(getImageRealPathFromURI(uri));
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;
            case REQUEST_CODE_PICK_FILE:
                if (res == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        Log.e(TAG, "onActivityResult: ->" + uri.getPath());
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(FileUtils.getFileAbsolutePath(mActivity, uri));
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_FILE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }
                break;
            case YS.REQUEST_SELECT_IMAGES_CODE://相册图片选取返回
//                    ArrayList<String> images =
//                            (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
//                                    .REQUEST_OUTPUT);
                ArrayList<String> images =
                        data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    if (images != null && images.size() > 0) {
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(images.get(0));
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                        EventBus.getDefault().post(messageInfo);
                    }
                break;
            case CAMERA:
                if (res == Activity.RESULT_OK) {
                    L.e("视频录制成功");
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setFilepath(Constant.VIDEO_PATH + "/" + currentVideoName);
                    messageInfo.setFileType(Constants.CHAT_FILE_TYPE_VIDEO);
                    EventBus.getDefault().post(messageInfo);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    show("请同意系统权限后继续");
                }
                break;
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto();
                } else {
                    show("请同意系统权限后继续");
                }
                break;
            case MY_PERMISSIONS_REQUEST_CAMERACODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageUri = Uri.fromFile(fileUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        PhotoUtils.takePicture(this, imageUri, CODE_CROP_PHOTO);
                    }
                }
                break;
        }
    }

    public String getImageRealPathFromURI(Uri contentUri) {

        //TODO upload file、image、voice then return url;
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null,
                null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private ClickListener clickListener;

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void clickVoice();

        void clickVideo();
    }

    public void setGone() {
        if(isHide) {
            voiceLL.setVisibility(View.GONE);
            videoLL.setVisibility(View.GONE);
        }else {
            voiceLL.setVisibility(View.VISIBLE);
            videoLL.setVisibility(View.VISIBLE);
        }
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
