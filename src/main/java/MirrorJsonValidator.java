import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.networknt.schema.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class MirrorJsonValidator {

	private static final JsonSchema SCHEMA = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
			.getSchema(new File("mirrors.schema.json").getAbsoluteFile().toURI());

	/**
	 * Validates mirrors.json against mirrors.schema.json.
	 *
	 * @param mirrorsJson the mirrors.json file to be validated
	 * @return true if mirrorsJson is a valid json matching schema, false otherwise
	 */
	public static boolean ValidateMirrorJson(File mirrorsJson) {

		JsonNode mirrorsJsonNode;
		try {
			mirrorsJsonNode = new JsonMapper().readTree(mirrorsJson);
		} catch (JsonParseException e) {
			System.out.println("mirrors.json contains invalid json");
			return false;
		} catch (IOException e) {
			System.out.println("Failed to read mirrors.json from file");
			return false;
		}

		Set<ValidationMessage> errors = SCHEMA.validate(mirrorsJsonNode);
		if (errors.isEmpty()) {
			System.out.println("mirrors.json is valid!");
			return true;
		} else {
			System.out.println("Errors were found in mirrors.json:");
			System.out.println(errors);
			return false;
		}
	}
}