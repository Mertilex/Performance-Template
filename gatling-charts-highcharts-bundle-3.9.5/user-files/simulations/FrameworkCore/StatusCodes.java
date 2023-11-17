package simulations.FrameworkCore;

import java.util.Arrays;
import java.util.List;

public class StatusCodes {
    public static List<Integer> getAcceptedStatusCodes() {
        return Arrays.asList(200, 302, 304, 201, 202, 203, 204, 205, 206, 207, 208, 209);
    }
}