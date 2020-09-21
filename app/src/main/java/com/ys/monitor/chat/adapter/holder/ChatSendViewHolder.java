package com.ys.monitor.chat.adapter.holder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.monitor.R;
import com.ys.monitor.chat.adapter.ChatAdapter;
import com.ys.monitor.chat.entity.IMContact;
import com.ys.monitor.chat.entity.Link;
import com.ys.monitor.chat.entity.MessageInfo;
import com.ys.monitor.chat.util.Constants;
import com.ys.monitor.chat.util.FileUtils;
import com.ys.monitor.chat.util.Utils;
import com.ys.monitor.chat.widget.BubbleImageView;
import com.ys.monitor.chat.widget.BubbleLinearLayout;
import com.ys.monitor.chat.widget.GifTextView;
import com.ys.monitor.util.DateUtil;
import com.ys.monitor.util.StringUtil;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatSendViewHolder extends BaseViewHolder<MessageInfo> {
    private static final String TAG = "ChatSendViewHolder";
    TextView chatItemDate;
    ImageView chatItemHeader;
    GifTextView chatItemContentText;
    BubbleImageView chatItemContentImage;
    ImageView chatItemFail;
    ProgressBar chatItemProgress;
    ImageView chatItemVoice;
    BubbleLinearLayout chatItemLayoutContent;
    TextView chatItemVoiceTime;
    BubbleLinearLayout chatItemLayoutFile;
    ImageView ivFileType;
    TextView tvFileName;
    TextView tvFileSize;

    BubbleLinearLayout chatItemLayoutContact;
    TextView tvContactSurname;
    TextView tvContactName;
    TextView tvContactPhone;

    BubbleLinearLayout chatItemLayoutLink;
    TextView tvLinkSubject;
    TextView tvLinkText;
    ImageView ivLinkPicture;
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;
    private RelativeLayout.LayoutParams layoutParams;
    private Context mContext;

    private RelativeLayout videoRL;
    private BubbleImageView videoIV;


    public ChatSendViewHolder(ViewGroup parent,
                              ChatAdapter.onItemClickListener onItemClickListener,
                              Handler handler) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent,
                false));
        findViewByIds(itemView);
        setItemLongClick();
        setItemClick();
        this.mContext = parent.getContext();
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
        layoutParams = (RelativeLayout.LayoutParams) chatItemLayoutContent.getLayoutParams();
    }

    private void findViewByIds(View itemView) {
        chatItemDate = (TextView) itemView.findViewById(R.id.chat_item_date);
        chatItemHeader = (ImageView) itemView.findViewById(R.id.chat_item_header);
        chatItemContentText = (GifTextView) itemView.findViewById(R.id.chat_item_content_text);
        chatItemFail = (ImageView) itemView.findViewById(R.id.chat_item_fail);
        chatItemProgress = (ProgressBar) itemView.findViewById(R.id.chat_item_progress);
        chatItemContentImage =
                (BubbleImageView) itemView.findViewById(R.id.chat_item_content_image);
        chatItemVoice = (ImageView) itemView.findViewById(R.id.chat_item_voice);
        chatItemLayoutContent =
                (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_layout_content);
        chatItemVoiceTime = (TextView) itemView.findViewById(R.id.chat_item_voice_time);
        chatItemLayoutFile = (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_layout_file);
        ivFileType = (ImageView) itemView.findViewById(R.id.iv_file_type);
        tvFileName = (TextView) itemView.findViewById(R.id.tv_file_name);
        tvFileSize = (TextView) itemView.findViewById(R.id.tv_file_size);
        chatItemLayoutContact =
                (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_layout_contact);
        tvContactSurname = (TextView) itemView.findViewById(R.id.tv_contact_surname);
        tvContactPhone = (TextView) itemView.findViewById(R.id.tv_contact_phone);
        chatItemLayoutLink = (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_layout_link);
        tvLinkSubject = (TextView) itemView.findViewById(R.id.tv_link_subject);
        tvLinkText = (TextView) itemView.findViewById(R.id.tv_link_text);
        ivLinkPicture = (ImageView) itemView.findViewById(R.id.iv_link_picture);

        videoRL = (RelativeLayout) itemView.findViewById(R.id.chat_item_layout_video);
        videoIV = (BubbleImageView) itemView.findViewById(R.id.chat_item_video);
    }


    @Override
    public void setData(MessageInfo data) {
        chatItemDate.setText(DateUtil.changeTimeToYMD(StringUtil.valueOf(data.getTime())));
        Glide.with(mContext).load(data.getHeader()).placeholder(R.mipmap.bg_default_head2).error(R.mipmap.bg_default_head2).into(chatItemHeader);
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick((Integer) itemView.getTag());
            }
        });
        switch (data.getFileType()) {
            case Constants.CHAT_FILE_TYPE_TEXT:
                chatItemContentText.setSpanText(handler, data.getContent(), true);
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemContentImage.setVisibility(View.GONE);
                chatItemLayoutFile.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);
                chatItemLayoutLink.setVisibility(View.GONE);

                chatItemContentText.setVisibility(View.VISIBLE);
                chatItemLayoutContent.setVisibility(View.VISIBLE);
                TextPaint paint = chatItemContentText.getPaint();
                paint.setColor(ContextCompat.getColor(mContext, R.color.chat_send_text));
                // 计算textview在屏幕上占多宽
                int len = (int) paint.measureText(chatItemContentText.getText().toString().trim());
                if (len < Utils.dp2px(mContext, 200)) {
                    layoutParams.width = len + Utils.dp2px(mContext, 30);
                } else {
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                }
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                chatItemLayoutContent.setLayoutParams(layoutParams);
                break;
            case Constants.CHAT_FILE_TYPE_IMAGE:
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemLayoutContent.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemLayoutFile.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);
                chatItemLayoutLink.setVisibility(View.GONE);

                chatItemContentImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.getFilepath()).into(chatItemContentImage);
                layoutParams.width = Utils.dp2px(mContext, 120);
                layoutParams.height = Utils.dp2px(mContext, 48);
                chatItemLayoutContent.setLayoutParams(layoutParams);
                break;
            case Constants.CHAT_FILE_TYPE_VOICE:
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.VISIBLE);
                chatItemLayoutContent.setVisibility(View.VISIBLE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.VISIBLE);
                chatItemContentImage.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);
                chatItemLayoutLink.setVisibility(View.GONE);

                chatItemLayoutFile.setVisibility(View.GONE);
                chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
                layoutParams.width = Utils.dp2px(mContext, 120);
                layoutParams.height = Utils.dp2px(mContext, 48);
                chatItemLayoutContent.setLayoutParams(layoutParams);
                break;
            case Constants.CHAT_FILE_TYPE_FILE:
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemContentImage.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemLayoutContent.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);
                chatItemLayoutLink.setVisibility(View.GONE);

                chatItemLayoutFile.setVisibility(View.VISIBLE);
                tvFileName.setText(FileUtils.getFileName(data.getFilepath()));
                try {
                    tvFileSize.setText(FileUtils.getFileSize(data.getFilepath()));
                    switch (FileUtils.getExtensionName(data.getFilepath())) {
                        case "doc":
                        case "docx":
                            ivFileType.setImageResource(R.mipmap.icon_file_word);
                            break;
                        case "ppt":
                        case "pptx":
                            ivFileType.setImageResource(R.mipmap.icon_file_ppt);
                            break;
                        case "xls":
                        case "xlsx":
                            ivFileType.setImageResource(R.mipmap.icon_file_excel);
                            break;
                        case "pdf":
                            ivFileType.setImageResource(R.mipmap.icon_file_pdf);
                            break;
                        default:
                            ivFileType.setImageResource(R.mipmap.icon_file_other);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.CHAT_FILE_TYPE_CONTACT:
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemContentImage.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemLayoutContent.setVisibility(View.GONE);
                chatItemLayoutFile.setVisibility(View.GONE);

                chatItemLayoutContact.setVisibility(View.VISIBLE);

                IMContact imContact = (IMContact) data.getObject();
                tvContactSurname.setText(imContact.getSurname());
                tvContactName.setText(imContact.getName());
                tvContactPhone.setText(imContact.getPhonenumber());
                break;
            case Constants.CHAT_FILE_TYPE_LINK:
                videoRL.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemContentImage.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemLayoutContent.setVisibility(View.GONE);
                chatItemLayoutFile.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);

                chatItemLayoutLink.setVisibility(View.VISIBLE);
                Link link = (Link) data.getObject();

                tvLinkSubject.setText(link.getSubject());
                tvLinkText.setText(link.getText());
                Glide.with(mContext).load(link.getStream()).into(ivLinkPicture);
                break;
            case Constants.CHAT_FILE_TYPE_VIDEO:
                chatItemContentImage.setVisibility(View.GONE);
                chatItemVoice.setVisibility(View.GONE);
                chatItemLayoutContent.setVisibility(View.GONE);
                chatItemVoiceTime.setVisibility(View.GONE);
                chatItemContentText.setVisibility(View.GONE);
                chatItemLayoutFile.setVisibility(View.GONE);
                chatItemLayoutContact.setVisibility(View.GONE);
                chatItemLayoutLink.setVisibility(View.GONE);

                videoRL.setVisibility(View.VISIBLE);
//                Glide.with(mContext).load(StringUtil.getVideoThumb(data.getFilepath())).into
//                (videoIV);
                String path = data.getFilepath();
                if (!StringUtil.isBlank(path)) {
                    if (path.startsWith("http://") || path.startsWith("https//")) {
                        videoIV.setImageBitmap(StringUtil.getNetVideoBitmap(data.getFilepath()));
                    } else {
                        videoIV.setImageBitmap(StringUtil.getVideoThumb(data.getFilepath()));
                    }
                }
                layoutParams.width = Utils.dp2px(mContext, 120);
                layoutParams.height = Utils.dp2px(mContext, 48);
                chatItemLayoutContent.setLayoutParams(layoutParams);
                break;
        }
        switch (data.getSendState()) {
            case Constants.CHAT_ITEM_SENDING:
                chatItemProgress.setVisibility(View.VISIBLE);
                chatItemFail.setVisibility(View.GONE);
                break;
            case Constants.CHAT_ITEM_SEND_ERROR:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_SUCCESS:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.GONE);
                break;
        }
    }

    public void setItemLongClick() {
        chatItemContentImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickImage(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemContentText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickText(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemLayoutContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickItem(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemLayoutFile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickFile(v, (Integer) itemView.getTag());
                return true;
            }
        });
    }

    public void setItemClick() {
        chatItemFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.reSend(chatItemFail, (Integer) itemView.getTag());
            }
        });
        chatItemContentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onImageClick(chatItemContentImage, (Integer) itemView.getTag());
            }
        });

        chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatItemVoice.getVisibility() == View.VISIBLE) {
                    onItemClickListener.onVoiceClick(chatItemVoice, (Integer) itemView.getTag());
                }
            }
        });

        chatItemLayoutFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onFileClick(v, (Integer) itemView.getTag());
            }
        });

        chatItemLayoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onLinkClick(v, (Integer) itemView.getTag());
            }
        });
        videoRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onVideoClick(v, (Integer) itemView.getTag());
            }
        });
    }
}
