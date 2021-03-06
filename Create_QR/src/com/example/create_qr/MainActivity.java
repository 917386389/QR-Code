package com.example.create_qr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/** 
* @ClassName: MainActivity 
* @author cjj 
* @date 2014年12月9日 下午5:52:24 
* @Description: 本demo是仿微信的二维码名片 本身google的二维码是一个开源的项目我们要制作一个二维码很简单,
*  				本例的作用是将图片与二维码结合，当然图片不能太大,要不然二维码读不出来。
* 
*/
public class MainActivity extends Activity {

	/**
	 *  图片宽度的一半
	 */
	private static final int IMAGE_HALFWIDTH = 20;
	
	/**
	 * 显示二维码图片
	 */
	private ImageView imageView;
	
	/**
	 *  插入到二维码里面的图片对象
	 */
	private Bitmap bitmap;
	
	/**
	 * 需要插图图片的大小 这里设定为40*40
	 */
	int[] pixels = new int[2*IMAGE_HALFWIDTH * 2*IMAGE_HALFWIDTH];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		//构建图片对象
		imageView = new ImageView(this);
		//构造需要插入的图片对象
		bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.item)).getBitmap();
		// 缩放图片
		Matrix matrix = new Matrix();
		float sx = (float)2*IMAGE_HALFWIDTH/bitmap.getWidth();
		float sy = (float)2*IMAGE_HALFWIDTH/bitmap.getHeight();
		matrix.setScale(sx, sy);
		// 重新构造一个40*40的图片
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
				matrix, false);
		
		try {
			String s = "BEGIN:VCARD\n" +
					"FN:cjj\n"+
					"TEL;TYPE=WORK,VOICE:823312343\n"+
					"ADR;TYPE=WORK,VOICE:fjkafjklafjadskd\n"+
					"TITLE:dfdsa\n"+
					"EMAIL;TYPE=WORK,VOICE:rerwe@qq.com\n"+
					"TEL:1232432423\n"+
					"ORG:空间发发\n"+
					"URL:ww.baidu.com\n"+ 
					"END:VCARD";
		//生成二维码
		imageView.setImageBitmap(createBitmap(new String(s.getBytes(),"ISO-8859-1")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setContentView(imageView);
		
	}

	/**
	 * 生成二维码的方法
	 * 
	 * @param str
	 * @return
	 */
	public Bitmap createBitmap(String str) {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		try {
			BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE,
					300, 300);
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			// 二维矩阵转为一维像素数组,也就是一直横着排了
			int halfW = width / 2;
			int halfH = height / 2;
			int[] pixels = new int[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
							&& y < halfH + IMAGE_HALFWIDTH) {
						pixels[y * width + x] = bitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
								- halfH + IMAGE_HALFWIDTH);
					} else {
						if (matrix.get(x, y)) {
							pixels[y * width + x] = 0xff000000;
						}
					}
	
				}
			}
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			// 通过像素数组生成bitmap
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
//	/**
//	 * 生成二维码
//	 * 
//	 * @param 字符串
//	 * @return Bitmap
//	 * @throws WriterException
//	 */
//	public Bitmap cretaeBitmap(String str) throws WriterException {
//		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//		BitMatrix matrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, 300, 300);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		// 二维矩阵转为一维像素数组,也就是一直横着排了
//		int halfW = width / 2;
//		int halfH = height / 2;
//		int[] pixels = new int[width * height];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {
//					pixels[y * width + x] = bitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
//							- halfH + IMAGE_HALFWIDTH);
//				} else {
//					if (matrix.get(x, y)) {
//						pixels[y * width + x] = 0xff000000;
//					}
//				}
//
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// 通过像素数组生成bitmap
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//
//		return bitmap;
//	}
}
