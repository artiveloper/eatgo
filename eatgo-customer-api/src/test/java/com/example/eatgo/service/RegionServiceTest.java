package com.example.eatgo.service;

import com.example.eatgo.domain.Region;
import com.example.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class RegionServiceTest {

    @InjectMocks
    RegionService regionService;

    @Mock
    RegionRepository regionRepository;

    @Test
    void getRegions() throws Exception {
        //given
        List<Region> expectedRegions = Arrays.asList(
                Region.builder()
                        .name("Seoul")
                        .build());

        doReturn(expectedRegions).when(regionRepository).findAll();

        //when
        List<Region> actualRegions = regionService.getRegions();
        Region actualRegion = actualRegions.get(0);

        //then
        assertThat(actualRegion.getName()).isEqualTo(expectedRegions.get(0).getName());
    }

}