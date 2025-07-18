package org.zerock.sboard.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 1. 스프링 설정 클래스임을 명시 (Spring이 인식하도록)
public class RootConfig {

    @Bean // 2. 아래 메서드에서 만든 객체를 Spring이 Bean으로 관리한다.
          // 해당 메서드가 리턴하는 객체를 스프링이 관리할 수 있도록 등록
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper(); // 3. ModelMapper객체 생성
                                    // (객체 복사 도구. 필드 이름이 같거나 비슷하면 자동으로 복사해줌. DTO <-> Entity 변환에 자주쓴다.)
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // 4. 필드이름끼리 매칭허용
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                                                // 5.private 필드에도 접근 가능 (getter가 없어도 값을 읽을수있게됨)
                .setMatchingStrategy(MatchingStrategies.LOOSE); //6. 느슨한 매칭 전략 설정
                                                        // LOOSE : 필드 이름이 완벽하게 일치하지 않아도 비슷하면 매칭 시도
        return modelMapper; // 만든 Mapper를 Bean으로 리턴
    }
}
