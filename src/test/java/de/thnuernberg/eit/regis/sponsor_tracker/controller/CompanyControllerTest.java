package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sector;
import de.thnuernberg.eit.regis.sponsor_tracker.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CompanyControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CompanyService companyService;

    private MockMvc mockMvc;

    private MockMvc mockMvc() {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        }
        return mockMvc;
    }

    @Test
    void getCompanies_returnsListWithOk() throws Exception {
        Company c = new Company();
        c.setId(1L);
        c.setName("Siemens");
        c.setSector(Sector.INDUSTRY);
        when(companyService.findAll()).thenReturn(List.of(c));

        mockMvc().perform(get("/api/companies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Siemens"))
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getCompanyById_whenNotFound_returns404() throws Exception {
        when(companyService.findById(99L)).thenReturn(Optional.empty());

        mockMvc().perform(get("/api/companies/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createCompany_withoutName_returns400() throws Exception {
        String invalidJson = "{ \"sector\": \"INDUSTRY\", \"city\": \"Munich\" }";

        mockMvc().perform(post("/api/companies")
                .contentType("application/json")
                .content(invalidJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createCompany_valid_returns201() throws Exception {
        Company saved = new Company();
        saved.setId(1L);
        saved.setName("Bosch");
        when(companyService.create(any(Company.class), anyString())).thenReturn(saved);

        String validJson = "{ \"name\": \"Bosch\", \"sector\": \"INDUSTRY\" }";

        mockMvc().perform(post("/api/companies")
                .contentType("application/json")
                .header("X-Created-By", "Regis")
                .content(validJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Bosch"));
    }
}