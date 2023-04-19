package de.cuioss.portal.tomcat.health;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import de.cuioss.tools.logging.CuiLogger;
import io.smallrye.health.SmallRyeHealth;
import lombok.experimental.UtilityClass;

/**
 * Transforms a {@link SmallRyeHealth} object into a {@link JsonObjectBuilder}, representing the
 * custom spring format.
 *
 * @author Sven Haag
 */
@UtilityClass
class MicroProfileToSpringResponseFormatMapper {

    private static final CuiLogger log = new CuiLogger(MicroProfileToSpringResponseFormatMapper.class);

    private static final String CHECKS = "checks";
    private static final String NAME = "name";

    public static JsonObjectBuilder apply(final SmallRyeHealth health) {
        final var customBuilder = Json.createObjectBuilder();

        log.debug("Transforming SmallRyeHealth to ICW health response format");
        // "checks" must always be present as defined in the wireformat!
        for (final JsonValue healthCheck : health.getPayload().getJsonArray(CHECKS)) {
            log.debug("Transforming single health check: {}", healthCheck);
            final var customCheckBuilder = Json.createObjectBuilder();

            final var status = healthCheck.asJsonObject().get("status");
            customCheckBuilder.add("status", status);

            final var data = healthCheck.asJsonObject().get("data");
            if (null != data) {
                for (final Map.Entry<String, JsonValue> dataEntry : data.asJsonObject().entrySet()) {
                    customCheckBuilder.add(dataEntry.getKey(), dataEntry.getValue());
                }
            }

            customBuilder.add(getName(healthCheck), customCheckBuilder.build());
        }

        log.debug("Health check response: {}", customBuilder);
        return customBuilder;
    }

    private static String getName(final JsonValue obj) {
        final var name = obj.asJsonObject().get(NAME);
        assert JsonValue.ValueType.STRING == name.getValueType() : "The health checks 'name' must be of type String";
        return ((JsonString) name).getString();
    }
}
