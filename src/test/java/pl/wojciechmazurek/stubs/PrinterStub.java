package pl.wojciechmazurek.stubs;

import pl.wojciechmazurek.printer.Printer;

public class PrinterStub implements Printer {
    @Override
    public void printReceipt(String receiptData) {
        System.out.println(receiptData);
    }
}
