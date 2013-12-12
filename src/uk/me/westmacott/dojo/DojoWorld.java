package uk.me.westmacott.dojo;

import uk.me.westmacott.AbstractWorld;
import uk.me.westmacott.dojo.domain.Bucket;
import uk.me.westmacott.dojo.domain.Wheel;

import java.awt.*;
import java.util.Map;

public class DojoWorld extends AbstractWorld {


    public static final int WHEEL_RADIUS = 200;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int MID_WIDTH = WIDTH / 2;
    public static final int MID_HEIGHT = HEIGHT / 2;
    public static final Color BUCKET_COLOUR = new Color(100, 60, 30);
    private static final int BUCKET_MARGIN = 5;
    public static final double SPOUT_ANGLE = 0.15;

    Wheel wheel;

    public DojoWorld() {
        super("Dojo World!", WIDTH, HEIGHT);
        wheel = new Wheel(13, SPOUT_ANGLE);
    }

    @Override
    public void tick() {
        wheel.tick();
    }

    @Override
    public void render(Graphics2D g2) {

        g2.setColor(Color.BLACK);
        g2.drawOval(
                MID_WIDTH - WHEEL_RADIUS,
                MID_HEIGHT - WHEEL_RADIUS,
                2 * WHEEL_RADIUS,
                2 * WHEEL_RADIUS);


        renderWheel(g2, MID_WIDTH, MID_HEIGHT);
        renderBuckets(g2);
        renderSpout(g2);

    }

    private void renderSpout(Graphics2D g2) {
        g2.setColor(Color.CYAN);

        int spoutMaxX = (int) (MID_WIDTH + WHEEL_RADIUS * Math.sin(SPOUT_ANGLE)) - 5;
        int spoutMinX = (int) (MID_WIDTH - WHEEL_RADIUS * Math.sin(SPOUT_ANGLE)) + 5;
        int spoutMaxY = MID_HEIGHT;
        int spoutMinY = 0;

        for (int i = spoutMinX; i < spoutMaxX; i++) {
            for (int j = spoutMinY; j < spoutMaxY; j+=10) {
                double brightnessX = Math.random();
                double brightnessY = Math.random();
                g2.setColor(new Color(0.0f, 1.0f, 1.0f, (float) (brightnessX * brightnessY * 0.1)));
                g2.drawLine(i, 0, i, j);
            }
        }
    }

    private void renderBuckets(Graphics2D g2) {
        double wheelRotation = wheel.getRotation();
        for (Map.Entry<Double, Bucket> bucketEntry : wheel.getBuckets().entrySet()) {
            renderBucket(g2, bucketEntry.getKey() + wheelRotation, bucketEntry.getValue());
        }
    }

    private void renderWheel(Graphics2D g2, int wheelCentreX, int wheelCentreY) {

        g2.setColor(Color.BLACK);
        g2.drawOval(
                wheelCentreX - WHEEL_RADIUS,
                wheelCentreY - WHEEL_RADIUS,
                2 * WHEEL_RADIUS,
                2 * WHEEL_RADIUS);

        double wheelRotation = wheel.getRotation();
        double spokeSeparation = (2 * Math.PI) / 13.0;
        for (double spokeAngle = wheelRotation; spokeAngle < wheelRotation + (2 * Math.PI); spokeAngle += spokeSeparation) {
            int spokeX = (int) (WHEEL_RADIUS * Math.sin(spokeAngle));
            int spokeY = (int) (WHEEL_RADIUS * Math.cos(spokeAngle));
            g2.drawLine(wheelCentreX, wheelCentreY, wheelCentreX - spokeX, wheelCentreY - spokeY);
            int crossX1 = (int) (WHEEL_RADIUS * Math.sin(spokeAngle) * 0.5);
            int crossY1 = (int) (WHEEL_RADIUS * Math.cos(spokeAngle) * 0.5);
            int crossX2 = (int) (WHEEL_RADIUS * Math.sin(spokeAngle + spokeSeparation) * 0.5);
            int crossY2 = (int) (WHEEL_RADIUS * Math.cos(spokeAngle + spokeSeparation) * 0.5);
            g2.drawLine(
                    wheelCentreX + crossX1,
                    wheelCentreY + crossY1,
                    wheelCentreX + crossX2,
                    wheelCentreY + crossY2);
        }
    }

    private void renderBucket(Graphics2D g2, double bucketAngle, Bucket bucket) {

        int x = (int) (MID_WIDTH - WHEEL_RADIUS * Math.sin(bucketAngle));
        int y = (int) (MID_HEIGHT - WHEEL_RADIUS * Math.cos(bucketAngle));

        int waterSize = (int) bucket.getMass();
        int bucketSize = (int) Bucket.getMaxMass() + BUCKET_MARGIN;

        g2.setColor(BUCKET_COLOUR);
        g2.fillOval(x - bucketSize/2, y - bucketSize/2, bucketSize, bucketSize);

        g2.setColor(Color.CYAN);
        g2.fillOval(x - waterSize / 2, y - waterSize / 2, waterSize, waterSize);
    }

}
