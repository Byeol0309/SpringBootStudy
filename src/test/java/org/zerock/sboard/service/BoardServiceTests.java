package org.zerock.sboard.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sboard.dto.BoardDTO;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired // Spring이 BoardService를 찾아서 자동으로 주입 / (@Autowired)가 없으면 객체가 안들어가서 null상태가됨
    private BoardService boardService; // BoardService는 이미 @Service로 등록돼 있음

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName()); // 주입된 객체의 클래스 이름 출력

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title....")
                .content("Sample Content....")
                .writer("user00")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);

    }


}
