package org.example.utils;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.GeodeticCalculator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Component;

@Component
public class DistanceUtils {
    private final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    public boolean isWithinDistance(String address1, String address2, double maxDistanceInMeters) {
        GeodeticCalculator calculator = new GeodeticCalculator();

        Coordinate coord1 = getCoordinateFromAddress(address1);
        Coordinate coord2 = getCoordinateFromAddress(address2);

        calculator.setStartingGeographicPoint(coord1.x, coord1.y);
        calculator.setDestinationGeographicPoint(coord2.x, coord2.y);

        double distance = calculator.getOrthodromicDistance();
        return distance <= maxDistanceInMeters;
    }

    private Coordinate getCoordinateFromAddress(String address) {
        // 在这里实现根据地址获取坐标的逻辑，例如调用地图服务API获取地址的经纬度坐标
        // 这里仅作示例，实际实现需要根据您的具体需求进行调整
        double latitude = 0.0; // 根据地址获取的纬度
        double longitude = 0.0; // 根据地址获取的经度
        return new Coordinate(longitude, latitude);
    }
}
