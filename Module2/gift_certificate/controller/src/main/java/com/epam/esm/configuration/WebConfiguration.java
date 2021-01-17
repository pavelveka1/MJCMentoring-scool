package com.epam.esm.configuration;

import com.epam.esm.service.configuration.ServiceConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
public class WebConfiguration  extends AbstractAnnotationConfigDispatcherServletInitializer{

        @Override
        protected Class<?>[] getRootConfigClasses() {
            return null;
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{DBConfig.class, ServiceConfiguration.class};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }

}
