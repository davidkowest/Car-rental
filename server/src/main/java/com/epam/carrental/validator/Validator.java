package com.epam.carrental.validator;

public interface Validator<Element> {

    boolean validate(Element element);

}
