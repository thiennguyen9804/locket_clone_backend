package org.example.locket_clone_backend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);
    System.out.println(token);

    try {
      Long id = jwtUtil.verifyToken(token);
      System.out.println(id);
      if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow();
        String email = userEntity.getEmail();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } catch (JWTVerificationException jwtException) {
      System.out.println("Invalid JWT Token");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
      return;
    } catch (NoSuchElementException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
      return;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    filterChain.doFilter(request, response);
  }
}
