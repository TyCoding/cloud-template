package cn.tycoding.api.common.service;

import java.util.List;

/**
 * 封装通用Service接口
 *
 * @author tycoding
 * @date 2019-05-22
 */
public interface BaseService<T> {

    List<T> selectAll();

    T selectByKey(Object key);

    void save(T entity);

    void delete(Object key);

    void batchDelete(List<Long> ids, String property, Class<T> clazz);

    void updateAll(T entity);

    void updateNotNull(T entity);

    List<T> selectByExample(Object example);
}
