package com.wj.base.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;

import junit.framework.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片工具类
 * 
 * @author wjian
 * */
public class BitmapUtils
{
	public static final File FILE_SDCARD = Environment.getExternalStorageDirectory();
	public static  File FILE_LOCAL;
	public static final int IMAGE_SIZE = 30000;//微信分享图片大小限制

	public static final byte[] streamReadBytes(DataInput dip) throws IOException
	{
		if(dip.readByte()==0) return null;
		int len = dip.readInt();
		byte[] bts = new byte[len];
		dip.readFully(bts);
		return bts;
	}
	
	public static final byte[] bitmapToPngBytes(Bitmap bitmap)
	{
		if(bitmap==null) return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	public static final Bitmap bitmapRoundCorner(Bitmap bitmap, int roundPixels) 
	{
		if(bitmap==null) return null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float radius = (width>height ? height/2 : width/2);
		
		Bitmap circleBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setShader(shader);
		paint.setAntiAlias(true);
		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);
		return circleBitmap;
	}

	public static Bitmap bitmapBlur(Bitmap bmp, int delta)  
	{
		if(bmp==null) return null;
		int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
		int width = bmp.getWidth();  
		int height = bmp.getHeight();  
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
		int pixR = 0;  
		int pixG = 0;  
		int pixB = 0;  
		int pixColor = 0;  
		int newR = 0;  
		int newG = 0;  
		int newB = 0;  
		int idx = 0;
		int[] pixels = new int[width * height];  
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
		for(int i=1, length=height-1; i<length; i++)  
		{  
			for(int k=1, len=width-1; k<len; k++)  
			{
				idx = 0;  
				for(int m=-1; m<=1; m++)  
				{
					for (int n = -1; n <= 1; n++)  
					{  
						pixColor = pixels[(i + m) * width + k + n];  
						pixR = Color.red(pixColor);  
						pixG = Color.green(pixColor);  
						pixB = Color.blue(pixColor);  
						newR = newR + (int) (pixR * gauss[idx]);  
						newG = newG + (int) (pixG * gauss[idx]);  
						newB = newB + (int) (pixB * gauss[idx]);  
						idx++;  
					}
				}
				newR /= delta;  
				newG /= delta;  
				newB /= delta;  
				newR = Math.min(255, Math.max(0, newR));  
				newG = Math.min(255, Math.max(0, newG));  
				newB = Math.min(255, Math.max(0, newB));  
				pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
				newR = 0;  
				newG = 0;  
				newB = 0;  
			}
		}
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
		return bitmap;  
    }
	
	public static Bitmap bitmapRound(Bitmap bitmap)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
		if(width <= height)
		{
			roundPx = width/2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		}
		else
		{
			roundPx = height/2;
			float clip = (width - height)/2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width,height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		int color = 0xff424242;
		Paint paint = new Paint();
		Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
		Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
		RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
	
	/**
	 * string转成bitmap
	 * @param st
	 */
	public static Bitmap convertStringToIcon(String st) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	 * @param drawable
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if(drawable == null){
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_4444: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap drawableResIdToBitmap(Context context, int resId) {
		if(resId<=0) return null;
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}

	/**
	 * 图片转成string
	 */
	public static String convertIconToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);

	}

	public static byte[] bmpToByteArr(Bitmap bmp){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 100, output);
		int options = 100;
		while (output.toByteArray().length > IMAGE_SIZE && options > 0) {
			output.reset(); //清空baos
			bmp.compress(CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 5;
			if(options < 0){
				options = 0;
			}
		}
		bmp.recycle();
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}




	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static Bitmap blurBitmap(Bitmap bitmap,Context context){

		//Let's create an empty bitmap with the same size of the bitmap we want to blur
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

		//Instantiate a new Renderscript
		RenderScript rs = RenderScript.create(context);

		//Create an Intrinsic Blur Script using the Renderscript
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

		//Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
		Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
		Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

		//Set the radius of the blur
		blurScript.setRadius(25.f);

		//Perform the Renderscript
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);

		//Copy the final bitmap created by the out Allocation to the outBitmap
		allOut.copyTo(outBitmap);

		//recycle the original bitmap
		bitmap.recycle();

