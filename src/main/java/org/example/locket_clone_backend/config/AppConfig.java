package org.example.locket_clone_backend.config;

import lombok.RequiredArgsConstructor;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.repository.UserRepository;
import org.hibernate.collection.spi.PersistentSet;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    // modelMapper.addConverter(new PersistentSetToSetConverter<>());

    return modelMapper;
  }

  public class PersistentSetToSetConverter<T> extends AbstractConverter<Set<T>, Set<T>> {

    @Override
    protected Set<T> convert(Set<T> source) {
      if (source == null)
        return null;
      return new HashSet<>(source); // or: source.stream().collect(Collectors.toSet());
    }
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      System.out.println(username);
      return userRepository
          .findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("User with email: " + username + " not found"));

    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

}
