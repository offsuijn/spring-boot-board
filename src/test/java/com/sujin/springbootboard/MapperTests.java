package com.sujin.springbootboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sujin.springbootboard.domain.BoardDTO;
import com.sujin.springbootboard.mapper.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class MapperTests {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void testOfInsert() {
        BoardDTO params = new BoardDTO();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("tester");

        int result = boardMapper.insertBoard(params);
        System.out.println("result is " + result);
    }

    @Test
    public void testOfSelectOfDetail() {
        BoardDTO boardDTO = boardMapper.selectBoardDetail((long) 1);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String boardJson = objectMapper.writeValueAsString(boardDTO);

            System.out.println("==================");
            System.out.println(boardJson);
            System.out.println("==================");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfUpdate() {
        BoardDTO params = new BoardDTO();
        params.setTitle("1번 수정 타이틀");
        params.setContent("1번 수정 내용");
        params.setWriter("정수지니");
        params.setIdx((long) 1);

        int result = boardMapper.updateBoard(params);

        if (result == 1) {
            BoardDTO boardDTO = boardMapper.selectBoardDetail((long) 1);
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String boardJson = objectMapper.writeValueAsString(boardDTO);

                System.out.println("==================");
                System.out.println(boardJson);
                System.out.println("==================");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testOfDelete() {
        int result = boardMapper.deleteBoard((long) 1);

        if (result == 1) {
            BoardDTO boardDTO = new BoardDTO();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String boardJson = objectMapper.writeValueAsString(boardDTO);

                System.out.println("==================");
                System.out.println(boardJson);
                System.out.println("==================");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testMultipleInsert() {
        for (int i = 2; i <= 50; i++) {
            BoardDTO params = new BoardDTO();
            params.setTitle(i + "번 게시글 제목");
            params.setContent(i + "번 게시글 내용");
            params.setWriter(i + "번 게시글 작성자");
            boardMapper.insertBoard(params);
        }
    }

    @Test
    public void testOfSelectList() {
        int boardTotalCount = boardMapper.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            List<BoardDTO> boardDTOList = boardMapper.selectBoardList();
            if (!CollectionUtils.isEmpty(boardDTOList)) {
                for (BoardDTO boardDTO : boardDTOList) {
                    System.out.println("==================");
                    System.out.println(boardDTO.getTitle());
                    System.out.println(boardDTO.getContent());
                    System.out.println(boardDTO.getWriter());
                    System.out.println("==================");
                }
            }


        }
    }

}
