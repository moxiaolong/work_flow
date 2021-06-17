package com.zjx.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 工作流程应用程序
 *
 * @author cgn
 * @date 2021/06/15
 */
@SpringBootApplication

@EnableJpaRepositories
@EnableJpaAuditing
@EnableAsync
public class WorkFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkFlowApplication.class);
    }
}
