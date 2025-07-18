package org.zerock.sboard.service;

import org.zerock.sboard.dto.BoardDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);
}
