package cropcert.entities.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.jwt.JwtClaims;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import com.google.inject.Inject;

import cropcert.entities.MyApplication;
import cropcert.entities.model.User;
import cropcert.entities.util.AuthUtility;
import cropcert.entities.util.SimpleUsernamePasswordAuthenticator;

public class AuthenticateService {

	@Inject
	private UserService userService;

	@Inject
	private SimpleUsernamePasswordAuthenticator usernamePasswordAuthenticator;

	public CommonProfile authenticate(String username, String password) throws Exception {
		// Authenticate the user using the credentials provided
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		usernamePasswordAuthenticator.validate(credentials, null);
		return credentials.getUserProfile();
	}

	public Map<String, Object> buildTokenResponse(CommonProfile profile, long userId, boolean getNewRefreshToken) {
		return buildTokenResponse(profile, userService.findById(userId), getNewRefreshToken);
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
		try {
			String jwtToken = generateAccessToken(profile, user);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("access_token", jwtToken);
			result.put("token_type", "bearer");
			result.put("timeout", AuthUtility.getAccessTokenExpiryDate());

			if (getNewRefreshToken) {
				String refreshToken = generateRefreshToken(profile, user);
				result.put("refresh_token", refreshToken);
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Generates access token in JWT format encrypted with JWT_SALT as secret for
	 * the profile.
	 * 
	 * @param profile dummy
	 * @return TODO : use bcrypt encryption for token
	 */
	private String generateAccessToken(CommonProfile profile, User user) {

		JwtGenerator<CommonProfile> generator = new JwtGenerator<CommonProfile>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));
		
		Set<String> roles = new HashSet<String>();
		roles.add(user.getRole());
		Set<String> permissions = user.getPermissions();

		Map<String, Object> jwtClaims = new HashMap<String, Object>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getAccessTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());
		jwtClaims.put("roles", roles);
		jwtClaims.put("permissions", permissions);

		String jwtToken = generator.generate(jwtClaims);
		return jwtToken;
	}

	/**
	 * Generates a refresh token which is a plain string used to identify user.
	 * 
	 * @return dummy
	 */
	private String generateRefreshToken(CommonProfile profile, User user) {
		JwtGenerator<CommonProfile> generator = new JwtGenerator<CommonProfile>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));

		Set<String> roles = new HashSet<String>();
		roles.add(user.getRole());

		Map<String, Object> jwtClaims = new HashMap<String, Object>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getRefreshTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());

		generator.setExpirationTime(AuthUtility.getRefreshTokenExpiryDate());
		
		String jwtToken = generator.generate(jwtClaims);
		return jwtToken;

	}
}
