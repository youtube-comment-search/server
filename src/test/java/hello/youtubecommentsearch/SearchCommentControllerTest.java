//package hello.youtubecommentsearch;
//
//import hello.youtubecommentsearch.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import hello.youtubecommentsearch.controller.SearchCommentController;
//import hello.youtubecommentsearch.dto.CommentDTO;
//import hello.youtubecommentsearch.exception.CustomExceptionHandler;
//import hello.youtubecommentsearch.service.SearchCommentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//
//@WebMvcTest(SearchCommentController.class)
//public class SearchCommentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private SearchCommentService searchCommentService;
//
//    @Test
//    public void testGetComment_Success() throws Exception {
//        // Mock service response
//        CommentDTO comment1 = new CommentDTO();
//        comment1.setAuthorDisplayName("User1");
//        comment1.setTextOriginal("Hello, World!");
//
//        CommentDTO comment2 = new CommentDTO();
//        comment2.setAuthorDisplayName("User2");
//        comment2.setTextOriginal("Hi there!");
//
//        List<CommentDTO> comments = Arrays.asList(comment1, comment2);
//        given(searchCommentService.search(anyString(), anyString(), anyString(), anyString()))
//                .willReturn(comments);
//
//        // Perform GET request to the controller
//        mockMvc.perform(MockMvcRequestBuilders.get("/search/comment")
//                        .param("id", "UCrpoE9e2-eWcj8AYvwYdebw")
//                        .param("type", "channel")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authorDisplayName").value("User1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].textOriginal").value("Hello, World!"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].authorDisplayName").value("User2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].textOriginal").value("Hi there!"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void testGetComment_WebClientResponseException() throws Exception {
//        // Mock service to throw WebClientResponseException with 404 status code
//        given(searchCommentService.search(anyString(), anyString(), anyString(), anyString()))
//                .willThrow(new WebClientResponseException(HttpStatus.NOT_FOUND.value(), "Not Found", null, null, null));
//
//        // Perform GET request to the controller
//        mockMvc.perform(MockMvcRequestBuilders.get("/search/comment")
//                        .param("id", "invalid-channel-id")
//                        .param("type", "channel")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(12))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Resource not found"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.responseBody").doesNotExist())
//                .andDo(MockMvcResultHandlers.print());
//    }
//}
