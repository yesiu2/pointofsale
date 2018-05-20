package pl.wojciechmazurek.stubs;

import pl.wojciechmazurek.display.LCDDisplay;
import pl.wojciechmazurek.product.Product;

public class LCDDisplayStub implements LCDDisplay {
    @Override
    public void display(String message) {
        System.out.println(message);
    }


}
