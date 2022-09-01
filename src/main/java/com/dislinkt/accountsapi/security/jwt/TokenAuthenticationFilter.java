package com.dislinkt.accountsapi.security.jwt;

import com.dislinkt.accountsapi.domain.account.Account;
import com.dislinkt.accountsapi.exception.types.EntityNotFoundException;
import com.dislinkt.accountsapi.service.accounts.AccountService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AccountService accountService;

    public TokenAuthenticationFilter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String username = Jwts.parser().setSigningKey("qvuSsmEgb8").parseClaimsJws(token).getBody().getSubject();

            if (username != null) {
                Optional<Account> account = accountService.findOneByUsername(username);

                if (account.isEmpty()) {
                    throw new EntityNotFoundException("Not authenticated");
                }

                User user = new User(account.get().getUsername(), "", new ArrayList<SimpleGrantedAuthority>());

                TokenBasedAuthentication authentication = new TokenBasedAuthentication(token, user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
