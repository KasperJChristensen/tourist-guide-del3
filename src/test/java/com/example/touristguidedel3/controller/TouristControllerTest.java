package com.example.touristguidedel3.controller;

import com.example.touristguidedel3.model.Category;
import com.example.touristguidedel3.model.TouristAttraction;
import com.example.touristguidedel3.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.touristguidedel3.model.Category.CULTURE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TouristControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    TouristService service;

    @Test
    void getAttractions() throws Exception {
        ArrayList<TouristAttraction> mockList = new ArrayList<>();
        mockList.add(new TouristAttraction("Tivoli", "Cool sted", "København", List.of(CULTURE)));
        when(service.getAttractions()).thenReturn(mockList);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("showattractions"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", mockList));

        verify(service).getAttractions();
    }

    @Test
    void findAttractionByName() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction("Tivoli", "Cool sted", "København", List.of(CULTURE));
        when(service.findAttractionByName("Tivoli")).thenReturn(mockAttraction);

        mockMvc.perform(get("/attractions/Tivoli"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));

        verify(service).findAttractionByName("Tivoli");

    }

    @Test
    void findTags() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction("Tivoli", "Cool sted", "København", List.of(CULTURE));
        when(service.findAttractionByName("Tivoli")).thenReturn(mockAttraction);

        mockMvc.perform(get("/attractions/Tivoli/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("showtags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));

        verify(service).findAttractionByName("Tivoli");
    }

    @Test
    void addAttraction() throws Exception {
        List<Category> mockTags = List.of(Category.values());
        when(service.getTags()).thenReturn(mockTags);

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewattraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", mockTags));
    }

    @Test
    void saveAttraction() throws Exception {

        mockMvc.perform(post("/attractions/save")
                        .param("name", "Tivoli")
                        .param("description", "Sted i København")
                        .param("location", "København")
                        .param("tags", "CULTURE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).saveAttraction(captor.capture());

        TouristAttraction attraction = captor.getValue();
        assertEquals("Tivoli", attraction.getName());
        assertEquals("Sted i København", attraction.getDescription());
        assertEquals("København", attraction.getLocation());
        assertEquals(List.of(CULTURE), attraction.getTags());

    }

    @Test
    void editAttraction() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction("Tivoli", "Cool sted", "København", List.of(CULTURE));
        when(service.findAttractionByName("Tivoli")).thenReturn(mockAttraction);

        List<String> mockCities = List.of("København", "Roskilde");
        when(service.getCities()).thenReturn(mockCities);

        List<Category> mockTags = List.of(Category.values());
        when(service.getTags()).thenReturn(mockTags);

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("attraction", mockAttraction))
                .andExpect(model().attribute("cities", mockCities))
                .andExpect(model().attribute("tags", mockTags));
    }

    @Test
    void updateAttraction() throws Exception {
        mockMvc.perform(post("/attractions/update")
                        .param("name", "Tivoli")
                        .param("description", "Sted i København")
                        .param("location", "København")
                        .param("tags", "CULTURE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).updateAttraction(captor.capture());

        TouristAttraction attraction = captor.getValue();
        assertEquals("Tivoli", attraction.getName());
        assertEquals("Sted i København", attraction.getDescription());
        assertEquals("København", attraction.getLocation());
        assertEquals(List.of(CULTURE), attraction.getTags());

    }

    @Test
    void deleteAttraction() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction("Tivoli", "Cool sted", "København", List.of(Category.CULTURE));
        when(service.findAttractionByName("Tivoli")).thenReturn(mockAttraction);

        mockMvc.perform(post("/attractions/delete/Tivoli"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(service).deleteAttraction("Tivoli");
    }
}