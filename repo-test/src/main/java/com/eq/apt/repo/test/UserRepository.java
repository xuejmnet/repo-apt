package com.eq.apt.repo.test;

import com.eq.apt.repo.processor.annotations.ProxyRepository;
import com.eq.apt.repo.processor.repositories.CrudRepository;
import com.eq.apt.repo.test.proxy.UserProxy;

/**
 * create time 2024/5/19 22:37
 * 文件说明
 *
 * @author xuejiaming
 */
@ProxyRepository
public interface UserRepository extends CrudRepository<UserProxy, User> {
}
