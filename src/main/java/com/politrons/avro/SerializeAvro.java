package com.politrons.avro;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class SerializeAvro {


    public static AvroPerson createPerson() {
        AvroPerson p1 = new AvroPerson();
        p1.setId(1);
        p1.setUsername("mrscarter");
        p1.setFirstName("Beyonce");
        p1.setLastName("Knowles-Carter");
        return p1;
    }

    public static void main(String args[]) {
        File avroOutput = new File("avro-person.avro");
        try {
            DatumWriter<AvroPerson> bdPersonDatumWriter = new SpecificDatumWriter<>(AvroPerson.class);
            DataFileWriter<AvroPerson> dataFileWriter = new DataFileWriter<>(bdPersonDatumWriter);
            AvroPerson p1 = createPerson();
            dataFileWriter.create(p1.getSchema(), avroOutput);
            dataFileWriter.append(p1);
            dataFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing Avro");
        }
    }
}
