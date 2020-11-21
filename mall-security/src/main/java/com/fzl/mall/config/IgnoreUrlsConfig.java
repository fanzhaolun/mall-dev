package com.fzl.mall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "sceure.ignored")
@Getter
@Setter
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
