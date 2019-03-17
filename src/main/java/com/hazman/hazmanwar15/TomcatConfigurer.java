package com.hazman.hazmanwar15;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import java.io.File;

@Configuration
public class TomcatConfigurer {
    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                // Ensure that the webapps directory exists
                new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdirs();

                Context context = tomcat.addWebapp("/hazman", "/Users/raj/reinvent/idea-projects/hazmanwar15/vendor/hazelcast-mancenter-3.11.2.war");
                // Allow the webapp to load classes from your fat jar
                context.setParentClassLoader(getClass().getClassLoader());
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }
}
