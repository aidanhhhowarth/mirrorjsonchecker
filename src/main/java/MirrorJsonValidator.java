import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.networknt.schema.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Set;

public class MirrorJsonValidator {

	public static final URI SCHEMA_URI = new File("mirrors.schema.json").getAbsoluteFile().toURI(); // location of mirrors.schema.json

	/**
	 * Validates mirrors.json against mirrors.schema.json.
	 *
	 * @param  mirrorsJson  the mirrors.json file to be validated
	 * @return      true if mirrorsJson is a valid json matching schema, false otherwise
	 */
	public static boolean ValidateMirrorJson(File mirrorsJson) {

		JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

		JsonSchema jsonSchema = jsonSchemaFactory.getSchema(SCHEMA_URI); // it is apparently very important that this URI is absolute

		JsonNode mirrorsJsonNode;
		try {
			mirrorsJsonNode = new JsonMapper().readTree(mirrorsJson);
		} catch (JsonParseException e) { // Invalid json
			System.out.println("mirrors.json contains invalid json");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Failed to read mirrors.json from file");
			return false;
		}

		Set<ValidationMessage> errors = jsonSchema.validate(mirrorsJsonNode);

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