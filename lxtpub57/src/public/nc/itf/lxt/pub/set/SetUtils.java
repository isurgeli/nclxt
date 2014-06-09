package nc.itf.lxt.pub.set;

import java.lang.reflect.Array;
import java.util.List;

public class SetUtils {
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		Class<?> type = first.getClass().getComponentType();
		@SuppressWarnings("unchecked")
		T[] result = (T[])Array.newInstance(type, totalLength);
		System.arraycopy(first, 0, result, 0, first.length);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
	
	public static String concatString(char deli, String[] strs, String[]... rest) {
		StringBuffer ret = new StringBuffer();
		
		for (int i=0; i<strs.length; i++) 
			ret.append(strs[i]+deli);
		
		for (String[] array : rest) {
			for (int i=0; i<array.length; i++) 
				ret.append(array[i]+deli);
		}
		
		ret.delete(ret.length()-1, ret.length());
		
		return ret.toString();
	}
	
	public static String concatString(char deli, List<String> strs, List<String>... rest) {
		StringBuffer ret = new StringBuffer();
		
		for (int i=0; i<strs.size(); i++) 
			ret.append(strs.get(i)+deli);
		
		for (List<String> array : rest) {
			for (int i=0; i<array.size(); i++) 
				ret.append(array.get(i)+deli);
		}
		
		ret.delete(ret.length()-1, ret.length());
		
		return ret.toString();
	}
}
