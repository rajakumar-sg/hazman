package com.hazman.hazmanwar15;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Configuration
public class TomcatConfigurer {
    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                // Ensure that the webapps directory exists
                File catalinaBase = new File(tomcat.getServer().getCatalinaBase(), "webapps");
                catalinaBase.mkdirs();
                catalinaBase.toPath().resolve("vendor").toFile().mkdirs();

                try {
                    Files.copy(this.getClass().getResourceAsStream("/vendor/hazelcast-mancenter-3.11.2.war"), catalinaBase.toPath().resolve("vendor").resolve("hazelcast-mancenter-3.11.2.war"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Context context = tomcat.addWebapp("/hazman", "/Users/raj/reinvent/idea-projects/hazmanwar15/vendor/hazelcast-mancenter-3.11.2.war");
                Context context = tomcat.addWebapp("/hazman", "vendor/hazelcast-mancenter-3.11.2.war");
                // Allow the webapp to load classes from your fat jar
                context.setParentClassLoader(getClass().getClassLoader());
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }
}
