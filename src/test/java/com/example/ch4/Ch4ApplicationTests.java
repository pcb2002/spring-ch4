package com.example.ch4;

import com.example.ch4.entity.Member;
import com.example.ch4.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CloudApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("POST /api/members - 팀원 정보를 성공적으로 저장한다")
    void saveMemberTest() throws Exception {
        String jsonContent = """
                {
                    "name": "Gemini",
                    "age": 25,
                    "mbti": "ENTJ"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)) // 직접 만든 문자열 삽입
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gemini"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.mbti").value("ENTJ"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/members/{id} - 저장된 팀원 정보를 조회한다")
    void getMemberTest() throws Exception {
        Member member = new Member();
        member.setName("Cloud");
        member.setAge(30);
        member.setMbti("INFP");
        Member savedMember = memberRepository.save(member);

        mockMvc.perform(get("/api/members/" + savedMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cloud"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.mbti").value("INFP"))
                .andDo(print());
    }
}