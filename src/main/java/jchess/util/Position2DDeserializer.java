package jchess.util;

import com.owlike.genson.Context;
import com.owlike.genson.Deserializer;
import com.owlike.genson.stream.ObjectReader;
import jchess.game.Position2D;

/**
 * A deserializer for {@link Position2D}. This class cannot be deserialized using the constructor, because it is a)
 * immutable and b) covered by the fly weight pattern via {@link Position2D#of(int, int)}
 *
 * @since 25.01.2016
 */
public class Position2DDeserializer implements Deserializer<Position2D> {

    @Override
    public Position2D deserialize(ObjectReader reader, Context ctx) throws Exception {
        int x = 0;
        int y = 0;
        reader.beginObject();

        while (reader.hasNext()) {
            reader.next();
            if ("x".equals(reader.name())) {
                x = reader.valueAsInt();
            }
            else if ("y".equals(reader.name())){
                y = reader.valueAsInt();
            }
            else {
                throw new Exception("Unexpected attribute '" + reader.name() + "' found while deserializing Position2D");
            }
        }

        reader.endObject();
        return Position2D.of(x, y);
    }
}
