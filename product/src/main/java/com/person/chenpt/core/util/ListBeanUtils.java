package com.person.chenpt.core.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ListBeanUtils extends BeanUtils {
	
	/**
	 * @param <S> 数据源类
	 * @param <T> 目标类::new(eg: AdminVo::new)
	 * @param sources
	 * @param target
	 * @return
	 */
	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
		return copyListProperties(sources, target, null);
	}
	
	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, ListBeanUtilsCallBack<S, T> callBack) {
		List<T> list = new ArrayList<>(sources.size());
		for (S source : sources) {
			T t = target.get();
			copyProperties(source, t);
			if (callBack != null) {
				// 回调
				callBack.callBack(source, t);
			}
			list.add(t);
		}
		return list;
	}
	
	@FunctionalInterface
	public interface ListBeanUtilsCallBack<S, T> {
		void callBack(S t, T s);
	}
}
