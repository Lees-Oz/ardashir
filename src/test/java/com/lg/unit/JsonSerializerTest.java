package com.lg.unit;

import com.lg.command.domain.events.NewGameSessionStarted;
import com.lg.command.domain.valueobjects.BackgammonConfig;
import com.lg.command.es.DomainEvent;
import com.lg.utils.JsonSerializer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.Map;
import java.util.UUID;

public class JsonSerializerTest extends TestCase {
    public JsonSerializerTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }


    public void test_When_deserialize_valid_json_Should_deserialize() throws Exception {
        // Arrange
        JsonSerializer target = new JsonSerializer();
        String gameId = "79a03138-6ba3-4a01-9e56-eaaea90796f0";
        String playerId = "2ea0b83e-0381-43d6-b50f-3ca4f77c6eda";
        String json = "{\n" +
                "  \"gameId\": \"" + gameId + "\",\n" +
                "  \"playerId\": \"" + playerId + "\"\n" +
                "}";

        Class<? extends DomainEvent> type = NewGameSessionStarted.class;

        // Act
        NewGameSessionStarted e = (NewGameSessionStarted) target.deserialize(json, type);

        // Assert
        Assert.assertNotNull(e);
        Assert.assertTrue(e.getGameId().equals(gameId));
        Assert.assertTrue(e.getByPlayerId().equals(UUID.fromString(playerId)));
    }

    public void test_When_serialize_object_Should_serialize() throws Exception {
        // Arrange
        JsonSerializer target = new JsonSerializer();
        NewGameSessionStarted arg = new NewGameSessionStarted(UUID.randomUUID().toString(), UUID.randomUUID(), new BackgammonConfig());

        // Act
        String result = target.serialize(arg);

        // Assert
        Assert.assertNotNull(result);
    }

    public void test_When_deserialize_valid_json_to_string_map_Should_deserialize() throws Exception {
        // Arrange
        JsonSerializer target = new JsonSerializer();
        String mapKey1 = "00903138-6ba3-4a01-9e56-eaaea90796f0";
        String mapKey2 = "0100b83e-0381-43d6-b50f-3ca4f77c6eda";
        String mapValue1 = "79a03138-6ba3-4a01-9e56-eaaea90796f0";
        String mapValue2 = "2ea0b83e-0381-43d6-b50f-3ca4f77c6eda";
        String json = "{\n" +
                "  \"" + mapKey1 + "\": \"" + mapValue1 + "\",\n" +
                "  \"" + mapKey2 + "\": \"" + mapValue2 + "\"\n" +
                "}";

        // Act
        Map<String, String> result = target.deserialize(json);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(result.values().size(), 2);
        Assert.assertEquals(result.get(mapKey1), mapValue1);
        Assert.assertEquals(result.get(mapKey2), mapValue2);
    }

    public void test_When_serialize_and_deserialize_object_Should_result_in_equivalent_object() throws Exception {
        // Arrange
        JsonSerializer target = new JsonSerializer();
        NewGameSessionStarted arg = new NewGameSessionStarted(UUID.randomUUID().toString(), UUID.randomUUID(), new BackgammonConfig(15, 24, 0, 12, 6));

        // Act
        String resultString = target.serialize(arg);
        NewGameSessionStarted resultObj = (NewGameSessionStarted) target.deserialize(resultString, arg.getClass());

        // Assert
        Assert.assertNotNull(resultObj);
        Assert.assertEquals(resultObj.getByPlayerId(), arg.getByPlayerId());
        Assert.assertEquals(resultObj.getGameId(), arg.getGameId());
    }
}
