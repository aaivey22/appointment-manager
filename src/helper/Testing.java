package helper;

public class Testing {

    public <T> T ObjectTest(T object) {
        if(object != null) {
            System.out.println("TEST-> PASS: Object " + object.toString() + " is valid");
        }
        else { System.out.println("TEST-> FAIL: " + object.toString() + " is null"); }
        return object;
    };
}
