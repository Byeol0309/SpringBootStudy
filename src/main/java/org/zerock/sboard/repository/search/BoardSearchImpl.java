package org.zerock.sboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sboard.domain.Board;
import org.zerock.sboard.domain.QBoard;

import java.util.List;

// extend - QuerydslRepositorySupport (Querydsl을 사용할때 도와주는 Spring Data JPA의 도우미클래스)
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    // Impl : implementation (구현체)
    // BoardSearch 인터페이스(interface)를 구현하는 클래스이다.
    // interface : 클래스가 따라야할 '기능' 메서드 이름만 있고 실제 내용은 없음.
    // implements : 해당 인터페이스에 정의된 메서드를 실제로 구현하겠다

    // QyerydslRepositorySupport : Querydsl쿼리를 만들때 도와주는 스프링 유틸클래스
    // Impl : 인터페이스를 실제로 구현한 클래스임을 나타내는 명명 규칙

    public BoardSearchImpl(){ //생성자
        super(Board.class);
        // 상속받은 QuerydslRepositorySupport에
        //         어떤 엔티티(Board)를 다루는지 알려주는 역할
        // Board.class 는 검색하고자 하는 테이블(또는 엔티티)이 Board임을 지정한것
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        // Board 객체들을 페이지 형식으로 리턴한다.
        // Pageable : 페이지 번호, 페이지 크기(한페이지에 몇개 보여줄지), 정렬방식 등을 담고있는 객체임
        QBoard board = QBoard.board; // Q도메인객체 (QBoard.board : Querydsl에서 Board엔티티를 다루기 위한 객체다)
                                      // Querydsl에서는 각 엔티티에 대응하는 Q타입 객체가 자동 생성된다.
        JPQLQuery<Board> query = from(board); //from( ) :  select...from board

        //where 조건에 and와 or를 (  ) 로 묶어야할때 사용한다.
        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (
        booleanBuilder.or(board.title.contains("11"));        // title like....
        booleanBuilder.or(board.content.contains("11"));      // content like.....

        query.where(booleanBuilder);
        query.where(board.bno.gt(0L));

        query.where(board.title.contains("1")); // where( ) :  where title like...
                            // contains : 포함시킨다는뜻.
                            // WHERE title LIKE '%1%'

        //paging
        this.getQuerydsl().applyPagination(pageable, query); // applyPagination : pageable에 담긴 정보(페이지 번호, 개수, 정렬)를 query에 자동 적용
        List<Board> list =query.fetch(); // fetch()는 해당 조건에 맞는 모든 결과를 리스트(list) 형태로 반환한다.
        long count = query.fetchCount(); // 조건에 맞는 결과가 총 몇개인지 개수를 세는 쿼리
                                         // Page 타입을 리턴하려면 전체 개수도 있어야하므로.

                                         // JPQL은 엔티티 이름과 필드 이름을 사용하는 쿼리 (JPA가 사용하는 쿼리언어)
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        if ((types != null && types.length > 0) && keyword != null) { // 검색조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types) {
                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }// for문종료
            query.where(booleanBuilder);
        }//if문 종료

        query.where(board.bno.gt(0L)); // gt : greater than(크다) 를 의미
                                            //where bno > 0
        //pageing
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
