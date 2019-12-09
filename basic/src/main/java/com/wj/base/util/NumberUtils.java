package com.wj.base.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.NumberFormat;
import java.util.HashMap;

/**
 * 捕捉数字格式转换异常,避免出现由数据类转换引起 的crash
 * 
 * @author wjian
 * */
@SuppressLint("UseSparseArrays")
public class NumberUtils {
	private static final String TAG = NumberUtils.class.getSimpleName();

	private static char[] chnNumbers;
    private static HashMap<Integer,String> unitMap;
    private static int[] unitArray ;
	
	static
    {
    	chnNumbers = new char[]{'零','一','二','三','四','五','六','七','八','九'};
    	unitMap = new HashMap<Integer,String>();
	    unitMap.put(1, "");
	    unitMap.put(10, "十");
	    unitMap.put(100, "百");
	    unitMap.put(1000, "千");
	    unitArray = new int[]{1000, 100, 10, 1};
    }
    
	public static String intToChnNum(int num)
	{
	    String resultNumber = null;
	    if(num > 10000 || num < 0) return "";
	    StringBuilder result = new StringBuilder();
	    int i = 0;
	    while(num > 0)
	    {
	        int n1 = num / unitArray[i];
	        if(n1 > 0)
	        {
	            result.append(chnNumbers[n1]).append(unitMap.get(unitArray[i]));
	        }
	        if(n1 == 0)
	        {
	            if(result.lastIndexOf("零") != result.length()-1)
	            {
	                result.append("零");
	            }
	        }
	        num = num % unitArray[i++];
	        if(num == 0)
	        {
	            break;
	        }
	    }
	    resultNumber = result.toString();
	    if(resultNumber.startsWith("零"))
	    {
	        resultNumber = resultNumber.substring(1);
	    }
	    if(resultNumber.startsWith("一十"))
	    {
	        resultNumber = resultNumber.substring(1);
	    }
	    return resultNumber;
	}
	
	/**
	 * String to Integer
	 * */
	public static Integer valueOf2Integer(String string, int def) 
	{
		if (!TextUtils.isEmpty(string)) 
		{
			try 
			{
				return Integer.parseInt(string);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return def;
	}

	/**
	 * String to Long
	 * */
	public static Long valueOf2Long(String string, long def) 
	{
		if (!TextUtils.isEmpty(string)) 
		{
			try 
			{
				return Long.valueOf(string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return def;
	}
	
	/**
	 * 判断某个值是否在区间内
	 */
	public static boolean rangeInDefined(long current, long min, long max)   {
		return Math.max(min, current) == Math.min(current, max);  
	}
	
	public static final String floatToString(float val, int maxFractionDigis)
	{
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(maxFractionDigis);
		return numberFormat.format(val);
	}
}
