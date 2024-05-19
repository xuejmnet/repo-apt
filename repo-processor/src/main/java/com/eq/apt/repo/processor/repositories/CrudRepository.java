package com.eq.apt.repo.processor.repositories;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import org.springframework.context.ApplicationContext;

/**
 * create time 2024/5/17 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CrudRepository<TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> {
    Class<T> tableClass();
    ApplicationContext getApplicationContext();
    EntityQueryable<TProxy, T> getQuery();
}
