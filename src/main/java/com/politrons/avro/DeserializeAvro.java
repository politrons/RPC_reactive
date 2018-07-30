package com.politrons.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;

public class DeserializeAvro {

    public static void fromFile() {
        try {
            File avroOutput = new File("avro-person.avro");
            DatumReader<AvroPerson> bdPersonDatumReader = new SpecificDatumReader(AvroPerson.class);
            DataFileReader<AvroPerson> dataFileReader = new DataFileReader<>(avroOutput, bdPersonDatumReader);
            AvroPerson p = null;
            while (dataFileReader.hasNext()) {
                p = dataFileReader.next(p);
                System.out.println(p);
            }
        } catch (IOException e) {
            System.out.println("Error reading Avro");
        }
    }

    public static AvroPerson fromByteArray(byte[] avroPersonData) {
        AvroPerson avroPerson = null;
        try {
            DatumReader<AvroPerson> employeeReader = new SpecificDatumReader<>(AvroPerson.class);
            Decoder binaryDecoder = DecoderFactory.get().binaryDecoder(avroPersonData, null);
            avroPerson = employeeReader.read(null, binaryDecoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avroPerson;
    }
}
