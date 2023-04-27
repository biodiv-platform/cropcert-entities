package cropcert.entities.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.jwt.JwtClaims;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.user.ApiException;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.Role;
import com.strandls.user.pojo.User;

import cropcert.entities.MyApplication;
import cropcert.entities.util.AuthUtility;

public class AuthenticateService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticateService.class);

	@Inject
	private UserServiceApi userServiceApi;

	public Map<String, Object> buildTokenResponse(CommonProfile profile, Long userId, boolean getNewRefreshToken) {
		User user = null;
		try {
			user = userServiceApi.getUser(userId.toString());

		} catch (ApiException e) {
			logger.error(e.getMessage());
		}
		return buildTokenResponse(profile, user, getNewRefreshToken);

	}

	/**
	 * Builds a response for authentication. On success it returns a access token
	 * and optionally a refresh token
	 * 
	 * @param profile            dummy
	 * @param user               dummy
	 * @param getNewRefreshToken dummy
	 * @return dummy
	 */

	public Map<String, Object> buildTokenResponse(CommonProfile profile, User user, boolean getNewRefreshToken) {
		Map<String, Object> result = new HashMap<>();

		try {
			String jwtToken = generateAccessToken(profile, user);

			result.put("access_token", jwtToken);
			result.put("token_type", "bearer");
			result.put("timeout", AuthUtility.getAccessTokenExpiryDate());

			if (getNewRefreshToken) {
				String refreshToken = generateRefreshToken(profile);
				result.put("refresh_token", refreshToken);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;

	}

	/**
	 * Generates access token in JWT format encrypted with JWT_SALT as secret for
	 * the profile.
	 * 
	 * @param profile dummy
	 * @return : use bcrypt encryption for token
	 */
	private String generateAccessToken(CommonProfile profile, User user) {

		JwtGenerator<CommonProfile> generator = new JwtGenerator<>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));

		Set<String> roles = new HashSet<>();
		if (user.getRoles() != null && user.getRoles().isEmpty()) {
			roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());

		}

		Map<String, Object> jwtClaims = new HashMap<>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getAccessTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());
		jwtClaims.put("roles", roles);

		return generator.generate(jwtClaims);
	}

	/**
	 * Generates a refresh token which is a plain string used to identify user.
	 * 
	 * @return dummy
	 */
	private String generateRefreshToken(CommonProfile profile) {
		JwtGenerator<CommonProfile> generator = new JwtGenerator<>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));

		Map<String, Object> jwtClaims = new HashMap<>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getRefreshTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());

		generator.setExpirationTime(AuthUtility.getRefreshTokenExpiryDate());

		return generator.generate(jwtClaims);

	}
}
