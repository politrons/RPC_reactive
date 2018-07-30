package com.politrons.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;

public class DeserializeAvro {

    public static void main(String args[]){
        try {
            File avroOutput = new File("avro-person.avro");
            DatumReader<AvroPerson> bdPersonDatumReader = new SpecificDatumReader(AvroPerson.class);
            DataFileReader<AvroPerson> dataFileReader = new DataFileReader<>(avroOutput, bdPersonDatumReader);
            AvroPerson p = null;
            while(dataFileReader.hasNext()){
                p = dataFileReader.next(p);
                System.out.println(p);
            }
        } catch(IOException e) {System.out.println("Error reading Avro");}
    }
}
