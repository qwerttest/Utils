package com.wj.base.util;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Density工具类
 * 
 * @author wjian
 * */
public final class DensityUtils {
	public static final int HEIGHT = 0;
	public static final int WIDTH = 1;

	private static float mDensity = 0;
	private static float mScaleDensity = 0;
	private static int mStatusBarHeight = 0;

	/**
	 * 得到设备屏幕的宽度
	 */
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null){
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
            return metrics.widthPixels;
        }
	    return getDisplayWidth(context);
	}

    /**
     * 得到设备屏幕的宽度
     */
    public static int getDisplayWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
	 * 得到设备屏幕的高度
	 */
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null){
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
            return metrics.heightPixels;
        }
        return getDisplayHeight(context);
	}

	public static int getDisplayHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

	/**
	 * 获取状态栏高度
	 * @param def 如果没有获取到，给一个默认值
	 */
	public static int getStatusBarHeight(Context context, int def){
		if(mStatusBarHeight <= 0){
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				mStatusBarHeight =  context.getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
				mStatusBarHeight = dip2px(context, def);
			}
		}
		return mStatusBarHeight;
	}

	/**
	 * 得到设备的密度
	 */
	public static float density(Context context) {
		if(mDensity <= 0){
			mDensity = context.getResources().getDisplayMetrics().density;
		}
		return mDensity;
	}

	public static float scaledDensity(Context context){
		if(mScaleDensity <= 0){
			mScaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
		}
		return mScaleDensity;
	}

	/**
	 * 把密度转换为像素
	 */
	public static final int dip2px(Context context, float dipValue){
		return (int)(dipValue * density(context) + 0.5f);
	}

	/**
	 * 像素转换密度
	 * */
	public static final int px2dip(Context context, float pxValue){
		return (int)(pxValue / density(context) + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		return (int) (pxValue / scaledDensity(context) + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		return (int) (spValue * scaledDensity(context) + 0.5f);
	}

	/**
	 * view 没绘制之前获取到view 的宽和高
	 * @param mView 要测量的view
	 *
	 *  @param type
	 */
	public static int getViewDimention(View mView, int type){
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mView.measure(w, h);
		int result;
		if (type==WIDTH){
			result=mView.getMeasuredWidth();
		}else if (type==HEIGHT){
			result=mView.getMeasuredHeight();
		}else {
			result=-1;
		}
		return result;
	}

	//获取一个中文字体的宽度.
	public static float getTextWidth(Context context, int textSize) {
		Paint mPaint = new Paint();
		int px = dip2px(context, textSize);
		mPaint.setTextSize(px);
		float fontSpace = mPaint.getFontSpacing();
		return fontSpace;
	}
}
