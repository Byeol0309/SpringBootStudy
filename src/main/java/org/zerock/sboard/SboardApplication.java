package org.zerock.sboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // AuditingEntityListener를 활성화시키기위해 필요함. 날짜 자동기록 기능 활성화
public class SboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SboardApplication.class, args);
    }

}
