//package com.sparta.trellocopy.domain.card.controller;
//
//
//import com.sparta.trellocopy.domain.card.dto.res.CardSimpleResponse;
//import com.sparta.trellocopy.domain.card.service.CardService;
//import com.sparta.trellocopy.domain.user.dto.AuthUser;
//import com.sparta.trellocopy.domain.user.entity.User;
//import com.sparta.trellocopy.domain.user.entity.UserRole;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//@WebMvcTest(CardController.class)
//class CardControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CardService cardService;
//
//    @Test
//    void card_생성_성공() throws Exception{
//        // given
//        long cardId = 1L;
//        String title = "제목";
//        String contents = "설명";
//        LocalDateTime deadline = LocalDateTime.parse("2024-10-14T10-20-20");
//        AuthUser authUser = new AuthUser(1L, "user@user.com", UserRole.ROLE_USER);
//        CardSimpleResponse res = new CardSimpleResponse(
//            "Card created sucessfully",
//            "user@user.com",
//            200
//        );
//
//
//        // when
//        cardService.createdCard();
//
//
//        // then
//
//
//
//    }
//
//
//}