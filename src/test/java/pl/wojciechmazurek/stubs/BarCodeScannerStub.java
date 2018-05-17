package pl.wojciechmazurek.stubs;

import pl.wojciechmazurek.scanner.BarCodeScanner;

public class BarCodeScannerStub implements BarCodeScanner {
    @Override
    public Long scan() {
        return 2L;
    }
}
