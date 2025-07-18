package org.zerock.sboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
                    // BaseEntity를 상속받음 (regDate, modDate)자동추가, 자동값입력

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    // 엔티티 클래스는 반드시 @Entity를 적용해야하고 @Id가 필요하다.
    // auto increment : 데이터베이스에 추가될때 생성되는 번호
    // 키생성 전략(key generate strategy)
    // IDENTITY  : 데이터베이스에 위임(MYSQL/MariaDB)     -auto_increment
    // SEQUENCE  : 데이터베이스 시퀀스 오브젝트 사용(ORACLE)-@SequenceGenerator 필요
    // TABLE     : 키생성용 테이블 사용, 모든 DB에서 사용   -@TableGenerator 필요
    // AUTO      : 방언에 따라 자동 지정, 기본값

    @Column(length = 500, nullable = false) //칼럼의 길이와 null허용 여부
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
}
