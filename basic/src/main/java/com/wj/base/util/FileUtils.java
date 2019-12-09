package com.wj.base.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Date;

/**
 * 文件、path工具类
 * 
 * @author wjian
 * */
public class FileUtils {

	public static boolean fileWrite(String filePath, byte[] data) {
		if (filePath == null) return false;
		int i = filePath.lastIndexOf('/');
		if (i < 0) return false;
		String dirPath = filePath.substring(0, i);
		File dir = new File(dirPath);
		if (dir.exists() == false) {
			if (dir.mkdirs() == false) return false;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			if (data != null) fos.write(data);
			fos.close();
		} catch (Exception e) {
			if (fos != null) try {
				fos.close();
			} catch (Exception ee) {
			}
			return false;
		}
		return true;
	}

	public static final boolean fileExisted(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public static final boolean fileMakeDirs(String filePath) {
		File file = new File(filePath);
		return file.mkdirs();
	}

	public static final long fileSize(String filePath) {
		File file = new File(filePath);
		return file.exists() ? file.length() : 0;
	}

	public static final byte[] fileRead(String filePath) {
		if (filePath == null) return null;
		File file = new File(filePath);
		if (file.exists() == false || file.isDirectory()) return null;
		int fileLen = (int) file.length();
		byte[] bts = new byte[fileLen];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			int readLen = 0;
			while (readLen < fileLen)
				readLen += fis.read(bts, readLen, fileLen - readLen);
			fis.close();
			return bts;
		} catch (Exception e) {
			if (fis != null) try {
				fis.close();
			} catch (Exception ee) {
			}
			return null;
		}
	}

	public static final void fileStreamClose(FileInputStream fis) {
		try {
			if (fis != null) fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void streamWriteString(DataOutput dop, String str) throws IOException {
		if (str == null)
			dop.writeByte(0);
		else {
			dop.writeByte(1);
			dop.writeUTF(str);
		}
	}

	public static final boolean streamTransfer(InputStream is, OutputStream os, int buflen) {
		byte[] buffer = new byte[buflen];
		while (true) {
			try {
				int len = is.read(buffer);
				if (len == -1) break;
				os.write(buffer, 0, len);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static final String streamReadString(DataInput dip) throws IOException {
		if (dip.readByte() == 0) return null;
		return dip.readUTF();
	}

	public static final void streamWriteBytes(DataOutput dop, byte[] bts) throws IOException {
		if (bts == null)
			dop.writeByte(0);
		else {
			dop.writeByte(1);
			dop.writeInt(bts.length);
			dop.write(bts);
		}
	}

	public static final FileOutputStream fileOpenToWrite(String filepath) {
		try {
			File file = new File(filepath);
			if (file.isDirectory()) return null;//宸茬粡瀛樺湪骞朵笖涓虹洰褰�
			if (file.exists() && file.delete() == false) return null;
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false && parent.mkdirs() == false) return null;
			return new FileOutputStream(file);
		} catch (Exception e) {
			return null;
		}
	}

	public static String fileMd5(String filePath, int bufSize) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			byte[] buffer = new byte[bufSize];
			int readCount = 0;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			while ((readCount = fis.read(buffer)) != -1) {
				md5.update(buffer, 0, readCount);
			}
			return TextUtils.StringUtils.bytesToHexString(md5.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			fileStreamClose(fis);
		}
	}

	public static boolean copyDb2SdCard(Application application, String externalHomeDir) {
		if (externalStorageExist() == false) return false;
		try {
			File desDir = new File(externalHomeDir + "/databases_" + System.currentTimeMillis());
			if (desDir.exists() == false)
				if (desDir.mkdirs() == false)
					return false;


			File databaseDir = new File(application.getFilesDir().getAbsolutePath().replace("files", "databases"));
			if (databaseDir.exists() && databaseDir.isDirectory()) {
				for (File srcFile : databaseDir.listFiles()) {
					if (srcFile.isDirectory()) continue;
					File desFile = new File(desDir.getAbsoluteFile() + "/" + srcFile.getName());
					if (desFile.createNewFile() == false) return false;
					copyFileByChannel(srcFile, desFile);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static long copyFileByChannel(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 128000;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				in.close();
				inC.close();
				out.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 128000;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}


	public static boolean externalStorageExist() {
		boolean ret = false;
		ret = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
		return ret;
	}


	/**
	 * 根据传过来的目录返回 目录里所有文件的大小
	 *
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		long length = 0;
		File files[] = dir.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					getDirSize(f);
				} else {
					System.out.println(f);
					length += f.length();
				}
			}
		}
		return length;
	}

	public static void deleteCache(File dir) {
		File files[] = dir.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					getDirSize(f);
				} else {
					f.delete();
				}
			}
		}
	}

	/**
	 * 从assets拷贝文件到sd卡
	 * @param assetsFile 要拷贝的文件
	 *                   @param savePath 要拷贝到的目录
	 *                                   @param saveName 拷贝后的文件名
	 * */
	public static boolean copyAssets2SDCard(Context context, String assetsFile, String savePath, String saveName){
		try {
			if(!FileUtils.fileExisted(savePath)){
				FileUtils.fileMakeDirs(savePath);
			}
			OutputStream myOutput = new FileOutputStream(savePath + "/" + saveName);
			InputStream myInput = context.getAssets().open(assetsFile);

			byte[] buffer = new byte[1024];
			int length = myInput.read(buffer);
			while(length > 0)
            {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
			myOutput.flush();
			myInput.close();
			myOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static InputStream getAssetsFile(Context context, String path){
		try {
			return context.getAssets().open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteFile(String path){
		if(TextUtils.isEmpty(path)){
			return;
		}
		deleteFile(new File(path));
	}

	public static void deleteFile(File file){
		try {
			if(file == null){
                return;
            }
			if(file.exists()){
                if(file.isDirectory()){
                    File[] files = file.listFiles();
                    for (File fileC : files){
                        deleteFile(fileC);
                    }
                    file.delete();
                } else {
                    file.delete();
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}