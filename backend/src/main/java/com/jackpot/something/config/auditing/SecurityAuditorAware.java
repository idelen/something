package com.jackpot.something.config.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityAuditorAware implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		// (1) 보안 컨텍스트에서 Authentication 객체 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// (2) 로그인 안 되어있거나 익명인 경우
		if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
			return Optional.of("ANONYMOUS");
			// 또는 Optional.empty()로 하여 null로 기록하게 해도 됨
		}

		// (3) 실제 사용자명
		String username = auth.getName();
		return Optional.ofNullable(username);
	}
}
