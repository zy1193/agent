package com.keicei.common;

import java.util.List;
import java.util.Map;

/**
 * 管理器接口基类
 * 
 * @author zhyan
 * 
 * @param <E>
 *            entity 实体对象
 * @param <K>
 *            key 实体对象的主键的类型
 * @param <M>
 *            mapper 与实体对象对应的数据操作对象
 */
public interface BaseManager<E, K, M extends BaseMapper<E, K>> {

	int insert(E entity);

	int update(E entity);

	int delete(K key);

	E select(K key);

	List<E> list(Map<String, Object> parameters);

	List<E> list(Map<String, Object> parameters, int page, int pageSize);

	int count(Map<String, Object> parameters);
}
