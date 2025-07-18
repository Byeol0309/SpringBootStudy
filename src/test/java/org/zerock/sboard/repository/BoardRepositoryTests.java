package org.zerock.sboard.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sboard.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest //메서드용 테스트 동작
@Log4j2 //로그용
public class BoardRepositoryTests {
    //영속성 계층에 테스트용

    @Autowired // 생성자 자동주입
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            //IntStream.rangeClosed(1,100) 1부터 100까지 숫자를 만들어내는 자바 스트림 for문 대체용
            Board board = Board.builder()
                    .title("title...."+i)    // board.setTitle()
                    .content("content...."+i)// board.setContent()
                    .writer("user"+(i % 10)) // board.setWriter
                    .build();                // @Builder용 (setter대신 좀더 간단하고 가독성 좋게)
                    log.info(board);

            Board result = boardRepository.save(board); // .save JPA에서 제공하는 메서드, 객체(Entity)를 DB에 저장함.
            log.info("BNO: " + result.getBno());
            log.info("Title: " + result.getTitle());
            log.info("Content: " + result.getContent());
        });
    } //testInsert()종료

    @Test
    public void testSelect(){
        Long bno = 100L; // 게시물 번호가 100번인 개체를 확인해본다.
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update..title 100", "update content 100");
        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno =1L;
        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        //1page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info("total count : "+ result.getTotalElements());
        log.info("total pages : "+result.getTotalPages());
        log.info("page number : "+result.getNumber());
        log.info("page size : "+result.getSize());

        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info(board));
    }
        @Test
        public void testSearch1(){
        // 2page order by bno desc
            Pageable pageable = PageRequest.of(1,10,Sort.by("bno").descending());

            boardRepository.search1(pageable);
            //BoardRepository의 구현체
        }

        @Test
        public void testSearchAll(){
        String[] types = {"t", "c", "w" };
        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
            Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        }

        @Test
        public void testSearchAll2(){
        String[] types = {"t", "c", "w"};
        String keyword ="1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

            //total pages
            log.info("total pages : " + result.getTotalPages());

            //page size
            log.info("page size : " + result.getSize());

            //pageNumber
            log.info(result.getNumber());

            //prev next
            log.info(result.hasPrevious() + ": " + result.hasNext()); // hasPrevious() 이전페이지 존재하면 true, 없으면  false
            result.getContent().forEach(board -> log.info(board));    // hasNext() 다음페이지 존재하면 true, 없으면 false
        }
}
