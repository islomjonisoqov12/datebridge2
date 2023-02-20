package com.kdatalab.bridge.point.model;

import org.springframework.beans.factory.annotation.Value;

public interface PointHistory {

    String getProjectType();
    String getProjectName();
    @Value(value = "#{@pointServiceImpl.getFormatDate(target.givenDate)}")
    String getGivenDate();
    Integer getPoint();
}
