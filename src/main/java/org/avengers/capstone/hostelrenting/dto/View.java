package org.avengers.capstone.hostelrenting.dto;

public class View {
    public static class FullInfo { }
    public static class ExcludeParent extends FullInfo { }
    public static class Internal extends ExcludeParent { }
}
