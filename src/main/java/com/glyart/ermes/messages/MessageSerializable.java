package com.glyart.ermes.messages;

import com.glyart.ermes.utils.ErmesDataInput;
import com.glyart.ermes.utils.ErmesDataOutput;

public interface MessageSerializable {

    void read(ErmesDataInput input);

    void write(ErmesDataOutput output);

}
