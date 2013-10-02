package demo2.service;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Use the IP address to work out the process location via a public REST service.
 * 
 * @author Paul Chapman
 */
@Service
@Profile("REST")
public class LocationViaREST implements LocaleService {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(LocationViaREST.class);
	protected static final String BODY_PATH = "/html/body";

	protected RestTemplate rt = new RestTemplate();
	protected String defaultIpAddress = "124.171.4.123";
	protected XPathExpression expression;

	public LocationViaREST() {
		LOGGER.warn("Created " + getClass().getSimpleName());

		XPath xPath = XPathFactory.newInstance().newXPath();

		try {
			expression = xPath.compile(BODY_PATH);
		} catch (XPathExpressionException e) {
			LOGGER.error("Failed to parse " + BODY_PATH + ": "
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * Find what the outside world thinks is my IP address by asking dyndns.
	 * There doesn't seem to be a REST service for this, but the data is
	 * returned in a minimal HTML page:
	 * 
	 * <pre>
	 * &lt;html>&lt;head>&lt;title>Current IP Check&lt;/title>&lt;/head>&lt;body>Current IP Address: 124.171.4.123&lt;/body>&lt;/html>
	 * </pre>
	 * 
	 * @return The IP address extracted from the HTML document.
	 */
	private String findMyIpAddress() {
		String url = "http://checkip.dyndns.org/";
		ResponseEntity<String> resp = rt.getForEntity(url, String.class);
		String html = resp.getBody();
		String ipAddress = defaultIpAddress;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setValidating(false);
			Document doc = builder.parse(new InputSource(
					new ByteArrayInputStream(html.getBytes("utf-8"))));

			String text = expression.evaluate(doc.getDocumentElement());
			int ix = text.indexOf(':');

			if (ix != -1)
				ipAddress = text.substring(ix + 1).trim();
			else
				throw new IllegalStateException("Response not as expected: "
						+ html);
		} catch (Exception e) {
			LOGGER.error(e.getClass() + " " + e.getLocalizedMessage());
		}

		LOGGER.info(ipAddress);
		return ipAddress;
	}

	/**
	 * Use <tt>freegeoip.net</tt> to work out where my IP address is located.
	 */
	@SuppressWarnings("unchecked")
	public String getLocation() {
		String ipAddress = findMyIpAddress();
		Map<String, String> resp = (Map<String, String>) rt.getForObject(
				"http://freegeoip.net/json/" + ipAddress, Map.class);
		LOGGER.info(resp.toString());
		String location = resp.get("city") + ", " + resp.get("region_name");
		String zip = resp.get("zipcode");

		if (!StringUtils.isEmpty(zip))
			location += ", " + zip;

		return location;
	}

}
