/**
 * 
 */
package com.pascloud.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author chenly
 *
 */
public class EnumUtils {

	public static final <T extends Enum<T> & EnumWithValue> T enumForValue(Class<T> type, int value) {
		final EnumSet<T> allOf = EnumSet.allOf(type);
		final Iterator<T> iterator = allOf.iterator();
		while (iterator.hasNext()) {
			final T next = iterator.next();
			if (next.getValue() == value) {
				return next;
			}
		}
		throw new IllegalArgumentException("Unknow enum value: '" + value + "'");
	}
	
	public static <T extends Enum<T> & EnumWithValue> List<Integer> enumForValueList(Class<T> type) {
		List<Integer> valueList = new ArrayList<Integer>();
		final Iterator<T> iterator = EnumSet.allOf(type).iterator();
		while (iterator.hasNext()) {
			valueList.add(iterator.next().getValue());
		}
		return valueList;
	}

}
