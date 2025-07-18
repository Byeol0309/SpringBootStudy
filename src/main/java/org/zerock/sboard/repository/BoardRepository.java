package org.zerock.sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sboard.domain.Board;
import org.zerock.sboard.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {
    // JpaRepository<Board Long>을 상속받으면
    // .save() , findById, findAll(), delete()등의 기본 DB작업 메서드가 자동 생성된다.
    // 이름만으로도 특정 쿼리 자동 생성됨 (findByWriter, findByTitleContaining등 가능)
    // JpaRepository<Board Long> : Spring Data JPA가 제공하는 인터페이스이다
    // Board-다루고싶은 엔티티 클래스 , Long-그 엔티티의 PK타입
    // Board 엔티티에 대한 기본적인 CRUD기능을 자동으로 사용할 수 있게 해준다.

    @Query(value="select now()", nativeQuery = true)
    String getTime();
}
