package com.fuzhucheng.circlemenu;

import android.content.Context;

/**
 * @author JackFrost
 * 一个常用工具类
 * 获取设备的高度和宽度，以及提供dp和px单位之间的转换
 */
public class DensityUtil {

	/**
	 * @param dpValue 需要转换的dp值
	 * @return 转换后的px值
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		//还可以通过如下代码获得
		//Resources.getSystem().getDisplayMetrics().density
		return (int) (dpValue * scale + 0.5f);
	}

}
