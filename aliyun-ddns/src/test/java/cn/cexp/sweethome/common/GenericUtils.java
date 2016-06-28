package cn.cexp.sweethome.common;

public class GenericUtils {
	/**
	 * 判断字符串对象是否为null或长度为0
	 * 
	 * @param obj
	 *            对象
	 * @return 若字符串对象为null或长度为0则为true
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && ((String) obj).length() == 0) {
			return true;
		}

		return false;
	}

	public static boolean equals(Object obj1, Object obj2) {
		if (null == obj1 && null == obj2) {
			return true;
		} else if (null == obj1) {
			if (obj2 instanceof String) {
				return 0 == ((String) obj2).length();
			}
			return false;
		} else if (null == obj2) {
			if (obj1 instanceof String) {
				return 0 == ((String) obj1).length();
			}
			return false;
		} else {
			return obj1.equals(obj2);
		}
	}

	public static boolean equalsIgnoreCase(Object obj1, Object obj2) {
		if (null == obj1 && null == obj2) {
			return true;
		} else if (null == obj1) {
			if (obj2 instanceof String) {
				return 0 == ((String) obj2).length();
			}
			return false;
		} else if (null == obj2) {
			if (obj1 instanceof String) {
				return 0 == ((String) obj1).length();
			}
			return false;
		} else if (obj1 instanceof String && obj2 instanceof String) {
			return ((String) obj1).equalsIgnoreCase((String) obj2);
		} else {
			return obj1.equals(obj2);
		}
	}
}
