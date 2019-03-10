package com.ilamcvel.service.impl;

import com.ilamcvel.service.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class that implements Displayservice. It uses Spring's message source
 * to get messages from resources.properties and returns them.
 */
@Component
public class DisplayServiceImpl implements DisplayService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
    }
}
