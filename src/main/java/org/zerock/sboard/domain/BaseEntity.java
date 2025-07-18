package org.zerock.sboard.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 이 클래스를 테이블로 만들지는 않지만, 자식클래스가 필드 상속 가능
@EntityListeners(value = {AuditingEntityListener.class}) // @EnableJpaAuditing설정이 프로젝트 어딘가에 있어야 감시(Auditing) 가능
//감시자 등록  데이터 변경 시 이벤트를 감지
@Getter
abstract class BaseEntity {
    //abstract class  : 직접 개체를 만들 수 없는 추상클래스 다른 클래스가 상속해서 사용함.
    //모든 엔티티(Board등)에 날짜(regDate, modDate)를 자동으로 기록해주기 위한 추상클래스임

    @CreatedDate // 객체가 처음 저장될때 시간 기록
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate // 객체가 수정될때 시간 갱신
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
