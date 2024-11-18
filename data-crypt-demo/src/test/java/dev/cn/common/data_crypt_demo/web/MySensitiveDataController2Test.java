package dev.cn.common.data_crypt_demo.web;

import java.util.*;
import java.math.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.cn.common.data_crypt_demo.mapper.MySensitiveDataMapper;
import dev.cn.common.data_crypt_demo.po.MySensitiveDataPO;
import dev.cn.common.data_crypt_demo.vo.Response;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class MySensitiveDataController2Test {

    private MockMvc mockMvc;

    @Mock
    private MySensitiveDataMapper mySensitiveDataMapper;

    @InjectMocks
    private MySensitiveDataController mySensitiveDataController;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mySensitiveDataController).build();
    }

    @Test
    public void testList_ReturnsData() throws Exception {
        MySensitiveDataPO po = new MySensitiveDataPO();
        po.setId(1L);
        po.setName("name");
        po.setTel("13800138000");

        when(mySensitiveDataMapper.selectList(null)).thenReturn(Collections.singletonList(po));

        mockMvc.perform(get("/my-sensitive-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("name"))
                .andExpect(jsonPath("$.data[0].tel").value("13800138000"));

        verify(mySensitiveDataMapper, times(1)).selectList(null);
    }

    @Test
    public void testList_ReturnsEmptyList() throws Exception {
        when(mySensitiveDataMapper.selectList(null)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/my-sensitive-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(mySensitiveDataMapper, times(1)).selectList(null);
    }
}
