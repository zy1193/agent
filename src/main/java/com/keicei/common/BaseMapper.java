package com.keicei.common;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口基类
 * 
 * @author zhyan
 * 
 * @parameters <E>
 *            要操作的实体对象
 * @parameters <K>
 *            该实体对象的主键的数据类型(String,Integer,Long等)
 */
public interface BaseMapper<E, K> {

	/** 插入对象 返回插入的数量 **/
	int insert(E entity);

	/** 更新对象 返回更新的数量 **/
	int update(E entity);

	/** 删除对象 返回删除的数量 **/
	int delete(K key);

	/** 用主键查询对象 **/
	E select(K key);

	/** 按条件查询对象 **/
	List<E> list(Map<String, Object> parameters);

	/** 按条件统计对象数量 **/
	int count(Map<String, Object> parameters);

}
