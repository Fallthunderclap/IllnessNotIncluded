package net.illnessnotincluded.client;

public class ClientThirstData {
    private static float playerThirst;

    public static void set(float thirst) {
        ClientThirstData.playerThirst = thirst;
    }

    public static float getPlayerThirst() {
        return playerThirst;
    }
}
