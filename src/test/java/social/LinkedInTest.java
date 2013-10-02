package social;

import org.junit.Test;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

public class LinkedInTest {

	protected String apiKey = "y6qh5rnhnujk";

	protected String secretKey = "4pFLIEZ1DKfmet8u";

	protected String oAuthUserToken = "d1756a28-dd46-4753-ac6c-b858e87bb6da";

	protected String oAuthUserSecret = "677242a4-bf92-47e3-a495-34eeaa89dba1";

	@Test
	public void checkProfile() {
//		ConnectionData data = new ConnectionData(providerId, providerUserId, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime)
//		connectionFactoryLocator().getConnectionFactory(LinkedInConnectionFactory.class).createConnection(data);
		
		LinkedIn linkedin = new LinkedInTemplate(oAuthUserToken);
		String id = "7538458";
		LinkedInProfile profile = linkedin.profileOperations().getProfileById(
				id);
		System.out.println(profile);
	}

	private ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new LinkedInConnectionFactory(apiKey,
				secretKey));
		return registry;
	}
}
