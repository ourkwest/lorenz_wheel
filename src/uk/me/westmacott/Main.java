package uk.me.westmacott;

import uk.me.westmacott.dojo.DojoWorld;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        new AnimationLoop(new DojoWorld(), 60).start();

    }
}