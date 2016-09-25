package com.ycss.j.bitmap;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/21 0021.
 */
public class GlideConfig {
    private Integer placeHolderResId; //默认占位资源
    private Integer errorResId; //错误时显示的资源
    private boolean crossFade; //是否淡入淡出动画
    private int crossDuration; //淡入淡出动画持续的时间
    private OverrideSize size; //图片最终显示在ImageView上的宽高度像素
    private int CropType = com.ycss.j.bitmap.CropType.CENTER_CROP; //裁剪类型,默认为中部裁剪
    private boolean asGif; //true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
    private boolean asBitmap;//true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
    private boolean skipMemoryCache;//true,跳过内存缓存,默认false
    /**
     * DiskCacheStrategy.NONE       跳过硬盘缓存
     * DiskCacheStrategy.SOURCE     仅仅保存原始分辨率的图片
     * DiskCacheStrategy.RESULT     仅仅缓存最终分辨率的图像(降低分辨率后的或者转换后的)
     * DiskCacheStrategy.ALL        缓存所有版本的图片,默认行为
     */
    private DiskCacheStrategy diskCache; //硬盘缓存,默认为all类型
    /**
     * Priority.LOW
     * Priority.NORMAL
     * Priority.HIGH
     * Priority.IMMEDIATE)
     */
    private Priority priority;
    private float thumbnail;//设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
    private String thumbnailUrl;//设置缩略图的url,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
    private SimpleTarget<Bitmap> simpleTarget; //指定simpleTarget对象,可以在Target回调方法中处理bitmap,同时该构造方法中还可以指定size
    private ViewTarget<? extends View, GlideDrawable> viewTarget;//指定viewTarget对象,可以是自定义View,该构造方法传入的view和通配符的view是同一个
    private NotificationTarget notificationTarget; //设置通知栏加载大图片的target;
    private AppWidgetTarget appWidgetTarget;//设置加载小部件图片的target
    private Integer animResId;//图片加载完后的动画效果,在异步加载资源完成时会执行该动画。
    private ViewPropertyAnimation.Animator animator; //在异步加载资源完成时会执行该动画。可以接受一个Animator对象
    private boolean cropCircle;//圆形裁剪
    private boolean roundedCorners;//圆角处理
    private boolean grayscale;//灰度处理
    private boolean blur;//高斯模糊处理
    private boolean rotate;//旋转图片
    private int rotateDegree;//默认旋转°
    private String tag; //唯一标识


    private GlideConfig(IBuilder builder){
        this.placeHolderResId = builder.placeHolderResId;
        this.errorResId = builder.errorResId;
        this.crossFade = builder.crossFade;
        this.crossDuration = builder.crossDuration;
        this.size = builder.size;
        this.CropType = builder.CropType;
        this.asGif = builder.asGif;
        this.asBitmap = builder.asBitmap;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.diskCache = builder.diskCache;
        this.thumbnail = builder.thumbnail;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.simpleTarget = builder.simpleTarget;
        this.viewTarget = builder.viewTarget;
        this.notificationTarget = builder.notificationTarget;
        this.appWidgetTarget = builder.appWidgetTarget;
        this.animResId = builder.animResId;
        this.animator = builder.animator;
        this.priority = builder.priority;
        this.blur = builder.blur;
        this.cropCircle = builder.cropCircle;
        this.roundedCorners = builder.roundedCorners;
        this.grayscale = builder.grayscale;
        this.rotate =builder.rotate;
        this.rotateDegree = builder.rotateDegree;
        this.tag = builder.tag;
    }



