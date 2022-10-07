package com.odeyalo.analog.netflix.service.storage;

import com.odeyalo.analog.netflix.service.storage.remote.ThumbsnapSiteRemoteDiskFileUploader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultFileUploaderFactory implements FileUploaderFactory {
    private final BeanFactory beanFactory;
    private final Environment environment;

    @Autowired
    public DefaultFileUploaderFactory(BeanFactory beanFactory, Environment environment) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    @Override
    public FileUploader getFileUploader() {
        List<String> profiles = List.of(environment.getActiveProfiles());
        if (profiles.contains("dev")) {
            return beanFactory.getBean(ThumbsnapSiteRemoteDiskFileUploader.class);
        }
        return beanFactory.getBean(LocalFileUploader.class);
    }
}
