package com.chunhe.custom.pc.posCard;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

@Data
@XStreamAlias("XMLPosCPCardUser")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class XMLPosCPCardUser {
    private String text;
}
