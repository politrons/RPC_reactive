import com.politrons.avro.DeserializeAvro;
import com.politrons.avro.SerializeAvro;
import org.junit.Test;

public class AvroFeature {


    @Test
    public void serializeDeserializeInFile() {
        SerializeAvro.toFile();
        DeserializeAvro.fromFile();
    }

    @Test
    public void serializeDeserializeInByteArray() {
        SerializeAvro.toByteArray();
        DeserializeAvro.fromByteArray();
    }


}
