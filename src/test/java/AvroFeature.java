import com.politrons.avro.DeserializeAvro;
import com.politrons.avro.SerializeAvro;
import org.junit.Test;

public class AvroFeature {


    @Test
    public void serializeDeserializeAvro() {
        SerializeAvro.main(null);
        DeserializeAvro.main(null);

    }

}
