package org.zerock.sboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sboard.domain.Board;
import org.zerock.sboard.dto.BoardDTO;
import org.zerock.sboard.repository.BoardRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor // final로 선언된 필드를 자동으로 생성자 주입
@Transactional           // 이 클래스의 메서드들이 트랜잭션 범위 안에서 실행됨. (메서드 도중 문제 발생시 DB작업 자동 롤백)
                         // 여러개의 작업을 하나처럼 묶어서, 전부 성공 or 전부 실패 , All or Nothing 모든 단계가 성공해야 반영된다.
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository; //실제 DB저장소 (save, find)

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class); // 첫번째인자(원본객체)에서 두번째인자(복사받을 클래스)로 값을 자동 복사

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow(); // Optional안에 값이 있으면 값 반환, 없으면 NoSuchElementException 예외
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return  boardDTO;
        //P466 수정작업할차례
    }
}
