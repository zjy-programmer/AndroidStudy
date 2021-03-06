package com.example.androidstudy.any.imagepicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.androidstudy.R;
import com.example.baselibrary.MyApplication;
import com.ypx.imagepicker.adapter.PickerItemAdapter;
import com.ypx.imagepicker.bean.selectconfig.BaseSelectConfig;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.data.ProgressSceneEnum;
import com.ypx.imagepicker.data.ICameraExecutor;
import com.ypx.imagepicker.data.IReloadExecutor;
import com.ypx.imagepicker.views.PickerUiConfig;
import com.ypx.imagepicker.presenter.IPickerPresenter;
import com.ypx.imagepicker.views.PickerUiProvider;
import com.ypx.imagepicker.views.base.PickerControllerView;
import com.ypx.imagepicker.views.base.PickerFolderItemView;
import com.ypx.imagepicker.views.base.PickerItemView;
import com.ypx.imagepicker.views.base.PreviewControllerView;
import com.ypx.imagepicker.views.base.SingleCropControllerView;
import com.ypx.imagepicker.views.wx.WXFolderItemView;
import com.ypx.imagepicker.views.wx.WXTitleBar;

import java.util.ArrayList;


public class KrImgPickerPresenter implements IPickerPresenter {

    @Override
    public void displayImage(View view, ImageItem item, int size, boolean isThumbnail) {
        Object object = item.getUri() != null ? item.getUri() : item.path;
        Glide.with(view.getContext()).load(object).apply(new RequestOptions()
                .format(isThumbnail ? DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888))
                .override(isThumbnail ? size : Target.SIZE_ORIGINAL)
                .into((ImageView) view);
    }

    @NonNull
    @Override
    public PickerUiConfig getUiConfig(@Nullable Context context) {
        PickerUiConfig uiConfig = new PickerUiConfig();
        uiConfig.setShowStatusBar(true);
        uiConfig.setThemeColor(Color.parseColor("#859D7B"));
        uiConfig.setStatusBarColor(Color.parseColor("#F5F5F5"));
        uiConfig.setPickerBackgroundColor(Color.WHITE);
        uiConfig.setFolderListOpenDirection(PickerUiConfig.DIRECTION_TOP);
        uiConfig.setFolderListOpenMaxMargin(0);

        uiConfig.setPickerUiProvider(new PickerUiProvider() {
            @Override
            public PickerControllerView getTitleBar(Context context) {
                WXTitleBar titleBar = (WXTitleBar) super.getTitleBar(context);
                titleBar.setCompleteText("?????????");
                titleBar.setCompleteBackground(null, null);
                titleBar.setCompleteTextColor(Color.parseColor("#859D7B"),
                        Color.parseColor("#50859D7B"));
                titleBar.centerTitle();
                titleBar.setShowArrow(true);
                titleBar.setCanToggleFolderList(true);
                titleBar.setBackIconID(R.mipmap.picker_icon_close_black);
                return titleBar;
            }

            @Override
            public PickerItemView getItemView(Context context) {
                return new KrPickerItem(context);
            }

            @Override
            public PickerControllerView getBottomBar(Context context) {
                return null;
            }

            @Override
            public PreviewControllerView getPreviewControllerView(Context context) {
                return new KrPreviewControllerView(context);
            }

            @Override
            public SingleCropControllerView getSingleCropControllerView(Context context) {
                return new KrCropControllerView(context);
            }

            @Override
            public PickerFolderItemView getFolderItemView(Context context) {
                WXFolderItemView itemView = (WXFolderItemView) super.getFolderItemView(context);
                itemView.setIndicatorColor(Color.parseColor("#859D7B"));
                return itemView;
            }
        });
        return uiConfig;
    }

    @Override
    public void tip(Context context, String msg) {
    }

    @Override
    public void overMaxCountTip(Context context, int maxCount) {
    }

    @Override
    public DialogInterface showProgressDialog(@Nullable Activity activity, ProgressSceneEnum progressSceneEnum) {
        return ProgressDialog.show(activity, null, progressSceneEnum == ProgressSceneEnum.crop ? "????????????..." : "????????????...");
    }

    @Override
    public boolean interceptPickerCompleteClick(@Nullable Activity activity, ArrayList<ImageItem> selectedList, BaseSelectConfig selectConfig) {
        return false;
    }

    @Override
    public boolean interceptPickerCancel(@Nullable Activity activity, ArrayList<ImageItem> selectedList) {
        return false;
    }

    /**
     * <p>
     * ???????????????????????????????????????true??????????????????????????????????????????????????????????????????????????????
     * ????????????????????????
     * <p>
     * adapter.preformCheckItem()
     * <p>
     * <p>
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @param activity        ?????????
     * @param imageItem       ????????????
     * @param selectImageList ??????????????????
     * @param allSetImageList ???????????????????????????
     * @param selectConfig    ????????????????????????????????????????????????selectConfig?????????MultiSelectConfig
     *                        ?????????????????????????????????????????????CropSelectConfig
     * @param adapter         ??????????????????????????????????????????
     * @param isClickCheckBox ????????????item?????????????????????
     * @param reloadExecutor  ?????????
     * @return ????????????
     */
    @Override
    public boolean interceptItemClick(@Nullable Activity activity, ImageItem imageItem, ArrayList<ImageItem> selectImageList, ArrayList<ImageItem> allSetImageList, BaseSelectConfig selectConfig, PickerItemAdapter adapter,boolean isClickCheckBox, @Nullable IReloadExecutor reloadExecutor) {
        if (selectImageList != null && selectImageList.size() == selectConfig.getMaxCount() && !selectImageList.contains(imageItem)) {
            Toast.makeText(MyApplication.getInstance(), "???????????????", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean interceptCameraClick(@Nullable Activity activity, ICameraExecutor takePhoto) {
        return false;
    }
}
