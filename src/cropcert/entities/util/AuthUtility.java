package cropcert.entities.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.jwt.JwtClaims;

import cropcert.entities.filter.SecurityInterceptor;
import cropcert.entities.model.User;

public class AuthUtility {

	private static final long ACCESS_TOKEN_EXPIRY_TIME_IN_DAYS = 1;
	private static final long EXPIRY_TIME_IN_DAYS = 30;

	public static CommonProfile createUserProfile(User user) {
		if (user == null)
			return null;
		try {
			// Set<String> roles = user.getRoles();
			List<String> authorities = new ArrayList<>();
			/*
			 * for (Role role : roles) { authorities.add(role.getAuthority()); }
			 */
			return createUserProfile(user.getId(), user.getFirstName(), user.getEmail(), authorities);
		} catch (Exception e) {
			throw e;
		}
	}

	public static CommonProfile createUserProfile(Long userId, String username, String email,
			List<String> authorities) {
		CommonProfile profile = new CommonProfile();
		updateUserProfile(profile, userId, username, email, authorities);
		return profile;
	}

	public static void updateUserProfile(CommonProfile profile, Long userId, String username, String email,
			List<String> authorities) {
		if (profile == null)
			return;
		profile.setId(userId.toString());
		profile.addAttribute("id", userId);
		profile.addAttribute(Pac4jConstants.USERNAME, username);
		profile.addAttribute(CommonProfileDefinition.EMAIL, email);
		profile.addAttribute(JwtClaims.EXPIRATION_TIME, getAccessTokenExpiryDate());
		profile.addAttribute(JwtClaims.ISSUED_AT, new Date());
		for (Object authority : authorities) {
			profile.addRole((String) authority);
		}
	}

	public static Date getAccessTokenExpiryDate() {
		final Date now = new Date();
		long expDate = now.getTime() + ACCESS_TOKEN_EXPIRY_TIME_IN_DAYS * (24 * 3600 * 1000);
		return new Date(expDate);

	}

	public static Date getRefreshTokenExpiryDate() {
		final Date now = new Date();
		long expDate = now.getTime() + EXPIRY_TIME_IN_DAYS * (24 * 3600 * 1000);
		return new Date(expDate);
	}

	public static CommonProfile getCurrentUser(HttpServletRequest request) {
		return getCurrentUser(request, null);
	}

	public static CommonProfile getCommonProfile(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = authorizationHeader.substring("Bearer".length()).trim();
		return SecurityInterceptor.jwtAuthenticator.validateToken(token);
	}

	public static CommonProfile getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		return getCommonProfile(request);
	}
}
