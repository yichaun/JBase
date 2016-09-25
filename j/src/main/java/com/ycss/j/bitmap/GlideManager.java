package com.ycss.j.bitmap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.ycss.j.R;
import com.ycss.j.http.HttpManager;
import com.ycss.j.common.utils.L;

import java.io.File;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/21 0021.
 */
public class GlideManager {

    private volatile static GlideManager instance;

    //默认配置
    public static GlideConfig defConfig = new GlideConfig.IBuilder().
            setCropType(CropType.CENTER_CROP).
            setAsBitmap(true).
            setPlaceHolderResId(R.mipmap.def_img).
            setErrorResId(R.mipmap.def_img).
            setDiskCache(DiskCacheStrategy.SOURCE).
            setPriority(Priority.HIGH).build();


    public static void initDefConfig(GlideConfig config) {
        defConfig = config;
    }

    public static GlideManager getInstance() {
        if (null == instance) {
            synchronized (HttpManager.class) {
                if (null == instance) {
                    instance = new GlideManager();
                }
            }
        }
        return instance;
    }

    public static <T, K> void load(T context, K load, ImageView iv, GlideConfig config, LoaderListener listener, BitmapLoadListener bitmapLoadListener) {
        internalLoad(context, load, iv, config, listener, bitmapLoadListener);
    }

    public static <T, K> void load(T context, K load, ImageView iv, GlideConfig config, LoaderListener listener) {
        internalLoad(context, load, iv, config, listener, null);
    }

    public static <T, K> void load(T context, K load, ImageView iv, GlideConfig config, BitmapLoadListener listener) {
        internalLoad(context, load, iv, config, null, listener);
    }

    public static <T, K> void load(T context, K load, ImageView iv, GlideConfig config) {
        internalLoad(context, load, iv, config, null, null);
    }

    public static <T, K> void load(T context, K load, ImageView iv, LoaderListener listener, BitmapLoadListener bitmapLoadListener) {
        internalLoad(context, load, iv, defConfig, listener, bitmapLoadListener);
    }

    public static <T, K> void load(T context, K load, ImageView iv, LoaderListener listener) {
        internalLoad(context, load, iv, defConfig, listener, null);
    }

    public static <T, K> void load(T context, K load, ImageView iv, BitmapLoadListener bitmapLoadListener) {
        internalLoad(context, load, iv, defConfig, null, bitmapLoadListener);
    }

    public static <T, K> void load(T context, K load, ImageView iv) {
        internalLoad(context, load, iv, defConfig, null, null);
    }


