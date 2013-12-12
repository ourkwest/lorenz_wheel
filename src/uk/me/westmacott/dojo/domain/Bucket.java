package uk.me.westmacott.dojo.domain;

public class Bucket {

    private static final double AMOUNT_OF_WATER_LOST_PER_TIME_PERIOD = 0.1;
    private static final double BUCKET_MASS = 10;
    private static final double BUCKET_CAPACITY = 50;

    private double amountOfWater;

    public void tick() {
        amountOfWater -= AMOUNT_OF_WATER_LOST_PER_TIME_PERIOD;

        if (amountOfWater < 0) {
            amountOfWater = 0;
        }

//        if (amountOfWater > (BUCKET_CAPACITY - 2)) {
//            System.out.println(amountOfWater);
//        }
    }

    public double getMass() {
        return amountOfWater + BUCKET_MASS;
    }

    public static double getMaxMass() {
        return BUCKET_MASS + BUCKET_CAPACITY;
    }

    public void addWater(double water) {
        amountOfWater += water;

        if (amountOfWater > BUCKET_CAPACITY) {
            amountOfWater = BUCKET_CAPACITY;
        }
    }

}
