package com.eq.apt.repo.test;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.apt.repo.test.proxy.UserProxy;

/**
 * create time 2024/5/19 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user")
@EntityProxy
public class User implements ProxyEntityAvailable<User , UserProxy> {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Class<UserProxy> proxyTableClass() {
        return UserProxy.class;
    }
}
