package com.glyart.ermes.common.messages;

import com.glyart.ermes.common.utils.ErmesDataInput;
import com.glyart.ermes.common.utils.ErmesDataOutput;

public interface MessageSerializable {

    void read(ErmesDataInput input);

    void write(ErmesDataOutput output);

}
