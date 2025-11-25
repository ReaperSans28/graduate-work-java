package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final String username = "ads@test.com";
    private final String password = "Password8";

    @BeforeEach
    void setUpUser() {
        if (userRepository.findByEmail(username).isPresent()) {
            return;
        }
        Register register = new Register();
        register.setUsername(username);
        register.setPassword(password);
        register.setFirstName("Ads");
        register.setLastName("Tester");
        register.setPhone("+7 900 000-00-00");
        register.setRole(Role.USER);
        userService.register(register);
    }

    @Test
    void createAdAndFetchList() throws Exception {
        CreateOrUpdateAdDto dto = new CreateOrUpdateAdDto();
        dto.setTitle("Test Ad");
        dto.setPrice(1000);
        dto.setDescription("Test description");

        MockMultipartFile props = new MockMultipartFile(
                "properties",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(dto)
        );

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[]{1, 2, 3}
        );

        mockMvc.perform(multipart("/ads")
                        .file(props)
                        .file(image)
                        .with(httpBasic(username, password)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Ad"));

        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1));
    }
}

