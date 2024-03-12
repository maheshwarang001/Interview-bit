package com.example.jobcreator.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.BitSet;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncoderData {

    @Column(name = "bit_set", columnDefinition = "LONGBLOB")
    private byte[] bytes;

    @ElementCollection
    @MapKeyColumn(name="key_column")
    @Column(name="value_column")
    private Map<String,Character> decoder;
}
