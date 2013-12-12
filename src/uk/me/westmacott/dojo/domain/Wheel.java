package uk.me.westmacott.dojo.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: peter.westmacott
 * Date: 11/12/2013
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class Wheel {

    public static final double TAU = 2 * Math.PI;
    public static final double HALF_PI = 0.5 * Math.PI;
    private static final double GRAVITY = 9.8;
    private static final double WATER_TO_ADD = 1.0;
    private static final double FRICTION = 0.99;

    private final double bucketSpacing;
    Map<Double, Bucket> buckets;
    double rotation = 0.01;
    double rotationalVelocity = 0.01;
    private final double spoutAngle;

    public Wheel(int numberOfBuckets, double spoutAngle) {
        this.spoutAngle = spoutAngle;
        buckets = new HashMap<Double, Bucket>();

        bucketSpacing = TAU / numberOfBuckets;

        for (int bucketNumber = 0; bucketNumber < numberOfBuckets; bucketNumber++) {
            buckets.put(bucketSpacing * bucketNumber, new Bucket());
        }
    }

    public double getRotation() {
        return rotation;
    }

    public Map<Double, Bucket> getBuckets() {
        return new HashMap<Double, Bucket>(buckets);
    }

    public void tick() {

        double totalForce = 0.0;
        double totalMass = 0.0;

        for (Map.Entry<Double, Bucket> bucketEntry : buckets.entrySet()) {

            Bucket bucket = bucketEntry.getValue();
            bucket.tick();

            double bucketAngle = rotation + bucketEntry.getKey();
            double forceAngle = bucketAngle - HALF_PI;

            double bucketMass = bucket.getMass();
            double verticalForce = bucketMass * GRAVITY;
            double angularForce = verticalForce * Math.cos(forceAngle);
            totalForce += angularForce;
            totalMass += bucketMass;

            bucketAngle = (bucketAngle + TAU) % TAU;
            if (bucketAngle > (TAU - spoutAngle) || bucketAngle < spoutAngle) {
                bucket.addWater(WATER_TO_ADD);
            }
        }

        double totalAcceleration = totalForce / totalMass;
        rotationalVelocity += (totalAcceleration / 10000);
        rotationalVelocity *= FRICTION;
        rotation += rotationalVelocity;

        rotation += TAU;
        rotation %= TAU;
    }
}
