import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.networknt.schema.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class MirrorJsonValidator {

	public static void main(String[] args) throws IOException {

		File schemaLocation = new File("src/main/resources/mirrors.schema.json"); // temp
		AbsoluteIri iri = new AbsoluteIri("file://" + schemaLocation.getAbsolutePath());
		SchemaLocation schema = new SchemaLocation(iri);

		JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
		JsonSchema jsonSchema = factory.getSchema(schema);

		JsonNode jsonNode;

		try {
			jsonNode = new JsonMapper().readTree(new File("src/main/resources/mirrors2.json"));
		} catch (JsonParseException e) {
			System.out.println("Failed to parse mirrors.json");
			e.printStackTrace();
			return;
		}

		Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

		if (errors.isEmpty()) {
			System.out.println("mirrors.json is valid!");
		} else {
			System.out.println("Errors were found in mirrors.json:");
			System.out.println(errors);
		}

	}
}