		//After finishing everything, we destroy the Renderscript.
		rs.destroy();

		return outBitmap;
	}

	public static String saveBitmapToJPEG(Bitmap mBitmap)
	{
		Bitmap bitmap = compressImage(mBitmap, 100);
		FILE_LOCAL  = new File(FILE_SDCARD,"wobo"+"_"+System.currentTimeMillis());
		String path = FILE_LOCAL+".jpg";
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return path;
	}

	/**
	 * @param mBitmap 需要存储的bitmap
	 *                @param path 存储的路径
	 *                            @param fileName 文件名，不包含后缀
	 *                                            @param size 保存的文件大小单位为Kb
	 * */
	public static boolean saveBitmapToPNG(Bitmap mBitmap, String path, String fileName, int size)
	{
		try
		{
//			Bitmap bitmap = compressImage(mBitmap, size);
			File directory = new File(path);
			if(!directory.exists()){
				directory.createNewFile();
			}
			if(!directory.isDirectory()){
				return false;
			}
			File img  = new File(path + File.separator + fileName + ".png");
			if(img.exists()){
				img.delete();
			}
			img.createNewFile();
			FileOutputStream fos = new FileOutputStream(img);
			mBitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
			if(!mBitmap.isRecycled()){
				mBitmap.recycle();
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 图片压缩，压缩质量
	 *
	 * @param image 目标图片
	 *
	 *             @param length 压缩到的大小 单位为K
	 * */
	public static  Bitmap compressImage(Bitmap image, int length) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		while ( baos.toByteArray().length / 1024>length) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}



	/**
	 * 将彩色图转换为灰度图
	 * @param img 位图
	 * @return  返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth();         //获取位图的宽
		int height = img.getHeight();       //获取位图的高

		int []pixels = new int[width * height]; //通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for(int i = 0; i < height; i++)  {
			for(int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey  & 0x00FF0000 ) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	private static final String TAG = "SDK_Sample.Util";

	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle)
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp, int maxLength, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] result = new byte[0];
		int quality = 100;
		while (true){
			if(quality < 0 || quality > 100){
				break;
			}
			bmp.compress(CompressFormat.PNG, quality, output);
			byte[] bs = output.toByteArray();
			if(bs.length >= maxLength){
				quality -= 10;
				output.reset();
			}else{
				result = bs;
				break;
			}
		}

		if (needRecycle) {
			bmp.recycle();
		}

		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] getHtmlByteArray(final String url) {
		URL htmlUrl = null;
		InputStream inStream = null;
		try {
			htmlUrl = new URL(url);
			URLConnection connection = htmlUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){
				inStream = httpConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = inputStreamToByte(inStream);

		return data;
	}

	public static byte[] inputStreamToByte(InputStream is) {
		try{
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] readFromFile(String fileName, int offset, int len) {
		if (fileName == null) {
			return null;
		}

		File file = new File(fileName);
		if (!file.exists()) {
			Log.i(TAG, "readFromFile: file not found");
			return null;
		}

		if (len == -1) {
			len = (int) file.length();
		}

		Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

		if(offset <0){
			Log.e(TAG, "readFromFile invalid offset:" + offset);
			return null;
		}
		if(len <=0 ){
			Log.e(TAG, "readFromFile invalid len:" + len);
			return null;
		}
		if(offset + len > (int) file.length()){
			Log.e(TAG, "readFromFile invalid file len:" + file.length());
			return null;
		}

		byte[] b = null;
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			b = new byte[len]; // ŽŽœšºÏÊÊÎÄŒþŽóÐ¡µÄÊý×é
			in.seek(offset);
			in.readFully(b);
			in.close();

		} catch (Exception e) {
			Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
			e.printStackTrace();
		}
		return b;
	}

	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
	public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;

			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				bm.recycle();
				bm = cropped;
				Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}

	/**
	 * 按正方形剪裁图片
	 * 指定正方形边长
	 */
	public static Bitmap imageCrop(Bitmap bitmap,int widthWeight, int heightWeight) {
		// 得到图片的宽，高
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

	/*	//width最大不能超过长方形的短边
		if(w > h){

		}
		return Bitmap.createBitmap(bitmap, retX, retY, width, width, null, false);*/
	return null;
	}
}
