package com.example.touristguidedel3.controller;

import com.example.touristguidedel3.exception.AttractionNotFound;
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

    List<String> tags = List.of("Architecture", "Park", "Historic", "Museum", "Art", "Nature", "Landmark");


    @Test
    void getAttractions() throws Exception {
        ArrayList<TouristAttraction> mockList = new ArrayList<>();
        mockList.add(new TouristAttraction(1,"Tivoli", "Cool sted", "København", List.of("Architecture", "Park")));
        when(service.getAttractions()).thenReturn(mockList);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("showattractions"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", mockList));

        verify(service).getAttractions();
    }

    @Test
    void findAttractionById() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction(1,"Tivoli", "Cool sted", "København", List.of("Architecture", "Park"));
        when(service.findAttractionById(1)).thenReturn(mockAttraction);

        mockMvc.perform(get("/attractions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));

        verify(service).findAttractionById(1);

    }

    @Test
    void findAttractionById_notFound() throws Exception {
        when(service.findAttractionById(999)).thenThrow(new AttractionNotFound(999));

        mockMvc.perform(get("/attractions/999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    void findTags() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction(1,"Tivoli", "Cool sted", "København", List.of("Architecture", "Park"));
        when(service.findAttractionById(1)).thenReturn(mockAttraction);

        mockMvc.perform(get("/attractions/1/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("showtags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));

        verify(service).findAttractionById(1);
    }

    @Test
    void addAttraction() throws Exception {

        when(service.getTags()).thenReturn(tags);
        when(service.getCities()).thenReturn(List.of("København"));

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewattraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", tags))
                .andExpect(model().attributeExists("cities"));

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
        assertEquals(List.of("CULTURE"), attraction.getTags());
    }

    @Test
    void editAttraction() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction(1,"Tivoli", "Cool sted", "København", List.of("Architecture", "Park"));
        when(service.findAttractionById(1)).thenReturn(mockAttraction);

        List<String> mockCities = List.of("København", "Roskilde");
        when(service.getCities()).thenReturn(mockCities);

        List<String> mockTags = tags;
        when(service.getTags()).thenReturn(mockTags);

        mockMvc.perform(get("/attractions/1/edit"))
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
                        .param("tags", "Culture"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).updateAttraction(captor.capture());

        TouristAttraction attraction = captor.getValue();
        assertEquals("Tivoli", attraction.getName());
        assertEquals("Sted i København", attraction.getDescription());
        assertEquals("København", attraction.getLocation());
        assertEquals(List.of("Culture"), attraction.getTags());
    }

    @Test
    void deleteAttraction() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction(1,"Tivoli", "Cool sted", "København", List.of("Architecture", "Park"));
        when(service.findAttractionById(1)).thenReturn(mockAttraction);

        mockMvc.perform(post("/attractions/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(service).deleteAttraction(1);
    }
}