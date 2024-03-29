package com.liulishuo.coins.txn.boot;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.liulishuo.server.httpserver.MyHttpServer;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class StartUp {
	private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
    public static void main(String[] args) throws IOException{
    	logger.debug("Application Server Starting!");
        SpringApplication app = new SpringApplication(StartUp.class);
        app.setWebEnvironment(true);
        app.setShowBanner(false);
        Set<Object> set = new HashSet<Object>();
        set.add("classpath:META-INF/spring/applicationContext.xml");
        app.setSources(set);
        app.run(args);
        System.out.println("port=8080 restful service server started!");
        MyHttpServer.start();
        logger.debug("Application Server Started!");
    }  
}