    private static <T, K> Target internalLoad(T context, K load, ImageView view, GlideConfig config, final LoaderListener listener, final BitmapLoadListener bitmapLoadingListener) {
        DrawableTypeRequest<K> type = null;
        GenericRequestBuilder builder = null;
        RequestManager requestManager = null;
        Target target = null;
        //判断加载的文件类型
        if (!((load instanceof String) || (load instanceof Integer) || (load instanceof File) || (load instanceof Uri))) {
            throw new RecyclableBufferedInputStream.InvalidMarkException("load type error");
        }
        requestManager = initRequestManager(context);
        type = requestManager.load(load);
        try {
            if (config.getCropType() == CropType.CENTER_CROP) {
                type.centerCrop();
            } else {
                type.fitCenter();
            }
            //是否gif
            if (config.isAsGif()) {
                builder = type.asGif();
                return builder.into(view);
            }
            //bitmap类型
            builder = type.asBitmap();
            //是否圆型图片
            if (config.isCropCircle()) {
                builder.transform(new GlideRoundTransform((Context) context, 10));
            }
            //淡入淡出动画
            if (config.isCrossFade()) {
                builder = type.crossFade();
            }
            builder.diskCacheStrategy(config.getDiskCache()).//硬盘缓存,默认为SOURCE类型,仅仅保存原始分辨率的图片
                    skipMemoryCache(config.isSkipMemoryCache()).//true,跳过内存缓存,默认false
                    priority(config.getPriority());//加载优先级
            //设置标签,默认为加载的文件路径
            if (!TextUtils.isEmpty(config.getTag())) {
                builder.signature(new StringSignature(config.getTag()));
            } else {
                builder.signature(new StringSignature(load.toString()));
            }
            if (null != config.getAnimator()) {
                builder.animate(config.getAnimator());
            }
            if (null != config.getAnimResId()) {
                builder.animate(config.getAnimResId());
            }

            //设置缩略图的缩放比例
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }

            //加载错误的资源
            if (null != config.getErrorResId()) {
                builder.error(config.getErrorResId());
            }
            //加载中的资源
            if (null != config.getPlaceHolderResId()) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            //图片最终显示在ImageView上的宽高度像素
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener) {
                setListener(builder, listener);
            }
            //设置加载的缩略图
            if (null != config.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = requestManager.load(config.getThumbnailUrl()).asBitmap();
                return builder.thumbnail(thumbnailRequest).into(view);
            } else {
                if (null != config.getSimpleTarget()) {//指定simpleTarget对象,可以在Target回调方法中处理bitmap,同时该构造方法中还可以指定size
                    return builder.into(config.getSimpleTarget());
                } else if (null != config.getViewTarget()) {//指定viewTarget对象,可以是自定义View,该构造方法传入的view和通配符的view是同一个
                    return builder.into(config.getViewTarget());
                } else if (null != config.getNotificationTarget()) {//设置通知栏加载大图片的target;
                    return builder.into(config.getNotificationTarget());
                } else if (null != config.getAppWidgetTarget()) {//设置加载小部件图片的target
                    return builder.into(config.getAppWidgetTarget());
                } else {
                    if (bitmapLoadingListener != null) {
                        return setBitmapLoadListener(builder, bitmapLoadingListener);
                    } else {
                        return builder.into(view);
                    }

                }
            }

        } catch (Exception e) {
            L.e("load", e);
            //异常加载错误图片
            view.setImageResource(config.getErrorResId());
            return null;
        }
    }

    private static <T> RequestManager initRequestManager(T context) {
        RequestManager requestManager = null;
        //传递的context参数
        if (context instanceof Activity) {
            requestManager = Glide.with((Activity) context);
        } else if (context instanceof android.support.v4.app.Fragment) {
            requestManager = Glide.with((android.support.v4.app.Fragment) context);
        } else if (context instanceof android.app.Fragment) {
            requestManager = Glide.with((android.app.Fragment) context);
        } else if (context instanceof Context) {
            requestManager = Glide.with((Context) context);
        } else if (context instanceof android.support.v4.app.FragmentActivity) {
            requestManager = Glide.with((android.support.v4.app.FragmentActivity) context);
        } else {
            throw new RecyclableBufferedInputStream.InvalidMarkException("context type error");
        }
        return requestManager;
    }

    private static void setListener(GenericRequestBuilder request, final LoaderListener listener) {
        request.listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                if (!e.getMessage().equals("divide by zero")) {
                    listener.onError();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onSuccess();
                return false;
            }
        });
    }

    private static Target setBitmapLoadListener(GenericRequestBuilder builder, final BitmapLoadListener loadingListener) {
        return builder.into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                loadingListener.onSuccess(resource);
            }
        });
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public static <T> void cancelAllTasks(T context) {
        initRequestManager(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static <T> void resumeAllTasks(T context) {
        initRequestManager(context).resumeRequests();
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearMemory();
            }
        }).start();
    }


    /**
     * drawable 转bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 转成圆形图片
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        //圆形图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //正方形的边长
        int r = 0;
        //取最短边做边长
        if(width > height) {
            r = height;
        } else {
            r = width;
        }
        //构建一个bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        //设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        //宽高相等，即正方形
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, paint);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
        //返回已经绘画好的backgroundBmp
        return backgroundBmp;
    }

}
