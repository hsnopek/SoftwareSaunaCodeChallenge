package hr.hsnopek.softwaresaunacodechallenge.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

	public static String read(String path) throws Exception {
		
		String res = null;
		ClassLoader classLoader = FileUtils.class.getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());
		
		try(InputStream fis = new FileInputStream(file)){
			res = new String(IOUtils.toByteArray(fis));
		} catch(Exception e) {
			throw e;
		}
		return res;
	}
}
