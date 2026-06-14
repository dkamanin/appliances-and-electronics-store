package io.hexlet.maconi.catalog.domain.model.product.attribute;

public sealed interface AttributeValue
        permits StringAttributeValue,
        NumericAttributeValue,
        BooleanAttributeValue,
        RangeNumericAttributeValue,
        ListAttributeValue {
}

