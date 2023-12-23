package org.soneira.savings.application.config

import org.jetbrains.annotations.NotNull
import org.soneira.savings.domain.service.SavingCreator
import org.soneira.savings.domain.service.impl.SavingCreatorImpl
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EntityScan(basePackages = ["org.soneira.homepal.savings.persistence.mongo"])
@EnableMongoRepositories(basePackages = ["org.soneira.homepal.savings.persistence.mongo"])
@Configuration
class SavingsConfiguration {
    @Bean
    fun savingCreator(): SavingCreator {
        return SavingCreatorImpl()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(@NotNull registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST")
            }
        }
    }
}