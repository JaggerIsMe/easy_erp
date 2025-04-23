package com.easyerp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jagger
 * * @date 2025/4/16
 */

@Component("appConfig")
@Getter
public class AppConfig {

    @Value("${company.code}")
    private String companyCode;

}
