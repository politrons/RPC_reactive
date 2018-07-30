import com.politrons.avro.AvroPerson;
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
        byte[] bytes = SerializeAvro.toByteArray();
        AvroPerson avroPerson = DeserializeAvro.fromByteArray(bytes);
        System.out.println(avroPerson);
    }


}