    public static class IBuilder{
        public Integer placeHolderResId; //默认占位资源
        public Integer errorResId; //错误时显示的资源
        public boolean crossFade; //是否淡入淡出动画
        public int crossDuration; //淡入淡出动画持续的时间
        public OverrideSize size; //图片最终显示在ImageView上的宽高度像素
        public int CropType = com.ycss.j.bitmap.CropType.CENTER_CROP; //裁剪类型,默认为中部裁剪
        public boolean asGif; //true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
        public boolean asBitmap;//true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
        public boolean skipMemoryCache;//true,跳过内存缓存,默认false
        /**
         * DiskCacheStrategy.NONE       跳过硬盘缓存
         * DiskCacheStrategy.SOURCE)    仅仅保存原始分辨率的图片
         * DiskCacheStrategy.RESULT     仅仅缓存最终分辨率的图像(降低分辨率后的或者转换后的)
         * DiskCacheStrategy.ALL        缓存所有版本的图片,默认行为
         */
        public DiskCacheStrategy diskCache; //硬盘缓存,默认为all类型
        /**
         * Priority.LOW
         * Priority.NORMAL
         * Priority.HIGH
         * Priority.IMMEDIATE)
         */
        public Priority priority;
        public float thumbnail;//设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
        public String thumbnailUrl;//设置缩略图的url,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
        public SimpleTarget<Bitmap> simpleTarget; //指定simpleTarget对象,可以在Target回调方法中处理bitmap,同时该构造方法中还可以指定size
        public ViewTarget<? extends View, GlideDrawable> viewTarget;//指定viewTarget对象,可以是自定义View,该构造方法传入的view和通配符的view是同一个
        public NotificationTarget notificationTarget; //设置通知栏加载大图片的target;
        public AppWidgetTarget appWidgetTarget;//设置加载小部件图片的target
        public Integer animResId;//图片加载完后的动画效果,在异步加载资源完成时会执行该动画。
        public ViewPropertyAnimation.Animator animator; //在异步加载资源完成时会执行该动画。可以接受一个Animator对象
        public boolean cropCircle;//圆形裁剪
        public boolean roundedCorners;//圆角处理
        public boolean grayscale;//灰度处理
        public boolean blur;//高斯模糊处理
        public boolean rotate;//旋转图片
        public int rotateDegree;//默认旋转°
        public String tag; //唯一标识
        public IBuilder setPlaceHolderResId(Integer placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public IBuilder setErrorResId(Integer errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public IBuilder setCrossFade(boolean crossFade) {
            this.crossFade = crossFade;
            return this;
        }

        public IBuilder setCrossDuration(int crossDuration) {
            this.crossDuration = crossDuration;
            return this;
        }

        public IBuilder setSize(OverrideSize size) {
            this.size = size;
            return this;
        }

        public IBuilder setCropType(int cropType) {
            CropType = cropType;
            return this;
        }

        public IBuilder setAsGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public IBuilder setAsBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public IBuilder setSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public IBuilder setDiskCache(DiskCacheStrategy diskCacheStrategy) {
            this.diskCache = diskCacheStrategy;
            return this;
        }

        public IBuilder setPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public IBuilder setThumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public IBuilder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public IBuilder setSimpleTarget(SimpleTarget<Bitmap> simpleTarget) {
            this.simpleTarget = simpleTarget;
            return this;
        }

        public IBuilder setViewTarget(ViewTarget<? extends View, GlideDrawable> viewTarget) {
            this.viewTarget = viewTarget;
            return this;
        }

        public IBuilder setNotificationTarget(NotificationTarget notificationTarget) {
            this.notificationTarget = notificationTarget;
            return this;
        }

        public IBuilder setAppWidgetTarget(AppWidgetTarget appWidgetTarget) {
            this.appWidgetTarget = appWidgetTarget;
            return this;
        }

        public IBuilder setAnimResId(Integer animResId) {
            this.animResId = animResId;
            return this;
        }

        public IBuilder setAnimator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public IBuilder setCropCircle(boolean cropCircle) {
            this.cropCircle = cropCircle;
            return this;
        }

        public IBuilder setRoundedCorners(boolean roundedCorners) {
            this.roundedCorners = roundedCorners;
            return this;
        }

        public IBuilder setGrayscale(boolean grayscale) {
            this.grayscale = grayscale;
            return this;
        }

        public IBuilder setBlur(boolean blur) {
            this.blur = blur;
            return this;
        }

        public IBuilder setRotate(boolean rotate) {
            this.rotate = rotate;
            return this;
        }

        public IBuilder setRotateDegree(int rotateDegree) {
            this.rotateDegree = rotateDegree;
            return this;
        }

        public IBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }
        public GlideConfig build() {
            return new GlideConfig(this);
        }
    }
    public static IBuilder parseBuilder(GlideConfig config) {
        IBuilder builder = new IBuilder();
        builder.placeHolderResId = config.placeHolderResId;
        builder.errorResId = config.errorResId;
        builder.crossFade = config.crossFade;
        builder.crossDuration = config.crossDuration;
        builder.size = config.size;
        builder.CropType = config.CropType;
        builder.asGif = config.asGif;
        builder.asBitmap = config.asBitmap;
        builder.skipMemoryCache = config.skipMemoryCache;
        builder.diskCache = config.diskCache;
        builder.thumbnail = config.thumbnail;
        builder.thumbnailUrl = config.thumbnailUrl;
        builder.simpleTarget = config.simpleTarget;
        builder.viewTarget = config.viewTarget;
        builder.notificationTarget = config.notificationTarget;
        builder.appWidgetTarget = config.appWidgetTarget;
        builder.animResId = config.animResId;
        builder.animator = config.animator;
        builder.priority = config.priority;
        builder.cropCircle = config.cropCircle;
        builder.roundedCorners = config.roundedCorners;
        builder.grayscale = config.grayscale;
        builder.blur = config.blur;
        builder.rotate = config.rotate;
        builder.rotateDegree =config.rotateDegree;
        builder.tag = config.tag;
        return builder;
    }
    public Integer getPlaceHolderResId() {
        return placeHolderResId;
    }

    public Integer getErrorResId() {
        return errorResId;
    }

    public boolean isCrossFade() {
        return crossFade;
    }

    public int getCrossDuration() {
        return crossDuration;
    }

    public OverrideSize getSize() {
        return size;
    }

    public int getCropType() {
        return CropType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCacheStrategy getDiskCache() {
        return diskCache;
    }

    public Priority getPriority() {
        return priority;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public SimpleTarget<Bitmap> getSimpleTarget() {
        return simpleTarget;
    }

    public ViewTarget<? extends View, GlideDrawable> getViewTarget() {
        return viewTarget;
    }

    public NotificationTarget getNotificationTarget() {
        return notificationTarget;
    }

    public AppWidgetTarget getAppWidgetTarget() {
        return appWidgetTarget;
    }

    public Integer getAnimResId() {
        return animResId;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public boolean isCropCircle() {
        return cropCircle;
    }

    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public boolean isBlur() {
        return blur;
    }

    public boolean isRotate() {
        return rotate;
    }

    public int getRotateDegree() {
        return rotateDegree;
    }

    public String getTag() {
        return tag;
    }


}
