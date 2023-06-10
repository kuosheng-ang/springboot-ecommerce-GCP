package com.clementang.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import javax.servlet.ServletContext;



@EnableAsync
@Configuration
@EnableWebMvc
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/register").setViewName("/register");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/cart").setViewName("/cart");
        registry.addViewController("/customer").setViewName("/customer");
        registry.addViewController("/admin/pages").setViewName("admin/pages");
        registry.addViewController("/admin/categories").setViewName("admin/categories");
        registry.addViewController("/admin/products").setViewName("admin/products");
        registry.addRedirectViewController("/", "/register");
    }


    public SpringResourceTemplateResolver templateResolver(){


        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }


    @Bean
    @Description("Thymeleaf View Resolver")
    public ThymeleafViewResolver viewResolver() {
    //public ViewResolver viewResolver() {

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setViewNames(new String[] {".html", ".xhtml"});
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCache(false);
        return resolver;
    }

    @Bean
    @Description("Thymeleaf Template Engine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/");

        if (!registry.hasMappingForPattern("/media/**")) {
            registry.addResourceHandler("/media/**")
                    .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/static/media/");
        }
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/static/");
        }
        if (!registry.hasMappingForPattern("/templates/**")) {
            registry.addResourceHandler("/templates/**")
                    .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/templates/");
        }
        if (!registry.hasMappingForPattern("/css/**")) {
            registry.addResourceHandler("/css/**")
                    .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/static/css/");
        }
        if (!registry.hasMappingForPattern("/js/**")) {
            registry.addResourceHandler("/js/**")
                    .addResourceLocations("file:/G:/springtutorials/cmsshoppingcart/cmsshoppingcart/src/main/resources/static/js/");
        }
    }

}
