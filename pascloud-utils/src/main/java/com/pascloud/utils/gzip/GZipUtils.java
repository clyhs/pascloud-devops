package com.pascloud.utils.gzip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.logging.log4j.core.helpers.FileUtils;

/**
 * 
 * @author chenly
 *
 */
public class GZipUtils {

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		compress(bais, baos);

		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return output;
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os)
			throws Exception {

		GZIPOutputStream gos = new GZIPOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}

		gos.finish();

		gos.flush();
		gos.close();
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String inputFileName, String outputFileName)
			throws Exception {
		FileInputStream inputFile = new FileInputStream(inputFileName);
		FileOutputStream outputFile = new FileOutputStream(outputFileName);
		compress(inputFile, outputFile);
		inputFile.close();
		outputFile.flush();
		outputFile.close();
	}

	/**
	 * 数据解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 解压缩

		decompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return data;
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, true, null);
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * 			      需要解压的文件
	 * @param delete
	 *            是否删除原始文件
	 * @param outPath           
	 *            解压文件的输出路径
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete, String outPath)
			throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = null;
		if (outPath == null || outPath=="") {
			fos = new FileOutputStream(file.getPath().replace(EXT, ""));
		} else {
			File files = new File(outPath);
			//判断文件是否存在，不存在，则创建
			//FileUtil.mkDir(files);//此处调用了递归创建文件夹，没有写出，网上很多
			FileUtils.mkdir(files, true);
			
			//文件输出流参数中，需要指定文件解压后的文件名，这里，用文件的原名称
			fos = new FileOutputStream(outPath + File.separator
					+ file.getName().replace(EXT, ""));
		}

		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(String inputFileName, String outputFileName)
			throws Exception {
		FileInputStream inputFile = new FileInputStream(inputFileName);
		FileOutputStream outputFile = new FileOutputStream(outputFileName);
		decompress(inputFile, outputFile);
		inputFile.close();
		outputFile.flush();
		outputFile.close();
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {
		GZIPInputStream gis = new GZIPInputStream(is);
		//GZIPInputStream gis = new GZIPInputStream(new BufferedInputStream(is));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

	/**
	 * 文件解压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void decompress(String path) throws Exception {
		decompress(path, true, null);
	}

	/**
	 * 文件解压缩(解压单个文件)
	 * 
	 * @param path
	 *            需要解压的文件路径（包含文件名称）
	 * @param delete
	 *            是否删除原始文件（true：删除；false：保留）
	 * @param outPath
	 *            解压后文件的输出路径，如果该参数的值为 null，则输出解压文件到当前文件夹
	 * @throws Exception
	 */
	public static void decompress(String path, boolean delete, String outPath)
			throws Exception {
		File file = new File(path);
		decompress(file, delete, outPath);
	}

	//测试，通过遍历，解压一个文件夹中的所有文件
	public static void main(String[] args) throws Exception {
		//GZipUtils.decompress("d:/gz/nginx-1.13.2.tar.gz");
		//GZipUtils.decompress("d:/gz/nginx-1.13.2.tar.gz", false, "d:/gz/");
		//GZipUtils.compress("d:/gz/nginx-1.13.2.tar");
	}
}
