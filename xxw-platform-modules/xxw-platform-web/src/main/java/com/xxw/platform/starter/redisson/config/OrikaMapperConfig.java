package com.xxw.platform.starter.redisson.config;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrikaMapperConfig {

    @Bean
    public MapperFacade mapperFacade(List<Mapper> mappers, List<Converter> converters) {
        return new ConfigurableMapper() {
            @Override
            protected void configure(MapperFactory factory) {
                super.configure(factory);
                if (mappers != null) {
                    mappers.forEach(mapper -> factory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize(mapper).register());
                }
                if (converters != null) {
                    converters.forEach(converter -> factory.getConverterFactory().registerConverter(converter));
                }
            }
        };
    }
}
