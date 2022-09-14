package com.odeyalo.analog.netflix.support.jpa;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

public class PrefixIdentifierGenerator implements IdentifierGenerator {
    /**
     * Prefix that will be used in id generation
     */
    private String prefix;
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        if (prefix == null) {
            return id;
        }
        return new StringBuilder().append(prefix).append("_").append(id).toString();
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.prefix = params.getProperty("prefix");
    }
}
